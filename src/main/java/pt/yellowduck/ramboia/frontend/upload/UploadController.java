package pt.yellowduck.ramboia.frontend.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.frontend.RamboIAController;

public class UploadController extends RamboIAController< UploadInterface > implements UploadInterface.UploadPresenter{

	private static final long serialVersionUID = 1L;

	private static String MUSIC_FOLDER = "/var/lib/mpd/music/";

	public UploadController( UploadView view, RamboiaApplication application ) {
		super( view, application );
	}

	public Boolean uploadFile(File file, String filename, String mimeType) {
		// Destination directory
		File dir = new File(MUSIC_FOLDER);
		// Move file to new directory
		return file.renameTo(new File(dir, filename));
	}

	public void showNotification(String string) {
		getApplication().getMainWindow().showNotification(string);
	}
}
