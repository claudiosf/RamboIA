package pt.yellowduck.ramboia.frontend;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: dneves
 * Date: 5/18/12 Time: 6:47 PM
 */
public class Utils {

	public static String formatTime( long duration, TimeUnit timeUnit ) {
//		long days = timeUnit.toDays( duration );
//		duration = duration - ( days * 3600 * 24 );
		long hours = timeUnit.toHours( duration );
		duration = duration - ( hours * 60 * 60 );
		long minutes = timeUnit.toMinutes( duration );
		duration = duration - ( minutes * 60 );
		long seconds = timeUnit.toSeconds( duration );

		List< Long > args = new LinkedList< Long >();
		args.add( minutes );
		args.add( seconds );
		String formatter = "%02d:%02d";
		if ( hours > 0 ) {
			formatter = "%02d:" + formatter;
			args.add( 0, hours );
		}

		return String.format( formatter, args.toArray( new Long[ args.size() ] ) );
	}
}
