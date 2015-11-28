package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public class ClientDisconnectEvent extends NetEvent {
	private Exception ex;
	public ClientDisconnectEvent(Client client, Exception ex) {
		super(client);
		this.ex = ex;
	}
	@Override
	public void processEvent(NetListener listener) {
		((ClientListener)listener).clientDisconnected(client, ex);
	}
}