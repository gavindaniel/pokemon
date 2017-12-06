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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Trainer;
import pokemon.Attack;
import pokemon.Bulbasaur;
import pokemon.Charmander;
import pokemon.Electrode;
import pokemon.Flareon;
import pokemon.Pikachu;
import pokemon.PokeType;
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
	private VBox pokeSelectBox;
	private HBox pokeSwitchBox;
	private HBox selectButtonBox;
	
	//Observable Lists
	private PokeTable pokeTable;
	private ListView<String> selectedPokeListView;
	private ObservableList<String> selectedPokemonList;

	//Declare observers
	private Observer battleView;

	private Stage mainStage;
	private Stage battleStage;
	
	//Window Size
	public static final double SCENE_WIDTH = 800;
	public static final double SCENE_HEIGHT = 550 + 144;
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		battleStage = stage;
		//Initialize main border pane and scene
		stage.setTitle("Pokemon Battle");
		window = new BorderPane();
		Scene scene = new Scene(window, SCENE_WIDTH, SCENE_HEIGHT);

		//Initialize logic model
		initializeTrainersAndBattle();
		
		//Trainers select pokemon
//		setupPokeSelectionMenu(battle.getActiveTrainer());
		
		//Add battle view observer
		battleView = new BattleView(battle, SCENE_WIDTH, SCENE_HEIGHT);
		battle.addObserver(battleView);
		
		/******************************For Quick Testing************************************/
		  
		List<Pokemon> pokeList1 = new ArrayList<>();
		pokeList1.add(new Electrode());
		pokeList1.add(new Charmander());
		pokeList1.add(new Bulbasaur());
		for(Pokemon p : pokeList1) {
			p.getBattleAnimation().setBattleView((BattleView) battleView);
		}

		List<Pokemon> pokeList2 = new ArrayList<>();
		pokeList2.add(new Flareon());
		pokeList2.add(new Pikachu());
		pokeList2.add(new Squirtle());
		
		for(Pokemon p : pokeList2) {
			p.getBattleAnimation().setBattleView((BattleView) battleView);
		}

		battle.getActiveTrainer().setBattlePokemonList(pokeList1);
		battle.getOppTrainer().setBattlePokemonList(pokeList2);
		 
		/**********************************************************************************/

		setViewToBattle();
		
		//Run Battle
		runBattle();
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	/**
	 * Sets the main game stage.
	 * @param mainStage the main game stage
	 */
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
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
		window.setCenter((Node) battleView);
	}
	
	/**
	 * Displayed at start of battle.
	 * Menu for selecting 3 pokemon to fight.
	 * @param trainer The trainer to display menu for.
	 */
	private void setupPokeSelectionMenu(Trainer trainer) {
		
		selectButtonBox = new HBox();
		pokeSelectBox = new VBox();
		
		Label selectLabel = new Label(trainer.getName() + " select your battle pokemon");
		selectLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: brown");
		
//		Label chooseLabel = new Label("Available Pokemon");
//		chooseLabel.setStyle("-fx-font-size: 14pt");
//		
//		Label chosenLabel = new Label("Chosen Pokemon");
//		chosenLabel.setStyle("-fx-font-size: 14pt");
		
		setupPokeTable(trainer.getOwnedPokemonList());
		setupSelectedPokeList(trainer);
		
		//Set up Selection Button
		Button pokeSelectButton = new Button(">>");
		selectButtonBox.getChildren().add(pokeSelectButton);
		selectButtonBox.setSpacing(30);
		
		//Register button handler
		pokeSelectButton.setOnAction(new SelectButtonListener(trainer));
		
		//Set width of pokeSelectBox
		pokeSelectBox.setMaxWidth(570);
		
		//Add lists and button to selection box
		pokeSelectBox.getChildren().add(pokeTable);
		pokeSelectBox.getChildren().add(selectButtonBox);
		pokeSelectBox.getChildren().add(selectedPokeListView);
		pokeSelectBox.setSpacing(30);

		//Add labels
		selectButtonBox.getChildren().add(selectLabel);
		
		// Add Box to center of borderPane
		window.setCenter(pokeSelectBox);
		BorderPane.setMargin(pokeSelectBox, new Insets(40, 40, 40, 40));
	}
	
	/**
	 * Removes pokemon selection menu
	 */
	private void removePokeSelectionMenu() {
	    window.setCenter(null);
	    pokeSelectBox.getChildren().clear();
	    selectButtonBox.getChildren().clear();
	}
	
	private void setupPokeTable(List<Pokemon> pokeList) {
		
		//Set up list view to select pokemon
		ObservableList<Pokemon> obsPokeList = FXCollections.observableArrayList(pokeList);
		pokeTable = new PokeTable();
		pokeTable.setItems(obsPokeList);

	}
	
	/**
	 * Allows user to switch pokemon during battle
	 * @param trainer The trainer activating the switch
	 */
	private void setupPokeSwitchMenu(Trainer trainer) {
		
		pokeSwitchBox = new HBox();
		
		Label switchLabel = new Label(trainer.getName() + ", Choose your new pokemon");
		switchLabel.setStyle("-fx-font-size: 14pt; -fx-text-fill: brown");
		
		setupPokeTable(trainer.getBattlePokemonList());
		
		//Set up Switch Button
		Button switchButton = new Button(">>");
		switchButton.setStyle("-fx-font-size: 18pt;");
		
		//Register button handler
		switchButton.setOnAction(new SwitchButtonListener(trainer));
		
		//Set width of pokeTable
		VBox tableBox = new VBox();
		tableBox.getChildren().add(pokeTable);
		tableBox.setMaxWidth(570);
		
		//Add lists and button to switch box
		pokeSwitchBox.getChildren().add(tableBox);
		pokeSwitchBox.getChildren().add(switchButton);
		pokeSwitchBox.setSpacing(30);
		
		// Add Box to center of borderPane
		window.setCenter(pokeSwitchBox);
		BorderPane.setMargin(pokeSwitchBox, new Insets(40, 40, 40, 40));
	}
	
	private void removePokeSwitchMenu() {
		
		window.setCenter(null);
		pokeSwitchBox.getChildren().clear();
	}
	
	private class PokeTable extends TableView<Pokemon> {
		
		public PokeTable() {
			//set-up columns
			TableColumn<Pokemon, String> nameColumn = new TableColumn<>("Pokemon");
			TableColumn<Pokemon, PokeType> typeColumn = new TableColumn<>("Type");
			TableColumn<Pokemon, Integer> currHPColumn = new TableColumn<>("currHP");
			TableColumn<Pokemon, Integer> maxHPColumn = new TableColumn<>("maxHP");
			TableColumn<Pokemon, Integer> attackColumn = new TableColumn<>("attack");
			TableColumn<Pokemon, Integer> defenseColumn = new TableColumn<>("defense");
			TableColumn<Pokemon, Integer> specialColumn = new TableColumn<>("special");
			TableColumn<Pokemon, Integer> speedColumn = new TableColumn<>("speed");
			
			//Get column values
			nameColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, String>("name"));
			typeColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, PokeType>("primaryType"));
			currHPColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("currHP"));
			maxHPColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("maxHP"));
			attackColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("attack"));
			defenseColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("defense"));
			specialColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("special"));
			speedColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("speed"));
			
			//Add columns to table
			this.getColumns().add(nameColumn); 
			this.getColumns().add(typeColumn); 
			this.getColumns().add(currHPColumn); 
			this.getColumns().add(maxHPColumn); 
			this.getColumns().add(attackColumn); 
			this.getColumns().add(defenseColumn); 
			this.getColumns().add(specialColumn);
			this.getColumns().add(speedColumn);
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
	
	private boolean checkReadyForBattle() {
		
		List<Pokemon> battleList1 = battle.getActiveTrainer().getBattlePokemonList();
		List<Pokemon> battleList2 = battle.getOppTrainer().getBattlePokemonList();
		
		if (battleList1.size() == 3 && battleList2.size() == 3) {
			return true;
		}
		
		else return false;
	}
	
	private void switchToGameOverMenu() {
		
		window.setCenter(null);
		window.setTop(null);
		
		Trainer winner = battle.getAttackTrainer();
		
		Label gameOver = new Label(winner.getName() + " is the winner !!");
		window.setCenter(gameOver);
		
		//Switch back to main game
//		battleStage.hide();
//		mainStage.show();
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
		
		registerMainSelectHandler();

//		boolean isPokemonDrained = false;
//		while (!battle.isBattleOver()) {
//
//			if (isPokemonDrained) {
//				battle.setActiveTrainer(battle.determineWhoStarts());
//			}
		}
	
	/**
	 * Restart battle after new pokemon is entered.
	 * Either because of a user-triggered switch or when a pokemon faints.
	 */
	private void restartBattle(Pokemon activePoke) {
		
		if (battle.getCurrState() == BattleState.SWITCHING) {
			switchTrainerControl();
			battle.setCurrState(BattleState.IDLE);
			battleView.update(battle, null);
		}
		
		else if (battle.getCurrState() == BattleState.FAINTED) {
			
			// Determines who starts based on speed
			battle.setAttackTrainer(battle.determineWhoStarts());
			battle.setDefendTrainer((battle.getAttackTrainer()== battle.getActiveTrainer()) ? battle.getOppTrainer() : battle.getActiveTrainer());
			
			battle.setCurrState(BattleState.IDLE);
			battleView.update(battle, null);
		}
		
	}
	
	private void runAttack(Attack chosenAttack, Pokemon attackPoke, Pokemon defendPoke) {
		
		battle.setCurrState(BattleState.ATTACKING);
		
		battle.applyAttack(chosenAttack, attackPoke, defendPoke);
		
		if (battle.isBattleOver()) { //Check if battle is over has fainted
			battle.setCurrState(BattleState.END);
			switchToGameOverMenu();
		}
		
		//Check if a pokemon has fainted
		else if (battle.isPokemonDrained(attackPoke) || battle.isPokemonDrained(defendPoke)) {
			battle.setCurrState(BattleState.FAINTED);
		}
		
		else {
			switchTrainerControl();
		}
	}
	
	/**
	 * Switches control from one trainer to the other.
	 */
	private void switchTrainerControl() {
		Trainer temp = battle.getAttackTrainer();
		battle.setAttackTrainer(battle.getDefendTrainer());
		battle.setDefendTrainer(temp);
	}

//		System.out.println("\nBattle is over.");
//
//		if (areAllPokemonDrained(trainer1.getBattlePokemonList())) {
//			System.out.println(trainer2.getName() + " has defeated " + trainer1.getName());
//		} else {
//			System.out.println(trainer1.getName() + " has defeated " + trainer2.getName());
//		}
//	}
	
	private void registerMainSelectHandler() {
		
		//Mouse click handler
		window.setOnMouseClicked((event) -> {
			double x = event.getSceneX();
			double y = event.getSceneY();
			
//			System.out.println();
//			System.out.println("X:" + x);
//			System.out.println();
//			System.out.println("Y:" + y);
			
			
			//IDLE: can select from the four main options.
			if (battle.getCurrState() == BattleState.IDLE) {
				//Fight is chosen. Switch to choose attack menu
				if (x >= 484 && x<= 581 && y>= 585 && y<= 620) {
					((BattleView) battleView).drawAttackMenu(0);
					battle.setCurrState(BattleState.CHOOSE_ATTACK);
				}
				
				//Pokemon is chosen. Switch to choose pokemon menu 
				else if (x >= 484 && x<= 619 && y>= 634 && y<= 668) {
					battle.setCurrState(BattleState.SWITCHING);
					setupPokeSwitchMenu(battle.getAttackTrainer());
				}
			}
			
			//CHOOSE_ATTACK: can select from one of four attacks
			else if (battle.getCurrState() == BattleState.CHOOSE_ATTACK) {
				//Attack1 is chosen
				if (x >= 43 && x<= 243 && y>= 585 && y<= 615) {
					Pokemon attackPoke = battle.getAttackTrainer().getActiveBattlePokemon();
					Pokemon defendPoke = battle.getDefendTrainer().getActiveBattlePokemon();
					Attack currAttack = attackPoke.getAttackList().get(0);
					runAttack(currAttack, attackPoke, defendPoke);
				}
				//Attack2 is chosen
				else if (x >= 302 && x<= 477 && y>= 585 && y<= 615) {
					Pokemon attackPoke = battle.getAttackTrainer().getActiveBattlePokemon();
					Pokemon defendPoke = battle.getDefendTrainer().getActiveBattlePokemon();
					Attack currAttack = attackPoke.getAttackList().get(1);
					runAttack(currAttack, attackPoke, defendPoke);
				}
				//Attack3 is chosen
				else if (x >= 43 && x<= 243 && y>= 631 && y<= 660) {
					Pokemon attackPoke = battle.getAttackTrainer().getActiveBattlePokemon();
					Pokemon defendPoke = battle.getDefendTrainer().getActiveBattlePokemon();
					Attack currAttack = attackPoke.getAttackList().get(2);
					runAttack(currAttack, attackPoke, defendPoke);
				}
				//Attack4 is chosen
				else if (x >= 302 && x<= 477 && y>= 631 && y<= 660) {
					Pokemon attackPoke = battle.getAttackTrainer().getActiveBattlePokemon();
					Pokemon defendPoke = battle.getDefendTrainer().getActiveBattlePokemon();
					Attack currAttack = attackPoke.getAttackList().get(3);
					runAttack(currAttack, attackPoke, defendPoke);
				}
			}
			
			/**
			 * FAINTED: can only select pokemon option to switch to a new pokemon.
			 */
			else if (battle.getCurrState() == BattleState.FAINTED) {
				//Pokemon is chosen. Switch to choose pokemon menu
				if (x >= 484 && x<= 619 && y>= 634 && y<= 668) {
					setupPokeSwitchMenu(battle.getDefendTrainer());
				}
			}
			
		});
		
		//Mouse move handler
		window.setOnMouseMoved((event) -> {
			
			double x = event.getSceneX();
			double y = event.getSceneY();
			
			if (battle.getCurrState() == BattleState.IDLE || battle.getCurrState() == BattleState.FAINTED) {	//User chooses action.
			
				//Fight is highlighted
				if (x >= 484 && x<= 581 && y>= 585 && y<= 620) {
					((BattleView) battleView).drawMainSelectMenu(0);
				}
				//Bag is highlighted
				else if (x >= 653 && x<= 713 && y>= 585 && y<= 620) {
					((BattleView) battleView).drawMainSelectMenu(1);
				}
				//Pokemon is highlighted
				else if (x >= 484 && x<= 619 && y>= 634 && y<= 668) {
					((BattleView) battleView).drawMainSelectMenu(2);
				}
				//Run is highlighted
				else if (x >= 653 && x<= 713 && y>= 634 && y<= 668) {
					((BattleView) battleView).drawMainSelectMenu(3);
				}
			}
			
			else if (battle.getCurrState() == BattleState.CHOOSE_ATTACK) {
				
				//Attack1 is highlighted
				if (x >= 43 && x<= 243 && y>= 585 && y<= 615) {
					((BattleView) battleView).drawAttackMenu(0);
				}
				//Attack2 is highlighted
				else if (x >= 302 && x<= 477 && y>= 585 && y<= 615) {
					((BattleView) battleView).drawAttackMenu(1);
				}
				//Attack3 is highlighted
				else if (x >= 43 && x<= 243 && y>= 631 && y<= 660) {
					((BattleView) battleView).drawAttackMenu(2);
				}
				//Attack4 is highlighted
				else if (x >= 302 && x<= 477 && y>= 631 && y<= 660) {
					((BattleView) battleView).drawAttackMenu(3);
				}
			}
		});
	}
	
	private class SelectButtonListener implements EventHandler<ActionEvent> {

		private Pokemon chosenPoke;
		private List<Pokemon> battleList;
		
		public SelectButtonListener(Trainer trainer) {
			battleList = trainer.getBattlePokemonList();
		}

		@Override
		public void handle(ActionEvent event) {

				chosenPoke = pokeTable.getSelectionModel().getSelectedItem();
				
				if (chosenPoke == null) return;
	
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
					removePokeSelectionMenu();
					setViewToBattle();
					runBattle();
				}
				
				else if (battleList.size() == 3) {
					removePokeSelectionMenu();
					setupPokeSelectionMenu(battle.getOppTrainer()); //Setup menu for next trainer
				}
		}
	}
	
	private class SwitchButtonListener implements EventHandler<ActionEvent> {

		private Trainer currTrainer;
		
		public SwitchButtonListener(Trainer trainer) {
			this.currTrainer = trainer; 
		}
		
		@Override
		public void handle(ActionEvent event) {
			
			Pokemon chosenPoke = pokeTable.getSelectionModel().getSelectedItem();
			
			if (chosenPoke == null) return;
			
				if (chosenPoke.getCurrHP() <= 0) {
					String message = chosenPoke.getName() + " is completely drained. Choose another pokemon.";
					newAlertMessage("Error", message);
				}
				
				else if (chosenPoke == currTrainer.getActiveBattlePokemon()) {
					String message = chosenPoke.getName() + " is currently active. Choose another pokemon.";
					newAlertMessage("Error", message);
				}
				
				else {
					((BattleView) battleView).stopAllActiveTimelines(); //Stop any pokemon animations running before switch
					currTrainer.setActiveBattlePokemon(chosenPoke);
					removePokeSwitchMenu();
					setViewToBattle();
					restartBattle(chosenPoke);
				}
			
		}
	}
	
}
