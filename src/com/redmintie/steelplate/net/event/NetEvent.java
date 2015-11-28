package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public abstract class NetEvent {
	protected Client client;
	public NetEvent(Client client) {
		this.client = client;
	}
	public abstract void processEvent(NetListener listener);
}