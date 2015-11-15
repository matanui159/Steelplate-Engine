package com.redmintie.steelplate.input.event;

public interface MouseListener {
	public void mouseButtonPressed(MouseEvent e);
	public void mouseButtonReleased(MouseEvent e);
	public void mouseButtonClicked(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void mouseScrolled(MouseWheelEvent e);
}