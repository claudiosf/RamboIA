package pt.yellowduck.ramboia.backend;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import pt.yellowduck.ramboia.backend.monitor.MonitorsHolder;

/**
 * User: dneves
 * Date: 5/18/12 Time: 1:53 PM
 */
public class ContextMonitorCleaner implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println( "RamboIA WebApp Initialized.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println( "RamboIA WebApp Destroyed." );
		MonitorsHolder.getInstance().destroyMonitors();
	}

}
