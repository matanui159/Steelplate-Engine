package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public class ClientDisconnectEvent extends NetEvent {
	public ClientDisconnectEvent(Client client) {
		super(client);
	}
	@Override
	public void processEvent(NetListener listener) {
		((ClientListener)listener).clientDisconnected(client);
	}
}