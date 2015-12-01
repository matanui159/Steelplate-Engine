package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public interface ServerListener extends ClientListener {
	public void clientAccepted(Client client);
}