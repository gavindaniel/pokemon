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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Trainer;
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
	
	//Observable Lists
	private OwnedPokeTable ownedPokeTable;
	private ListView<String> selectedPokeListView;
	private ObservableList<String> selectedPokemonList;

	//Declare observers
	private Observer battleView;

	//Window Size
	public static final double SCENE_WIDTH = 800;
	public static final double SCENE_HEIGHT = 550;


	@Override
	public void start(Stage stage) throws Exception {

		//Initialize main border pane and scene
		stage.setTitle("Pokemon Battle");
		window = new BorderPane();
		Scene scene = new Scene(window, SCENE_WIDTH, SCENE_HEIGHT);

		//Initialize logic model
		initializeTrainers();
		
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
	
	
	private void runBattle() {
		
		battle.initializeActiveBattlePokemon();
		
		
		
	}
				
				
}
