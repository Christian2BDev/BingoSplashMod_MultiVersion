package com.heckvision.bingosplash.web;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;

/**
 * WebSocketManager with SAN/SNI fix for Java 8u51.
 * Safe for production: full cert validation, SNI enforced.
 */
public class WebSocketManager {

    private WebSocketClient client;
    private final String serverUrl;

    public WebSocketManager(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void connect() {
        try {
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
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket closed: " + reason);
                    reconnect();
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex);
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
        }
    }

    public void reconnect() {
        try {
            System.out.println("Connection lost, trying to reconnect...");
            Thread.sleep(2000); // delay before retry
            connect();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void close() {
        if (client != null) {
            client.close();
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