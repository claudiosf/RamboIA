package pt.yellowduck.ramboia.frontend.upload;

import java.io.OutputStream;

import pt.yellowduck.ramboia.RamboiaApplication;
import pt.yellowduck.ramboia.frontend.RamboIAController;

public class UploadController extends RamboIAController< UploadInterface > implements UploadInterface.UploadPresenter{

	private static final long serialVersionUID = 1L;

	public UploadController( UploadView view, RamboiaApplication application ) {
		super( view, application );
	}

	public OutputStream uploadFile(String filename, String mimeType) {
		// TODO Auto-generated method stub
		return null;
	}

}
