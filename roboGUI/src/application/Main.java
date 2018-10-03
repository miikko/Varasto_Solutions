package application;
	
import java.io.PrintWriter;
import java.io.StringWriter;

import controller.Controller;
import controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;


public class Main extends Application implements GUI{
	
	private Controller controller;
	private BorderPane root;
	
	private VBox leftBox;
	private VBox leftBoxConnected;
	private Button topBoxButton;
	private Button test;
	
	private VBox centerBox;
	private HBox centerBoxH1;
	private Button addButton;
	private Label packets;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new BorderPane();
			createLeftBox();
			root.setLeft(leftBox);
			Scene scene = new Scene(root,600,350);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
	public void init() {
		controller = new MainController(this);
	}
	
	public void createLeftBox() {
		leftBox = new VBox();
		leftBox.getStyleClass().add("leftBox");
		topBoxButton = new Button("Connect");
		topBoxButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.connectRobot();
			}
			
		});
		leftBox.getChildren().addAll(topBoxButton);
	}
	
	public void createLeftBoxConnected() {
		leftBoxConnected = new VBox();
		leftBoxConnected.getStyleClass().add("leftBox");
		Label label = new Label("Connected");
		leftBoxConnected.getChildren().add(label);
	}
	
	public void createCenterBox() {
		centerBox  = new VBox();
		centerBoxH1 = new HBox();
		centerBoxH1.getStyleClass().add("centerBoxH1");
		
		addButton = new Button("add packets");
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		packets = new Label("0");
		centerBoxH1.getChildren().addAll(addButton, new Label("Packets waiting for transfer: "), packets);
		centerBox.getChildren().add(centerBoxH1);
	}

	@Override
	public void setConnected() {
		// TODO Auto-generated method stub
		createLeftBoxConnected();
		root.setLeft(leftBoxConnected);
		createCenterBox();
		root.setCenter(centerBox);
	}

	
	//ExceptionPopupWindow
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
	
}
