package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public class ClientAdapter implements ClientListener {
	@Override
	public void stringReceived(Client client, String msg) {
	}
	@Override
	public void numberReceived(Client client, double msg) {
	}
	@Override
	public void clientDisconnected(Client client, Exception ex) {
	}
}