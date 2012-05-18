package pt.yellowduck.ramboia;

import java.util.Collection;
import org.bff.javampd.MPD;
import org.bff.javampd.MPDDatabase;
import org.bff.javampd.MPDFile;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.objects.MPDSong;

/**
 * User: dneves
 * Date: 5/18/12 Time: 2:32 PM
 */
public class MPDTest {
	
	public static void main( String ... args ) throws Exception {
		MPD mpd = new MPD( "172.19.232.41" );
		
		MPDDatabase db = mpd.getMPDDatabase();

		System.out.println( "------------------------------------------------------------------");

		processFiles( db, db.listRootDirectory(), "" );
		
		System.out.println( "------------------------------------------------------------------");

		Collection< MPDSong > found = db.find( MPDDatabase.ScopeType.FILENAME, "You Can't Win, Charlie Brown/Chromatic/01 Over The Sun _ Under The Water.mp3" );
		for ( MPDSong song : found ) {
			System.out.println( " -> " + song.getFile() );
		}

	}
	
	private static void processFiles( MPDDatabase db, Collection< MPDFile > files, String pad ) throws Exception {
		for ( MPDFile file : files ) {
			if ( file.isDirectory() ) {
				Collection< MPDFile > directoryFiles = db.listDirectory( file );
				System.out.println( pad + file.getName() );
				processFiles(db, directoryFiles, pad + "-");
			} else {
				System.out.println( pad + "> " + file.getPath() );
			}
		}
	}
	
}
