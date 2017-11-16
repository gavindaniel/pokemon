package menus;

import java.util.Observable;
import java.util.Observer;

import controller.Settings;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainMenu extends BorderPane implements Observer {

	private Settings settings;
	private GridPane gp;
	private Button newGame;
	
	public MainMenu() {
		settings = new Settings();
		gp = new GridPane();
		initializePane();
	}
	
	private void initializePane() {
		gp.setPrefHeight(settings.getHeight("scene"));
		gp.setPrefWidth(settings.getWidth("scene"));
		
		newGame = new Button("New Game");
		GridPane.setConstraints(newGame, 0, 1);
		
		gp.getChildren().addAll(newGame);
		
		this.setCenter(gp);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
