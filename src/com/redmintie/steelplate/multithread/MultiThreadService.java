package com.redmintie.steelplate.multithread;

public abstract class MultiThreadService extends MultiThreadAction {
	private static final int EVENT_START = Integer.MAX_VALUE - 1;
	public static final int EVENT_END = Integer.MAX_VALUE;
	private boolean running;
	private volatile int event;
	public MultiThreadService(String name, MultiThreadListener listener) {
		super(name, listener);
	}
	private void block() {
		while (event != -1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {}
		}
	}
	@Override
	public void start() {
		if (!running) {
			running = true;
			synchronized (this) {
				event = EVENT_START;
			}
			super.start();
			block();
		}
	}
	@Override
	public void doAction() throws Exception {
		while (true) {
			synchronized (this) {
				if (event != -1) {
					eventCalled(event);
					boolean end = event == EVENT_END;
					event = -1;
					if (end) {
						break;
					}
				}
			}
			update();
		}
	}
	public abstract void update() throws Exception;
	public abstract void eventCalled(int event) throws Exception;
	public void callEvent(int event, boolean block) {
		if (!running) {
			throw new RuntimeException("Service not running");
		}
		this.event = event;
		if (block) {
			block();
		}
	}
	public void callEvent(int event) {
		this.event = event;
	}
	public void end(boolean block) {
		callEvent(EVENT_END, block);
	}
	public void end() {
		callEvent(EVENT_END);
	}
}