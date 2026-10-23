package com.uesvaz.servidor;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/** Servidor local que retransmite cada mensaje a todos los clientes conectados. */
public final class ServidorWebSocket extends WebSocketServer {
    public static final int DEFAULT_PORT = 8887;

    public ServidorWebSocket(int port) {
        // Escucha en todas las interfaces para aceptar emuladores y móviles de la LAN.
        super(new InetSocketAddress("0.0.0.0", port));
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake handshake) {
        System.out.println("Cliente conectado: " + connection.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket connection, int code, String reason, boolean remote) {
        System.out.println("Cliente desconectado: " + connection.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
        System.out.println("Mensaje: " + message);
        broadcast(message);
    }

    @Override
    public void onError(WebSocket connection, Exception exception) {
        System.err.println("Error WebSocket: " + exception.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Servidor WebSocket escuchando en el puerto " + getPort());
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        new ServidorWebSocket(port).start();
    }
}
