package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public class ClientFailureEvent extends ClientDisconnectEvent {
	private Exception ex;
	public ClientFailureEvent(Client client, Exception ex) {
		super(client);
		this.ex = ex;
	}
	@Override
	public void processEvent(NetListener listener) {
		((ClientListener)listener).clientFailed(client, ex);
	}
}