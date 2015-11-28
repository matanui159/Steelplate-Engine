package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public interface ClientListener extends NetListener {
	public void stringReceived(Client client, String msg);
	public void numberReceived(Client client, double msg);
	public void clientDisconnected(Client client, Exception ex);
}