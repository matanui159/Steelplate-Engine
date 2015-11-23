package com.redmintie.steelplate.multithread;

public interface MultiThreadListener {
	public void actionStarted();
	public void actionFinished();
	public void actionFailed(Exception ex);
}