package demo.net;

import java.io.IOException;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.net.Client;
import com.redmintie.steelplate.net.event.ClientListener;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.array.MappedArray;
import com.redmintie.steelplate.util.data.DataPacket;

public class DemoClient extends Client {
	private MappedArray<Player> players = new MappedArray<Player>();
	private PlayerPosition buffer = new PlayerPosition();
	private ClientPlayer player = new ClientPlayer();
	public DemoClient(String address) throws IOException {
		super(address);
		addListener(new ClientListener() {
			@Override
			public void packetRecieved(Client client, DataPacket packet) {
				try {
					packet.getObject(buffer);
					if (players.get(buffer.id) == null) {
						players.set(buffer.id, packet.getObject(new Player()));
					} else {
						packet.getObject(players.get(buffer.id));
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void clientDisconnected(Client client, Exception ex) {
				Game.getGameInstance().close();
			}
		});
	}
	public void update(double delta) {
		player.update(delta);
		try {
			sendObject(player);
		} catch (IOException ex) {}
	}
	public void draw(Canvas canvas) {
		for (Player player : players) {
			player.draw(canvas);
		}
		player.draw(canvas);
	}
}