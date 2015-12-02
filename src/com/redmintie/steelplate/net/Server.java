package com.redmintie.steelplate.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import com.redmintie.steelplate.net.event.ServerAcceptEvent;
import com.redmintie.steelplate.net.event.ServerListener;
import com.redmintie.steelplate.util.array.MappedArray;
import com.redmintie.steelplate.util.multithread.MultiThreadService;

public class Server {
	private ServerSocket socket;
	MappedArray<Client> clients = new MappedArray<Client>();
	private MappedArray<ServerListener> listeners = new MappedArray<ServerListener>();
	private MappedArray<ServerAcceptEvent> events = new MappedArray<ServerAcceptEvent>();
	private InetAddress address;
	
	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
		address = InetAddress.getLoopbackAddress();
		new ServerService().start();
	}
	public Server() throws IOException {
		this(0);
	}
	public String getLocalAddress() {
		return address.getHostAddress();
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
		for (Client client : clients) {
			client.pollEvents();
		}
	}
	public void close() throws IOException {
		socket.close();
	}
	private class ServerService extends MultiThreadService {
		public ServerService() {
			super("Server Service", null);
		}
		@Override
		public void update() throws IOException {
			events.add(new ServerAcceptEvent(new Client(Server.this, socket.accept())));
		}
		@Override
		public void eventCalled(int event) {
		}
	}
}