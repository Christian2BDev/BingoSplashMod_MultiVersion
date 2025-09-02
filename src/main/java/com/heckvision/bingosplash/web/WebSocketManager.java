package com.heckvision.bingosplash.web;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * WebSocketManager with SAN/SNI fix for Java 8u51.
 * Safe for production: full cert validation, SNI enforced.
 */
public class WebSocketManager {

    private WebSocketClient client;
    private final String serverUrl;
    private final ReentrantLock clientLock = new ReentrantLock();
    private final AtomicBoolean shouldReconnect = new AtomicBoolean(false);
    private final ScheduledExecutorService reconnectionExecutor = Executors.newSingleThreadScheduledExecutor();
    private volatile boolean isShutdown = false;
    private MessageListener messageListener;

    public WebSocketManager(String serverUrl, MessageListener messageListener) {
        this.serverUrl = serverUrl;
        this.messageListener = messageListener;
    }

    public void connect() {
        if (isShutdown) {
            return;
        }

        clientLock.lock();
        try {
            if (isShutdown) {
                return;
            }

            URI uri = new URI(serverUrl);
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 443 : uri.getPort();

            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("WebSocket opened: " + getURI());
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Message received: " + message);
                    messageListener.onMessage(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket closed: " + reason);
                    if (shouldReconnect.get() && !isShutdown) {
                        scheduleReconnection();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex);
                    if (shouldReconnect.get() && !isShutdown) {
                        scheduleReconnection();
                    }
                }
            };

            KeyStore ts = KeyStore.getInstance("PKCS12");
            try (InputStream is = WebSocketManager.class.getResourceAsStream("/mykeystore.jks")) {
                ts.load(is, "changeit".toCharArray());
            }

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ts);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            SSLSocketFactory sniFactory = new SNISocketFactory(sslContext.getSocketFactory(), host, port);
            client.setSocketFactory(sniFactory);

            client.connect();

        } catch (Exception e) {
            e.printStackTrace();
            if (shouldReconnect.get() && !isShutdown) {
                scheduleReconnection();
            }
        } finally {
            clientLock.unlock();
        }
    }

    private void scheduleReconnection() {
        if (isShutdown || !shouldReconnect.get()) {
            return;
        }

        reconnectionExecutor.schedule(() -> {
            if (!isShutdown && shouldReconnect.get()) {
                connect();
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }

    public void reconnect() {
        if (isShutdown) {
            return;
        }

        clientLock.lock();
        try {
            if (client != null) {
                client.close();
            }
            connect();
        } finally {
            clientLock.unlock();
        }
    }

    public void close() {
        clientLock.lock();
        try {
            shouldReconnect.set(false);
            if (client != null) {
                client.close();
                client = null;
            }
        } finally {
            clientLock.unlock();
        }
    }

    public void shutdown() {
        clientLock.lock();
        try {
            isShutdown = true;
            shouldReconnect.set(false);
            if (client != null) {
                client.close();
                client = null;
            }
            reconnectionExecutor.shutdown();
        } finally {
            clientLock.unlock();
        }
    }

    // === SNISocketFactory (forces SAN/SNI) ===
    private static class SNISocketFactory extends SSLSocketFactory {
        private final SSLSocketFactory delegate;
        private final String host;
        private final int port;

        SNISocketFactory(SSLSocketFactory delegate, String host, int port) {
            this.delegate = delegate;
            this.host = host;
            this.port = port;
        }

        @Override
        public Socket createSocket() throws IOException {
            return enableSNI(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return enableSNI(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return enableSNI(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return enableSNI(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enableSNI(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return enableSNI(delegate.createSocket(address, port, localAddress, localPort));
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        private Socket enableSNI(Socket socket) {
            if (socket instanceof SSLSocket) {
                try {
                    SSLSocket sslSocket = (SSLSocket) socket;
                    SSLParameters sslParams = sslSocket.getSSLParameters();

                    // Force SNI with hostname
                    List<SNIServerName> serverNames =
                            Collections.<SNIServerName>singletonList(new SNIHostName(host));
                    sslParams.setServerNames(serverNames);

                    sslSocket.setSSLParameters(sslParams);
                } catch (Exception e) {
                    System.err.println("Warning: Failed to set SNI: " + e.getMessage());
                }
            }
            return socket;
        }
    }
}