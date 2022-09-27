package application;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;
import javafx.application.Platform;

public class TicTacToe extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	static ArrayList<Button> buttons;
	private static Text text;

	private Game game = new Game("X");
	private Minimax ai = new Minimax(game);
	public static boolean multiplayer = false;
	public static boolean flag = false;
	Optional<ButtonType> result;
	Label text1 = new Label();
	Label text2 = new Label();
	static int x;
	static int o;

	@Override
	public void start(Stage stage) {
		// menu

		Alert menu = new Alert(Alert.AlertType.CONFIRMATION);
		menu.setGraphic(null);
		menu.setTitle("Tic Tac Toe");
		menu.setHeaderText("What would you like to play?");
		menu.setContentText("Select one of the following modes");
		ButtonType btnSingle = new ButtonType("Minimax AI");
		ButtonType btnRandom = new ButtonType("Random AI");
		ButtonType btnMulti = new ButtonType("Player vs Player");

		ButtonType btnQuit = new ButtonType("Quit", ButtonData.CANCEL_CLOSE);
		menu.getButtonTypes().setAll(btnSingle, btnRandom, btnMulti, btnQuit);

		result = menu.showAndWait();
		if (!result.isPresent()) {
			return;
		}
		multiplayer = result.get() == btnMulti;
		if (multiplayer) {
			flag = false;
		}
		if (result.get() == btnQuit) {
			Platform.exit();
		}

		flag = result.get() == btnRandom;
		if (flag) {
			multiplayer = false;
		}

		// game
		buttons = new ArrayList<Button>(Arrays.asList(new Button(), new Button(), new Button(), new Button(),
				new Button(), new Button(), new Button(), new Button(), new Button()));
		buttons.forEach(b -> {
			b.setMinWidth(120);
			b.setMinHeight(120);
			b.setStyle("-fx-background-color: white;" + "-fx-text-fill: #164A6D ;" + "-fx-font-weight: bold;"
					+ "-fx-font: 53px 'Sans-serif';-fx-background-radius:19;-fx-border-width:2;-fx-border-radius:17;-fx-border-color:#EB8F05");
			b.setOnAction(this::btnClick);
		});
		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(buttons);
		flow.setVgap(10);
		flow.setHgap(10);
		flow.setAlignment(javafx.geometry.Pos.CENTER);

		text = new Text("Player X's turn");
		//text.setFill(Color.web("#E38316"));
		text.setStyle("-fx-fill: ORANGERED;-fx-font-weight: bold;" + "-fx-font: 36px 'Sans-serif';");
		//text.setStyle("-fx-fill: lightseagreen;-fx-stroke: firebrick;"+"-fx-stroke-width: 2px;"+"-fx-font: 50px 'Sans-serif';");
	      text.setStrokeWidth(.3);
	      //Setting the stroke color
	     text.setStroke(Color.DODGERBLUE);
		text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		text1.setText("Player(O): "+o);
		text2.setText("Player(X): "+x);
		text1.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		text1.setStyle("-fx-TEXT-fill: BLACK;-fx-font-weight: bold;" + "-fx-font: 25 'Sans-serif' ;");
		text2.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		text2.setStyle("-fx-TEXT-fill: BLACK;-fx-font-weight: bold;" + "-fx-font: 25 'Sans-serif';");

		Button restartButton = new Button("Restart");
		Button reselect = new Button("select mode");
		reselect.setOnAction(e -> {
			buttons.forEach(b -> {

				b.setStyle("-fx-background-color: white;" + "-fx-text-fill: #164A6D ;" + "-fx-font-weight: bold;"
						+ "-fx-font: 53px 'Sans-serif';-fx-background-radius:19;-fx-border-width:2;-fx-border-radius:17;-fx-border-color:#EB8F05");

			});
			text.setText("Player X's turn");

			x=0;
			o=0;
			text1.setText("Player(O): "+o);
			text2.setText("Player(X): "+x);
			game = new Game("X");
			ai = new Minimax(game);
			showText("Player X's turn");
			result = menu.showAndWait();
			if (!result.isPresent()) {
				return;
			}
			multiplayer = result.get() == btnMulti;
			if (result.get() == btnQuit) {
				Platform.exit();
			}

			flag = result.get() == btnRandom;
			buttons.forEach(b -> {
				b.setText("");

			});

		});
		restartButton.setOnAction(e -> {
			ai.airandom();
			game.switchPlayer();

			buttons.forEach(b -> {

				b.setStyle("-fx-background-color: white;" + "-fx-text-fill: #164A6D ;" + "-fx-font-weight: bold;"
						+ "-fx-font: 53px 'Sans-serif';-fx-background-radius:19;-fx-border-width:2;-fx-border-radius:17;-fx-border-color:#EB8F05");

			});
			text.setText("Player X's turn");

			game = new Game("X");

			ai = new Minimax(game);
			flag = result.get() == btnRandom;
			buttons.forEach(b -> {
				b.setText("");



			});
		});
		Button resetButton = new Button("Reset");

		reselect.setStyle(
				"-fx-text-fill:  white;-fx-background-color:  #008CBA;-fx-background-radius:20;-fx-font-width: bold;"
						+ "-fx-font: 18px 'Sans-serif';");
		restartButton.setStyle(
				"-fx-text-fill:  white;-fx-background-color:  #008CBA;-fx-background-radius:20;-fx-font-width: bold;"
						+ "-fx-font: 18px 'Sans-serif';");
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(e -> Platform.exit());
		quitButton.setStyle(
				"-fx-text-fill:  white;-fx-background-color:  #EB1E07;-fx-background-radius:26;-fx-font-width: bold;"
						+ "-fx-font:16px 'Sans-serif';");
		HBox hbox = new HBox();
		hbox.getChildren().addAll(reselect, restartButton,resetButton, quitButton);
		hbox.setAlignment(javafx.geometry.Pos.CENTER);
		hbox.setSpacing(35);

		
		
		
		HBox hbox1 = new HBox();
		resetButton.setOnAction(e -> {		
		x=0;
		o=0;
		text1.setText("Player(O): "+o);
		text2.setText("Player(X): "+x);
			/*
			 * buttons.forEach(b -> {
			 * 
			 * b.setStyle("-fx-background-color: white;" + "-fx-text-fill: #164A6D ;" +
			 * "-fx-font-weight: bold;" +
			 * "-fx-font: 53px 'Sans-serif';-fx-background-radius:19;-fx-border-width:2;-fx-border-radius:17;-fx-border-color:#EB8F05"
			 * );
			 * 
			 * }); text.setText("Player X's turn");
			 * 
			 * game = new Game("X");
			 * 
			 * ai = new Minimax(game); flag = result.get() == btnRandom; buttons.forEach(b
			 * -> { b.setText("");
			 * 
			 * 
			 * 
			 * });
			 */
		});
		resetButton.setStyle(
				"-fx-text-fill:  white;-fx-background-color:  #C05747;-fx-background-radius:50;-fx-font-width: bold;"
						+ "-fx-font:16px 'Sans-serif';");
		hbox1.getChildren().addAll(text2, text1);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.setSpacing(25);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(hbox1, flow, text, hbox);
		vbox.setAlignment(javafx.geometry.Pos.CENTER);
		vbox.setSpacing(25);
		vbox.setStyle("-fx-background-color:  lightblue;");

		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		stage.setTitle("Tic Tac Toe");
		stage.setWidth(470);
		stage.setHeight(640);
		stage.setResizable(false);
		stage.show();
	}

	private void boint(String st) {
		if (st == "X") {
			x++;

			text2.setText("Player(X): " + x);
		} else if (st == "O") {
			o++;
			text1.setText("Player(O): " + o);
		} else {

		}

	}

	private void btnClick(ActionEvent event) {
		int f = 0;
		if (game.getIsOn()) {
			int pos = buttons.indexOf(event.getSource());
			try {
				game.makeMove(pos);
				buttons.get(pos).setText(game.getBoard().getCell(pos).getPlayer()); // move all gui in this class
				if (flag) {
					System.out.println("hi");
					if (!(game.checkForWinner() == null)) {
						boint(game.checkForWinner());
						f = 1;
					}
					if (game.getIsOn()) {
						game.switchPlayer();
						ai.airandom();
						if (f == 0) {
							boint(game.checkForWinner());

							if (game.getIsOn())
								game.switchPlayer();

						}

					}
				} else if (multiplayer) {
					boint(game.checkForWinner());
					if (game.getIsOn())
						game.switchPlayer();
				} else {
					if (!(game.checkForWinner() == null)) {
						boint(game.checkForWinner());
						f = 1;
					}

					if (game.getIsOn()) {
						game.switchPlayer();
						ai.aiMakeMove();
						if (f == 0) {
							boint(game.checkForWinner());

							if (game.getIsOn())
								game.switchPlayer();


						}
					}
				}

			} catch (CellAlreadyTakenException e) {
				showText("That cell was already taken!");
				return;
			} catch (CloneNotSupportedException e) {
				showText("Ai isn't working");
				return;
			}


		}
	}

	public static void showText(String s) {
		text.setText(s);
	}
}
