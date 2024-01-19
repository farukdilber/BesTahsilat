
/**
 * @author ABDULLAH
 *
 */
package com.avivasa.rpa.util;

import java.util.LinkedHashMap;

import com.avivasa.rpa.util.ThreadControl;

public class ThreadController {

	private static LinkedHashMap<String, ThreadControl> tlisthm = new LinkedHashMap<>();

	public void threadStart(String procces) {

		if (!tlisthm.containsKey(procces)) {
			tlisthm.put(procces, new ThreadControl());
		}
		tlisthm.get(procces).threadStart();
	}

	public void threadStop(String procces) {
		tlisthm.get(procces).threadStop();
	}
}
