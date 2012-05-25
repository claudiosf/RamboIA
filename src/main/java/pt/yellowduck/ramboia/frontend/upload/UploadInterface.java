package pt.yellowduck.ramboia.frontend.upload;

import pt.yellowduck.ramboia.frontend.RamboIAView;

import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.SucceededListener;

public interface UploadInterface extends RamboIAView< UploadController >, SucceededListener, FailedListener {

	public interface UploadPresenter {

	}
	
}
