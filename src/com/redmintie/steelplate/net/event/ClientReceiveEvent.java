package com.redmintie.steelplate.net.event;

import com.redmintie.steelplate.net.Client;
import com.redmintie.steelplate.util.data.DataPacket;

public class ClientReceiveEvent extends NetEvent {
	private DataPacket packet;
	public ClientReceiveEvent(Client client, DataPacket packet) {
		super(client);
		this.packet = packet;
	}
	@Override
	public void processEvent(NetListener listener) {
		((ClientListener)listener).packetRecieved(client, packet);
	}
}