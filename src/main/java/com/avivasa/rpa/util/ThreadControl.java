
/**
 * @author ABDULLAH
 *
 */
package com.avivasa.rpa.util;

import java.util.LinkedList;

public class ThreadControl {
	private LinkedList<Thread> tList;

	public ThreadControl() {
		tList = new LinkedList<>();
	}

	@SuppressWarnings("deprecation")
	public void threadStart() {
		tList.addLast(Thread.currentThread());
		if (!tList.getFirst().equals(Thread.currentThread()))
			Thread.currentThread().suspend();
	}

	@SuppressWarnings("deprecation")
	public void threadStop() {
		tList.removeFirst();
		if (!tList.isEmpty())
			tList.getFirst().resume();
	}

}
