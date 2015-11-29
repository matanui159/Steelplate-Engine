package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;
import com.redmintie.steelplate.util.data.DataPacket;

public class ClientAdapter implements ClientListener {
	@Override
	public void packetRecieved(Client client, DataPacket packet) {
	}
	@Override
	public void clientDisconnected(Client client, Exception ex) {
	}
}