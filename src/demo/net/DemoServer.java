package demo.net;

import java.io.IOException;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.net.Client;
import com.redmintie.steelplate.net.Server;
import com.redmintie.steelplate.util.Map;

public class DemoServer extends Server {
	private Map<Client, Entity> players = new Map<Client, Entity>();
	public DemoServer() throws IOException {
	}
}