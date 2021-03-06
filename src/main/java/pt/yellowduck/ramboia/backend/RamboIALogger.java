package pt.yellowduck.ramboia.backend;

import com.vaadin.ui.Window;

/**
 * User: dneves
 * Date: 5/21/12 Time: 3:33 PM
 */
public class RamboIALogger {

	private RamboIALogger() {
		
	}

	public static void notify( Window window, String description ) {
		notify( window, "Notify", description );
	}
	
	public static void notify( Window window, String caption, String description ) {
		if ( window != null ) {
			window.showNotification( caption, description, Window.Notification.TYPE_HUMANIZED_MESSAGE );
		}
	}
	
}
