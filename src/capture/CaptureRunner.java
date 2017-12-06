package capture;

import java.util.Observer;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Trainer;
import pokemon.Caterpie;


public class CaptureRunner extends Application {

	private Capture capture;
	private Trainer trainer1;
	private Observer captureView;

	private BorderPane window;

	public static final double SCENE_WIDTH = 800;
	public static final double SCENE_HEIGHT = 550;
	
	private static final Image bgImg = new Image("file:images/battle/battle-background.png", false);


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		//Initialize main border pane and scene
		stage.setTitle("Pokemon Capture");
		window = new BorderPane();
		Scene scene = new Scene(window, SCENE_WIDTH, SCENE_HEIGHT);
		//Add battle view observer
		trainer1=new Trainer("Person");
		capture=new Capture(new Caterpie(), trainer1);
//		captureView = new CaptureView(capture, SCENE_WIDTH, SCENE_HEIGHT,window);
		window.setCenter((Node) captureView);
		stage.setScene(scene);
		stage.show();
		
		
	}
}
