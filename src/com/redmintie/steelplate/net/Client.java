package com.redmintie.steelplate.net;

import java.io.IOException;
import java.net.Socket;

import com.redmintie.steelplate.net.event.ClientDisconnectEvent;
import com.redmintie.steelplate.net.event.ClientFailureEvent;
import com.redmintie.steelplate.net.event.ClientListener;
import com.redmintie.steelplate.net.event.ClientReceiveEvent;
import com.redmintie.steelplate.net.event.NetEvent;
import com.redmintie.steelplate.util.array.MappedArray;
import com.redmintie.steelplate.util.data.DataInputStream;
import com.redmintie.steelplate.util.data.DataObject;
import com.redmintie.steelplate.util.data.DataOutputStream;
import com.redmintie.steelplate.util.data.DataPacket;
import com.redmintie.steelplate.util.data.DataUtil;
import com.redmintie.steelplate.util.multithread.MultiThreadAdapter;
import com.redmintie.steelplate.util.multithread.MultiThreadService;

public class Client {
	private static final long CLOSE = DataUtil.generateHeader("STLPLT", "CLOSE ") << 16;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Server server;
	private MappedArray<NetEvent> events = new MappedArray<NetEvent>();
	private MappedArray<ClientListener> listeners = new MappedArray<ClientListener>();
	private ClientService service;
	private boolean closed;
 	
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
	Client(Server server, Socket socket) throws IOException {
		this.server = server;
		server.clients.add(this);
		init(socket);
	}
	private void formatError() {
		throw new RuntimeException("Address must be in the form of 'address':'port'");
	}
	private void init(Socket socket) throws IOException {
		this.socket = socket;
		socket.setTcpNoDelay(true);
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		service = new ClientService();
		service.start();
	}
	public String getLocalAddress() {
		return socket.getLocalAddress().getHostAddress();
	}
	public int getLocalPort() {
		return socket.getLocalPort();
	}
	public String getAddress() {
		return socket.getInetAddress().getHostAddress();
	}
	public int getPort() {
		return socket.getPort();
	}
	public void sendObject(DataObject object) throws IOException {
		out.writeDataObject(object);
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
			if (server != null && event instanceof ClientDisconnectEvent) {
				server.clients.remove(this);
			}
			events.remove(event);
		}
	}
	public void close() throws IOException {
		if (!closed) {
			out.getDataOutput().writeLong(CLOSE);
			socket.close();
			closed = true;
		}
	}
	private class ClientService extends MultiThreadService {
		public ClientService() {
			super("Client Service", new MultiThreadAdapter() {
				@Override
				public void actionFailed(Exception ex) {
					if (!closed) {
						events.add(new ClientFailureEvent(Client.this, ex));
						try {
							close();
						} catch (IOException e) {}
					}
				}
			});
		}
		@Override
		public void update() throws IOException {
			long header = in.getDataInput().readLong();
			if (header == CLOSE) {
				socket.close();
				closed = true;
				events.add(new ClientDisconnectEvent(Client.this));
				end();
			} else {
				events.add(new ClientReceiveEvent(Client.this, new DataPacket(header, in.getDataInput())));
			}
		}
		@Override
		public void eventCalled(int event) {
		}
	}
}