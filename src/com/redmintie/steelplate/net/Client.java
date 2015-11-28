package com.redmintie.steelplate.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.redmintie.steelplate.multithread.MultiThreadAdapter;
import com.redmintie.steelplate.multithread.MultiThreadService;
import com.redmintie.steelplate.net.event.ClientDisconnectEvent;
import com.redmintie.steelplate.net.event.ClientListener;
import com.redmintie.steelplate.net.event.ClientReceiveEvent;
import com.redmintie.steelplate.net.event.NetEvent;
import com.redmintie.steelplate.util.array.MappedArray;

public class Client {
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private MappedArray<NetEvent> events = new MappedArray<NetEvent>();
	private MappedArray<ClientListener> listeners = new MappedArray<ClientListener>();
 	
	public Client(String address) throws IOException {
		String[] parts = address.split(":");
		if (parts.length == 2) {
			try {
				init(new Socket(parts[0], Integer.parseInt(parts[1])));
			} catch (NumberFormatException ex) {
				formatError();
			}
		} else {
			formatError();
		}
	}
	public Client(String address, int port) throws IOException {
		init(new Socket(address, port));
	}
	Client(Socket socket) throws IOException {
		init(socket);
	}
	private void formatError() {
		throw new RuntimeException("Address must be in the form of 'address':'port'");
	}
	private void init(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		new ClientService();
	}
	public String getLocalAddress() {
		return socket.getLocalAddress().getHostName();
	}
	public int getLocalPort() {
		return socket.getLocalPort();
	}
	public String getAddress() {
		return socket.getInetAddress().getHostName();
	}
	public int getPort() {
		return socket.getPort();
	}
	public void sendString(String msg) {
		try {
			out.writeBoolean(false);
			out.writeUTF(msg);
		} catch (IOException ex) {}
	}
	public void sendNumber(double msg) {
		try {
			out.writeBoolean(true);
			out.writeDouble(msg);
		} catch (IOException ex) {}
	}
	public void addListener(ClientListener listener) {
		listeners.add(listener);
	}
	public void removeListener(ClientListener listener) {
		listeners.remove(listener);
	}
	public void clearListeners() {
		listeners.clear();
	}
	public void pollEvents() {
		for (NetEvent event : events) {
			for (ClientListener listener : listeners) {
				event.processEvent(listener);
			}
			events.remove(event);
		}
	}
	public void close() throws IOException {
		socket.close();
	}
	private class ClientService extends MultiThreadService {
		public ClientService() {
			super("Client Service", new MultiThreadAdapter() {
				@Override
				public void actionFailed(Exception ex) {
					if (!socket.isClosed()) {
						try {
							socket.close();
						} catch (IOException e) {}
						events.add(new ClientDisconnectEvent(Client.this, ex));
					}
				}
			});
		}
		@Override
		public void update() throws IOException {
			ClientReceiveEvent event = new ClientReceiveEvent(Client.this, in.readBoolean());
			if (event.type) {
				event.num = in.readDouble();
			} else {
				event.str = in.readUTF();
			}
			events.add(event);
		}
		@Override
		public void eventCalled(int event) {
		}
	}
}