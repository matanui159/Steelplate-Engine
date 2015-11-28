package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public class ClientReceiveEvent extends NetEvent {
	public boolean type;
	public String str;
	public double num;
	public ClientReceiveEvent(Client client, boolean type) {
		super(client);
		this.type = type;
	}
	@Override
	public void processEvent(NetListener listener) {
		if (type) {
			((ClientListener)listener).numberReceived(client, num);
		} else {
			((ClientListener)listener).stringReceived(client, str);
		}
	}
}