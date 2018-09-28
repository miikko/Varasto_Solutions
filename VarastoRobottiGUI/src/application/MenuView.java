package application;
	
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javax.swing.GroupLayout.Alignment;

import interfaces.MenuView_IF;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class MenuView extends Application implements MenuView_IF {
	
	private Label updateFeedLbl;
	private Label deliveryStatusLbl;
	private Button confirmBtn;
	private ListView<String> catalogListView;
	private ListView<Integer> quantityListView;
	private ObservableList<String> catalogObsList;
	private ObservableList<Integer> quantityObsList;
	private MenuController controller;
	
	public void init() {
		controller = new MenuController(this);
		catalogObsList = controller.initializeCatalog();
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			updateFeedLbl = new Label();
			deliveryStatusLbl = new Label();
			catalogListView = new ListView<String>(catalogObsList);
			confirmBtn = new Button("Confirm");
			confirmBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					
				}

			});
			
			GridPane root = new GridPane();
			root.setAlignment(Pos.CENTER);
			root.setVgap(10);
			root.setHgap(10);
			root.add(confirmBtn, 0, 0);
			root.add(catalogListView, 1, 0);
			Scene scene = new Scene(root,600,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void updateFeed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayItemQuantity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeliveryStatus() {
		// TODO Auto-generated method stub
		
	}
}
