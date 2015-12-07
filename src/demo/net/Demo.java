package demo.net;

import java.io.IOException;
import java.util.Scanner;

import com.redmintie.steelplate.core.DeviceException;
import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;

public class Demo extends Game {
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			System.out.print("Create a new game (y/n)? ");
			boolean host = in.nextLine().toLowerCase().trim().startsWith("y");
			if (host) {
				new Demo();
			} else {
				System.out.print("What address would you like to connect to? ");
				new Demo(in.nextLine());
			}
			in.close();
		} catch (DeviceException|IOException ex) {
			ex.printStackTrace();
		}
	}
	private DemoServer server;
	private DemoClient client;
	
	public Demo() throws DeviceException, IOException {
		setTitle("Net Demo (server)");
		server = new DemoServer();
		client = new DemoClient("127.0.0.1:" + server.getLocalPort());
		begin();
	}
	public Demo(String address) throws DeviceException, IOException {
		setTitle("Net Demo (client)");
		client = new DemoClient(address);
		begin();
	}
	@Override
	public void init() {
	}
	@Override
	public void update(double delta) {
		if (server != null) {
			server.pollEvents();
		}
		client.pollEvents();
		client.update(delta);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.setColor(new Color(0, 0, 0, 0));
		canvas.clear();
		client.draw(canvas);
	}
	@Override
	public void close() {
		try {
			if (server == null) {
				client.close();
			} else {
				server.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		end();
	}
}