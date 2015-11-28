package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;

public interface ServerListener extends NetListener {
	public void clientAccepted(Client client);
}