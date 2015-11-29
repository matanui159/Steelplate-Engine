package com.redmintie.steelplate.util.multithread;

public interface MultiThreadListener {
	public void actionStarted();
	public void actionFinished();
	public void actionFailed(Exception ex);
}