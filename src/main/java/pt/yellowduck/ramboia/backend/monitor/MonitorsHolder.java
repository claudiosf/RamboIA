package pt.yellowduck.ramboia.backend.monitor;

import java.util.LinkedList;
import java.util.List;

/**
 * User: dneves
 * Date: 5/18/12 Time: 1:53 PM
 */
public class MonitorsHolder {

	private static MonitorsHolder INSTANCE = null;
	
	private final List< Monitor > monitors = new LinkedList< Monitor >();
	
	private MonitorsHolder() {
		
	}
	
	public static synchronized MonitorsHolder getInstance() {
		if ( INSTANCE == null ) {
			INSTANCE = new MonitorsHolder();
		}
		return INSTANCE;
	}
	
	
	public void addMonitor( Monitor monitor ) {
		if ( monitor != null ) {
			monitors.add( monitor );
		}
	}

	public void destroyMonitor(Monitor monitor) {
		monitor.stop();
		monitors.remove( monitor );
	}

	public void startMonitors() {
		for ( Monitor monitor : monitors ) {
			monitor.start();
		}
	}

	public void stopMonitors() {
		for ( Monitor monitor : monitors ) {
			monitor.stop();
		}
	}
	
	public void destroyMonitors() {
		System.out.println("MonitorsHolder . destroyMonitors() : " + monitors.size());
		stopMonitors();
		monitors.clear();
	}
}
