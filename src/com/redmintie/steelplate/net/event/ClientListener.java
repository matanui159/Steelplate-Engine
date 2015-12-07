package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;
import com.redmintie.steelplate.util.data.DataPacket;

public interface ClientListener extends NetListener {
	public void packetRecieved(Client client, DataPacket packet);
	public void clientDisconnected(Client client);
	public void clientFailed(Client client, Exception ex);
}