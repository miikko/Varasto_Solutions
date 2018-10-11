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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import interfaces.MenuView_IF;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
/**
 * This class is responsible of creating and displaying the graphical user interface(GUI).
 * @author Miikka Oksanen
 *
 */

public class MenuView extends Application implements MenuView_IF {

	private Label updateFeedLbl;
	public Label deliveryStatusLbl;
	public Button confirmBtn;
	private Button updateBtn;
	private ListView<String> catalogListView;
	private ListView<Integer> quantityListView;
	private ObservableList<String> catalogObsList;
	private ObservableList<Integer> quantityObsList;
	private MenuController controller;
	private BorderPane borderPane;

	private VBox leftBox;
	private VBox leftBoxConnected;
	private Button leftBoxButton;

	private GridPane centerGrid;

	/**
	 * This method is executed first when the program starts. It instantiates some objects that are needed later on in the program execution.
	 */
	public void init() {
		controller = new MenuController(this);
		catalogObsList = controller.initializeCatalog();
	}

	/**
	 * Calls the methods that build the GUI.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Käyttäjän GUI");
			borderPane = new BorderPane();
			createLeftBox();
			borderPane.setLeft(leftBox);
			createMainGrid();
			Scene scene = new Scene(borderPane, 1200, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
			ConfirmBtnThread btnThread = new ConfirmBtnThread(this);
			btnThread.start();
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

	// Method for exception popup window
	@Override
	public void popExceptionAlert(String headerText, String contentText, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	@Override
	public void setConnected() {
		// TODO Auto-generated method stub
		borderPane.setCenter(centerGrid);
		createLeftBoxConnected();
		borderPane.setLeft(leftBoxConnected);
	}

	// LeftBox of BorderPane when not Conneted
	private void createLeftBox() {
		leftBox = new VBox();
		leftBox.getStyleClass().add("leftBox");
		leftBoxButton = new Button("Connect");
		leftBoxButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.connectRobot();
			}

		});
		leftBox.getChildren().addAll(leftBoxButton);
	}

	// LeftBox of BorderPane when Connected
	private void createLeftBoxConnected() {
		leftBoxConnected = new VBox();
		leftBoxConnected.getStyleClass().add("leftBox");
		Label label = new Label("Connected");
		leftBoxConnected.getChildren().add(label);
	}

	// MainGrid of view, Center of BorderPane
	private void createMainGrid() {
		catalogListView = new ListView<String>(catalogObsList);
		catalogListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (newValue != null) {
					controller.updateQuantity(newValue);
				}
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

					controller.startDelivery(catalogListView.getSelectionModel().getSelectedItem(),
							quantityListView.getSelectionModel().getSelectedItem());
				}
			}

		});
		confirmBtn.setMinWidth(200);
		updateBtn = new Button("Update");
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				catalogObsList = controller.initializeCatalog();
				catalogListView.setItems(catalogObsList);
			}
			
		});
		updateBtn.setMinWidth(200);
		VBox vBox = new VBox(20);
		vBox.getChildren().add(deliveryStatusLbl);
		vBox.getChildren().add(confirmBtn);
		vBox.getChildren().add(updateBtn);
		vBox.setMinWidth(200);
		VBox.setMargin(confirmBtn, new Insets(300, 0, 0, 0));

		updateFeedLbl = new Label("Inventory updates will be displayed here.");

		centerGrid = new GridPane();
		centerGrid.setAlignment(Pos.CENTER);
		centerGrid.setVgap(20);
		centerGrid.setHgap(20);
		centerGrid.add(catalogListView, 0, 0);// sarake, rivi
		centerGrid.add(quantityListView, 1, 0);
		centerGrid.add(vBox, 2, 0);
		centerGrid.add(updateFeedLbl, 3, 0);
	}
	
	public void removeItemFromCatalog(String itemName) {
		catalogObsList.remove(itemName);
	}
	
	public void flushQuantityList() {
		quantityObsList.clear();
	}

	@Override
	public void stop() {
		controller.terminateSessionFactory();
	}
}
