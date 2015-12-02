package demo.net;

import java.io.IOException;

import com.redmintie.steelplate.net.Client;
import com.redmintie.steelplate.net.Server;
import com.redmintie.steelplate.net.event.ServerAdapter;
import com.redmintie.steelplate.util.array.MappedArray;
import com.redmintie.steelplate.util.data.DataPacket;

public class DemoServer extends Server {
	private MappedArray<Client> clients = new MappedArray<Client>();
	private PlayerPosition pos = new PlayerPosition();
	public DemoServer() throws IOException {
		System.out.println("Server Address: " + getLocalAddress() + ":" + getLocalPort());
		addListener(new ServerAdapter() {
			@Override
			public void clientAccepted(Client client) {
				clients.add(client);
			}
			@Override
			public void packetRecieved(Client client, DataPacket packet) {
				try {
					packet.getObject(pos);
					pos.id = clients.indexOf(client);
					for (Client c : clients) {
						if (c != client) {
							c.sendObject(pos);
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}