package pt.yellowduck.ramboia.backend.model;

import java.util.LinkedList;
import java.util.List;
import org.bff.javampd.MPDFile;

/**
 * User: laught
 * Date: 20-05-2012 Time: 23:44
 */
public class SongFile {

	private final MPDFile file;
	
	private List< SongFile > childrens;

	public SongFile( MPDFile file ) {
		this.file = file;
	}

	public List<SongFile> getChildrens() {
		return childrens;
	}

	public void setChildrens( List<SongFile> childrens ) {
		this.childrens = childrens;
	}

	public void addFile( SongFile file ) {
		if ( childrens == null ) {
			childrens = new LinkedList<SongFile>();
		}
		childrens.add( file );
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	public MPDFile getFile() {
		return file;
	}

	@Override
	public String toString() {
		return file.getName();
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( o == null || getClass() != o.getClass() ) return false;

		SongFile songFile = ( SongFile ) o;

		if ( file != null ? !file.equals( songFile.file ) : songFile.file != null ) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return file != null ? file.hashCode() : 0;
	}
}
