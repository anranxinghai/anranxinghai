package com.anranxinghai.musicplayer.runnable;


public abstract class SecurityThread extends Thread {
	protected volatile boolean stopRequested = false;
	private Thread runThread = Thread.currentThread();

	@Override
	public void run() {
	
		stopRequested = false;
		/*
		int count = 0;
		while (!stopRequested) {
			System.out.println("Running ... count=" + count);
			count++;
			try {
				Thread.sleep(300);
				
			} catch (InterruptedException x) {
				Thread.currentThread().interrupt(); // re-assert interrupt
			}
		}*/
	}

	public void stopRequest() {
		stopRequested = true;
		if (runThread != null) {
			runThread.interrupt();
		}
	}
}
