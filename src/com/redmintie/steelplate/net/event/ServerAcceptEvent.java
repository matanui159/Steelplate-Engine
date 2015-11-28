package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public class ServerAcceptEvent extends NetEvent {
	public ServerAcceptEvent(Client client) {
		super(client);
	}
	@Override
	public void processEvent(NetListener listener) {
		((ServerListener)listener).clientAccepted(client);
	}
}