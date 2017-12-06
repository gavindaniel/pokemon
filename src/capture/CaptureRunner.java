package capture;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import battle.BattleLogic;
import items.MasterBall;
import items.UltraBall;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Trainer;
import pokemon.Bulbasaur;
import pokemon.Caterpie;
import pokemon.Charmander;
import pokemon.Eevee;
import pokemon.Electrode;
import pokemon.Flareon;
import pokemon.Ivysaur;
import pokemon.Luxio;
import pokemon.Pikachu;
import pokemon.Pokemon;
import pokemon.Psyduck;
import pokemon.Squirtle;
import pokemon.Vaporeon;

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
		trainer1.addItem(new MasterBall());
		trainer1.addItem(new UltraBall());
		capture=new Capture(new Vaporeon(), trainer1);
		captureView = new CaptureView(capture, SCENE_WIDTH, SCENE_HEIGHT,window);
		window.setCenter((Node) captureView);
		stage.setScene(scene);
		stage.show();
		
		
	}
}
