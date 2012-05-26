package pt.yellowduck.ramboia.frontend.upload;

import java.io.File;
import java.io.OutputStream;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;

public class UploadView extends Panel implements UploadInterface {

	private static final long serialVersionUID = 1L;
	private UploadController presenter;
	private Upload upload;

	public UploadView() {
		setupComponents();
		setupLayout();
	}

	private void setupComponents() {
		setCaption("Get some new tunes");
		
		upload = new Upload("", new Upload.Receiver() {

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				OutputStream result = null;
				if(presenter != null ){
					result = presenter.uploadFile(filename, mimeType);
				}
				return result;
			}
		});
		upload.addListener(new Upload.SucceededListener() {
			
			@Override
			public void uploadSucceeded(SucceededEvent event) {
				presenter.showNotification("New tune added successfully!!");
			}
		});
		upload.addListener(new Upload.FailedListener() {
			
			@Override
			public void uploadFailed(FailedEvent event) {
				presenter.showNotification("Failed to add new tune!!");
			}
		});
		upload.setButtonCaption("Rock on");
	}

	private void setupLayout() {
		addComponent(upload);
		setWidth( "400px" );
	}

	@Override
	public void uploadFailed(FailedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPresenter(UploadController presenter) {
		this.presenter = presenter;		
	}
}
