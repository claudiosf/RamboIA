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

	public OutputStream uploadFile(String filename, String mimeType) {
		OutputStream result = null;
		try {
			File outFile = new File(MUSIC_FOLDER + filename);
			outFile.createNewFile();
			result = new FileOutputStream(outFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void showNotification(String string) {
		getApplication().getMainWindow().showNotification(string);
	}
}
