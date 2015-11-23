package com.redmintie.steelplate.multithread;

public abstract class MultiThreadAction extends Thread {
	private MultiThreadListener listener;
	public MultiThreadAction(MultiThreadListener listener) {
		super("Multi Thread Action");
		this.listener = listener;
	}
	@Override
	public void run() {
		if (listener != null) {
			listener.actionStarted();
		}
		try {
			doAction();
			listener.actionFinished();
		} catch (Exception ex) {
			if (listener != null) {
				listener.actionFailed(ex);
			}
		}
	}
	public abstract void doAction() throws Exception;
}