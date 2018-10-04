package application;

public class ConfirmBtnThread extends Thread {

	private MenuView view;
	private boolean quit;

	public ConfirmBtnThread(MenuView view) {
		this.view = view;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void terminate() {
		quit = true;
	}
}
