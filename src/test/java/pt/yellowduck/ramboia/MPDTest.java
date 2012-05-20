package pt.yellowduck.ramboia;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.bff.javampd.MPD;
import org.bff.javampd.MPDDatabase;
import org.bff.javampd.MPDFile;
import org.bff.javampd.MPDPlayer;
import org.bff.javampd.objects.MPDSong;
import pt.yellowduck.ramboia.backend.model.SongFile;

/**
 * User: dneves
 * Date: 5/18/12 Time: 2:32 PM
 */
public class MPDTest {
	
	public static void main( String ... args ) throws Exception {
		MPD mpd = new MPD( "127.0.0.1" );
		
		MPDDatabase db = mpd.getMPDDatabase();

		System.out.println( "------------------------------------------------------------------");
		processFiles( db, db.listRootDirectory(), "" );
		System.out.println( "------------------------------------------------------------------");
		
		List< SongFile > files = processRoots( db, db.listRootDirectory() );
		System.out.println( "Files : " + files.size() );
		for ( SongFile file : files ) {
			System.out.println( file );
			if ( file.isDirectory() ) {
				List< SongFile > folderFiles = file.getChildrens();
				if ( folderFiles != null ) {
					for ( SongFile f : folderFiles ) {
						System.out.println( " -> " + f );
					}
				}
			}
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

	private static List< SongFile > processRoots( MPDDatabase db, Collection< MPDFile > roots ) throws Exception {
		List< SongFile > result = new LinkedList<SongFile>();
		for ( MPDFile root : roots ) {
			if ( root.isDirectory() ) {
				SongFile dir = new SongFile( root );
				List< SongFile > childrens = processRoots( db, db.listDirectory( root ) );
				dir.setChildrens( childrens );
				result.add( dir );
			} else {
				result.add( new SongFile( root ) );
			}
		}
		return result;
	}
	
}
