package application;

/**
 * Thread for handling the confirm-button.
 * @author Miikka Oksanen
 *
 */
public class ConfirmBtnThread extends Thread {

	private MenuView view;
	private boolean quit;

	/**
	 * Constructor.
	 * @param view
	 */
	public ConfirmBtnThread(MenuView view) {
		this.view = view;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!quit) {
			
			if (view.confirmBtn != null) {
				
				if (MenuCommunicationModel.transferInProgress) {
					view.confirmBtn.setDisable(true);
				} else {
					view.confirmBtn.setDisable(false);
				}

				try {
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Terminates the thread.
	 */
	public void terminate() {
		quit = true;
	}
}
