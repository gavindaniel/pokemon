package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import battle.BattleLogic;
import battle.BattleLogicForView;
import battle.BattleView;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Trainer;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;
import pokemon.Squirtle;

public class BattleViewRunner extends Application {

	private BattleLogicForView battle;
	private Trainer trainer1;
	private Trainer trainer2;
	private Observer battleView;

	private BorderPane window;

	public static final double SCENE_WIDTH = 800;
	public static final double SCENE_HEIGHT = 550;
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		//Initialize main border pane and scene
		stage.setTitle("Pokemon Battle");
		window = new BorderPane();
		Scene scene = new Scene(window, SCENE_WIDTH, SCENE_HEIGHT);
		
		//Initialize logic model
		initializeTrainers();
		battle = new BattleLogicForView(trainer1, trainer2);
		
		//Add battle view observer
		battleView = new BattleView(battle, SCENE_WIDTH, SCENE_HEIGHT);
		battle.addObserver(battleView);
		
		window.setCenter((Node) battleView);
		stage.setScene(scene);
		stage.show();
		
		
	}

	private void initializeTrainers() {
	
		List<Pokemon> pokeList1 = new ArrayList<>();
		pokeList1.add(new Pikachu());
		pokeList1.add(new Charmander());
		pokeList1.add(new Bulbasaur());
		pokeList1.add(new Squirtle());

		List<Pokemon> pokeList2 = new ArrayList<>();
		pokeList2.add(new Pikachu());
		pokeList2.add(new Charmander());
		pokeList2.add(new Bulbasaur());
		pokeList2.add(new Squirtle());

		trainer1 = new Trainer("Player1");
		trainer1.setOwnedPokemonList(pokeList1);

		trainer2 = new Trainer("Player2");
		trainer2.setOwnedPokemonList(pokeList2);

		BattleLogic battle = new BattleLogic(trainer1, trainer2);
//		battle.runBattle();
	}

	
	
	
	
}