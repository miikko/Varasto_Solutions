package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

import interfaces.MenuView_IF;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
			primaryStage.setTitle("Käyttäjän GUI");

			catalogListView = new ListView<String>(catalogObsList);
			catalogListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					controller.updateQuantity(newValue);
				}

			});

			quantityListView = new ListView<Integer>();

			deliveryStatusLbl = new Label("No deliveries in progress.");
			confirmBtn = new Button("Confirm");
			confirmBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					if (catalogListView.getSelectionModel().getSelectedIndex() >= 0
							&& quantityListView.getSelectionModel().getSelectedIndex() >= 0) {
						
						controller.startDelivery();
					}
				}

			});
			confirmBtn.setMinWidth(200);
			VBox vBox = new VBox(20);
			vBox.getChildren().add(deliveryStatusLbl);
			vBox.getChildren().add(confirmBtn);
			vBox.setMinWidth(200);
			VBox.setMargin(confirmBtn, new Insets(300,0,0,0));
			
			updateFeedLbl = new Label("Inventory updates will be displayed here.");

			GridPane root = new GridPane();
			root.setAlignment(Pos.CENTER);
			root.setVgap(20);
			root.setHgap(20);
			root.add(catalogListView, 0, 0);// sarake, rivi
			root.add(quantityListView, 1, 0);
			root.add(vBox, 2, 0);
			root.add(updateFeedLbl, 3, 0);
			Scene scene = new Scene(root, 1200, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void updateFeed(String message) {
		updateFeedLbl.setText(message);
	}

	@Override
	public void displayItemQuantity(int quantity) {
		List<Integer> nums = new ArrayList<>();
		for (int i = 1; i <= quantity; i++) {
			nums.add(i);
		}
		quantityObsList = FXCollections.observableArrayList(nums);
		quantityListView.setItems(quantityObsList);
	}

	@Override
	public void updateDeliveryStatus(String message) {
		deliveryStatusLbl.setText(message);
	}
}
