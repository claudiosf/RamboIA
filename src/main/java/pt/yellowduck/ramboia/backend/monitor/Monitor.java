package pt.yellowduck.ramboia.backend.monitor;

import org.bff.javampd.monitor.MPDStandAloneMonitor;

/**
 * User: dneves
 * Date: 5/18/12 Time: 1:56 PM
 */
public class Monitor {

	private Thread threadMonitor = null;

	private final MPDStandAloneMonitor monitor;

	public Monitor( MPDStandAloneMonitor monitor ) {
		this.monitor = monitor;
	}

	public boolean isRunning() {
		return threadMonitor != null && threadMonitor.isAlive();
	}

	public void start() {
		if ( ! isRunning() ) {
			threadMonitor = new Thread( monitor );
			threadMonitor.setName( "MPD StandAlone Monitor - " + monitor.getStatus() );
			threadMonitor.start();
		}
	}

	public void stop() {
		monitor.stop();
		if ( isRunning() ) {
			threadMonitor.interrupt();
			threadMonitor = null;
		}
	}

}
