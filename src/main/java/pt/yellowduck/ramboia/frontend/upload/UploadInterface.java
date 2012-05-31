package pt.yellowduck.ramboia.frontend.upload;

import java.io.File;

import pt.yellowduck.ramboia.frontend.RamboIAView;

public interface UploadInterface extends RamboIAView< UploadController > {
	
	public interface UploadPresenter {
		public Boolean uploadFile(File file, String mimeType);
		public void showNotification(String msg);
	}
	
}
