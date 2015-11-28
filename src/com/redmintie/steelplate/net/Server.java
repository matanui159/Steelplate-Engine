package com.redmintie.steelplate.net;

import java.io.IOException;
import java.net.ServerSocket;

import com.redmintie.steelplate.net.event.ServerAcceptEvent;
import com.redmintie.steelplate.net.event.ServerListener;
import com.redmintie.steelplate.util.array.MappedArray;

public class Server {
	private ServerSocket socket;
	private MappedArray<ServerListener> listeners = new MappedArray<ServerListener>();
	private MappedArray<ServerAcceptEvent> events = new MappedArray<ServerAcceptEvent>();
	
	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
	}
	public Server() throws IOException {
		socket = new ServerSocket();
	}
	public String getLocalAddress() {
		return socket.getInetAddress().getHostName();
	}
	public int getLocalPort() {
		return socket.getLocalPort();
	}
	public void addListener(ServerListener listener) {
		listeners.add(listener);
	}
	public void removeListener(ServerListener listener) {
		listeners.remove(listener);
	}
	public void clearListeners() {
		listeners.clear();
	}
	public void pollEvents() {
		for (ServerAcceptEvent event : events) {
			for (ServerListener listener : listeners) {
				event.processEvent(listener);
			}
			events.remove(event);
		}
	}
	public void close() throws IOException {
		socket.close();
	}
}