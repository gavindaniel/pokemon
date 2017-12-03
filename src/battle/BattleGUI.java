package battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Trainer;
import pokemon.Attack;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;
import pokemon.Squirtle;

public class BattleGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	//Declare Model
	private BattleLogicForView battle;

	//Declare JavaFX nodes
	private BorderPane window;
	private HBox pokeSelectBox;
	private VBox selectButtonBox;
	private Button pokeSelectButton;
	private Label selectLabel;
	private GridPane attackPane;
	
	//Observable Lists
	private OwnedPokeTable ownedPokeTable;
	private ListView<String> selectedPokeListView;
	private ObservableList<String> selectedPokemonList;

	//Declare observers
	private Observer battleView;

	//Window Size
	public static final double SCENE_WIDTH = 800;
	public static final double SCENE_HEIGHT = 550 + 144;


	@Override
	public void start(Stage stage) throws Exception {

		//Initialize main border pane and scene
		stage.setTitle("Pokemon Battle");
		window = new BorderPane();
		Scene scene = new Scene(window, SCENE_WIDTH, SCENE_HEIGHT);

		//Initialize logic model
		initializeTrainersAndBattle();
		
		//Trainers select pokemon
		initializeGUINodes();
//		setupPokeSelectionMenu(battle.getTrainer1());
		
		//Add battle view observer
		battleView = new BattleView(battle, SCENE_WIDTH, SCENE_HEIGHT);
		battle.addObserver(battleView);
		
		/******************************For Quick Testing************************************/
		  
		List<Pokemon> pokeList1 = new ArrayList<>();
		pokeList1.add(new Pikachu());
		pokeList1.add(new Charmander());
		pokeList1.add(new Bulbasaur());
		for(Pokemon p : pokeList1) {
			p.getBattleAnimation().setBattleView((BattleView) battleView);
		}

		List<Pokemon> pokeList2 = new ArrayList<>();
		pokeList2.add(new Charmander());
		pokeList2.add(new Pikachu());
		pokeList2.add(new Squirtle());
		
		for(Pokemon p : pokeList2) {
			p.getBattleAnimation().setBattleView((BattleView) battleView);
		}

		battle.getTrainer1().setBattlePokemonList(pokeList1);
		battle.getTrainer2().setBattlePokemonList(pokeList2);
		 
		/**********************************************************************************/

		setViewToBattle();
		
		//Run Battle
		runBattle();
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void initializeGUINodes() {
		
		selectButtonBox = new VBox();
		pokeSelectBox = new HBox();
		
	}

	private void initializeTrainersAndBattle() {

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

		Trainer trainer1 = new Trainer("Player1");
		trainer1.setOwnedPokemonList(pokeList1);

		Trainer trainer2 = new Trainer("Player2");
		trainer2.setOwnedPokemonList(pokeList2);

		battle = new BattleLogicForView(trainer1, trainer2);
	}
	
	/**
	 * Changes from selection view to battle view.
	 */
	private void setViewToBattle() {
	    
		removePokeSelectionMenu();
	    
	    window.setCenter((Node) battleView);
	    
//	    setupAttackPane();
	  }
	
	private void setupAttackPane() {
		
		//Initializing Attack Label
		Label attackLabel = new Label(battle.getActiveTrainer() + ". Your Turn.");
		attackLabel.setStyle("-fx-font-size: 14pt");
		
		//Initializing attack buttons
		Button attack1 = new Button("Attack 1");
		Button attack2 = new Button("Attack 2");
		Button attack3 = new Button("Attack 3");
		Button attack4 = new Button("Attack 4");
		
		//Adding button to grid pane
		attackPane = new GridPane();
		attackPane.add(attackLabel, 0, 0);
		attackPane.add(attack1, 2, 0);
		attackPane.add(attack2, 3, 0);
		attackPane.add(attack3, 4, 0);
		attackPane.add(attack4, 5, 0);
		
		//formating grid pane
		attackPane.setHgap(10.0);
		
		window.setTop(attackPane);
	}
	
	private void updateAttackPane(Trainer trainer) {
		
		int i = 0;
		
		for (Node node : attackPane.getChildren()) {
			
			if (node instanceof Button) {
				((Button) node).setText(trainer.getActiveBattlePokemon().getAttackList().get(i).getName());
				i++;
			}
			
			else {
				((Label) node).setText(trainer.getName() + ". Your Turn.");
			}
		}
	}
	
	private void setupPokeSelectionMenu(Trainer trainer) {
		
		selectLabel = new Label(trainer.getName() + " select your battle pokemon");
		selectLabel.setStyle("-fx-font-size: 18pt");
		
		setupOwnedPokeTable(trainer);
		setupSelectedPokeList(trainer);
		
		//Set up Selection Button
		pokeSelectButton = new Button(">");
		selectButtonBox.getChildren().add(pokeSelectButton);
		selectButtonBox.setSpacing(15);
		
		//Register button handler
		pokeSelectButton.setOnAction(new SelectButtonListener(trainer));
		
		// Add lists and button to selection box
		pokeSelectBox.getChildren().add(ownedPokeTable);
		pokeSelectBox.getChildren().add(selectButtonBox);
		pokeSelectBox.getChildren().add(selectedPokeListView);
		pokeSelectBox.setSpacing(30);
		pokeSelectBox.setPadding(new Insets(40, 0, 0, 0));

		// Add Box to bottom of borderPane
		window.setTop(selectLabel);
		window.setBottom(pokeSelectBox);
		BorderPane.setMargin(pokeSelectBox, new Insets(0, 0, 0, 100));
		BorderPane.setMargin(selectLabel, new Insets(30, 0, 0, 100));
	}
	
	/**
	 * Removes pokemon selection menu
	 */
	private void removePokeSelectionMenu() {
	    window.setBottom(null);
	    window.setTop(null);
	    pokeSelectBox.getChildren().clear();
	    selectButtonBox.getChildren().clear();
	}
	
	private void setupOwnedPokeTable(Trainer trainer) {
		
		//Set up list view to select pokemon
		ObservableList<Pokemon> ownedPokemonList = FXCollections.observableArrayList(trainer.getOwnedPokemonList());
		ownedPokeTable = new OwnedPokeTable();
		ownedPokeTable.setItems(ownedPokemonList);

	}
	
	private class OwnedPokeTable extends TableView<Pokemon> {
		
		public OwnedPokeTable() {
			TableColumn<Pokemon, String> nameColumn = new TableColumn<>("Pokemon");
			nameColumn.setMaxWidth(250);
			nameColumn.setMinWidth(250);
			nameColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, String>("name"));
			this.getColumns().add(nameColumn);
		}
	}
	
	private void setupSelectedPokeList(Trainer trainer) {

		//Set up list view to select pokemon
		selectedPokemonList = FXCollections.observableArrayList();
		selectedPokeListView = new ListView<>();

		//Add pokemon from trainers owned list
		for (Pokemon poke : trainer.getBattlePokemonList()) {
			selectedPokemonList.add(poke.getName());
		}
		selectedPokeListView.setItems(selectedPokemonList);
	}

	private class SelectButtonListener implements EventHandler<ActionEvent> {

		private Pokemon chosenPoke;
		private List<Pokemon> battleList;
		
		public SelectButtonListener(Trainer trainer) {
			battleList = trainer.getBattlePokemonList();
		}

		@Override
		public void handle(ActionEvent event) {

				chosenPoke = ownedPokeTable.getSelectionModel().getSelectedItem();
	
				if (!battleList.contains(chosenPoke)) {
					chosenPoke.getBattleAnimation().setBattleView((BattleView) battleView); //Set battleView for pokemon
					battleList.add(chosenPoke);
					selectedPokemonList.add(chosenPoke.getName());
				}
				
				else {
					String message = chosenPoke.getName() + " is already chosen. Pick another one.";
					newAlertMessage("Error", message);
				}
				
				if (checkReadyForBattle()) {
					setViewToBattle();
					runBattle();
				}
				
				else if (battleList.size() == 3) {
					removePokeSelectionMenu();
					setupPokeSelectionMenu(battle.getTrainer2()); //Setup menu for next trainer
				}
		}
	}
	
	private boolean checkReadyForBattle() {
		
		List<Pokemon> battleList1 = battle.getTrainer1().getBattlePokemonList();
		List<Pokemon> battleList2 = battle.getTrainer2().getBattlePokemonList();
		
		if (battleList1.size() == 3 && battleList2.size() == 3) {
			return true;
		}
		
		else return false;
	}
	
	private void switchToGameOverMenu() {
		
		window.setCenter(null);
		window.setTop(null);
		attackPane.getChildren().clear();
		
		Trainer winner = (battle.isPokemonDrained(battle.getTrainer1().getActiveBattlePokemon())) 
				? battle.getTrainer2() : battle.getTrainer1();
		
		Label gameOver = new Label(winner.getName() + " is the winner !!");
		window.setCenter(gameOver);
	}
	
	/**
	 * Template for generating alert windows.
	 * 
	 * @param title
	 *            title of alert box
	 * @param message
	 *            body of alert
	 */
	private void newAlertMessage(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	
	/**
	 * Top level function to handle battle sequence.
	 */
	public void runBattle() {
		
		battle.initializeActiveBattlePokemon();

		battle.setActiveTrainer(battle.determineWhoStarts());
		
//		updateAttackPane(battle.getActiveTrainer());
//		registerAttackButtonHandlers();

//		boolean isPokemonDrained = false;
//		while (!battle.isBattleOver()) {
//
//			if (isPokemonDrained) {
//				battle.setActiveTrainer(battle.determineWhoStarts());
//			}
//
		}

//		System.out.println("\nBattle is over.");
//
//		if (areAllPokemonDrained(trainer1.getBattlePokemonList())) {
//			System.out.println(trainer2.getName() + " has defeated " + trainer1.getName());
//		} else {
//			System.out.println(trainer1.getName() + " has defeated " + trainer2.getName());
//		}
//	}
	
	private void registerAttackButtonHandlers() {
		
		for (Node node : attackPane.getChildren()) {	
			if (node instanceof Button) {
				((Button) node).setOnAction(new AttackButtonListener());
			}
		}
	}
	
	private class AttackButtonListener implements EventHandler<ActionEvent> {
		
		private Label label;
		private Trainer attackTrainer;
		private Trainer defendTrainer;
		
		public AttackButtonListener() {
			label = (Label) attackPane.getChildren().get(0);			
		}

		@Override
		public void handle(ActionEvent event) {
			
			//Get Text from clicked button
			String buttonText = ((Button) event.getSource()).getText();
			
			//Identify current active trainer
			attackTrainer = battle.getActiveTrainer();
			defendTrainer = (attackTrainer == battle.getTrainer1()) 
					? battle.getTrainer2() : battle.getTrainer1();
			
			Attack chosenAttack = null;
			
			for (Attack attack : attackTrainer.getActiveBattlePokemon().getAttackList()) {
				if (buttonText.equals(attack.getName())) {
					chosenAttack = attack;
					break;
				}
			}
			
			battle.applyAttack(chosenAttack, attackTrainer.getActiveBattlePokemon(), defendTrainer.getActiveBattlePokemon());
			label.setText(attackTrainer.getActiveBattlePokemon() + " used " + chosenAttack.getName());
			if (battle.isBattleOver()) switchToGameOverMenu();
			else {
				Trainer active = (battle.getActiveTrainer() == battle.getTrainer1())
						? battle.getTrainer2() : battle.getTrainer1();
				battle.setActiveTrainer(active);
				updateAttackPane(battle.getActiveTrainer());
			}
		}
	}
}
