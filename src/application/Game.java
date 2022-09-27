package application;

import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class Game implements Cloneable {

    public ArrayList<Integer> playerX = new ArrayList<>();
    public ArrayList<Integer> playerO = new ArrayList<>();
    private boolean isOn;
    private String currentPlayer;
    private Board board;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getIsOn(){
        return isOn;
    }

    public Board getBoard() {
        return board;
    }

    public Game(String p) {
        board = new Board();
        currentPlayer = p.toUpperCase();
        isOn = true;
    }

    //cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Game copy = (Game)super.clone();
        copy.board = (Board)copy.board.clone();
        ArrayList<Integer> xCopy = new ArrayList<>(playerX.size());
        ArrayList<Integer> oCopy = new ArrayList<>(playerO.size());
        for (Integer i : playerX) {
            xCopy.add(Integer.valueOf(i));
        }
        for (Integer i : playerO) {
            oCopy.add(Integer.valueOf(i));
        }
        copy.playerX = xCopy;
        copy.playerO = oCopy;
        //copy.currentPlayer = currentPlayer;
        return copy;
    }

    public void makeMove(int pos) {
        //might throw CellAlreadyTakenException
        board.getCell(pos).setPlayer(currentPlayer);
        if (currentPlayer.equals("X")) {
            playerX.add(pos);
        } else {
            playerO.add(pos);
        }
        board.takeCell();
    }

    public String checkForWinner() {
        if (board.isFull()) {
            isOn = false;
            TicTacToe.showText("Draw");
        }
        HashSet<ArrayList<Integer>> winCombs = new HashSet<>(Arrays
            .asList(new ArrayList<Integer>(Arrays.asList(0, 1, 2)),
                    new ArrayList<Integer>(Arrays.asList(3, 4, 5)),
                    new ArrayList<Integer>(Arrays.asList(6, 7, 8)),
                    new ArrayList<Integer>(Arrays.asList(0, 3, 6)),
                    new ArrayList<Integer>(Arrays.asList(1, 4, 7)),
                    new ArrayList<Integer>(Arrays.asList(2, 5, 8)),
                    new ArrayList<Integer>(Arrays.asList(0, 4, 8)),
                    new ArrayList<Integer>(Arrays.asList(2, 4, 6))));
        for (ArrayList<Integer> comb : winCombs) {
            if (playerO.containsAll(comb) || playerX.containsAll(comb)) {
                isOn = false;
                for (Integer i : comb) {
        	        TicTacToe.buttons.get(i).setStyle("-fx-background-color: #00FA9A;" + "-fx-text-fill: #164A6D ;" + "-fx-font-weight: bold;"
        					+ "-fx-font: 55px 'Sans-serif';-fx-background-radius:19;-fx-border-width:2;-fx-border-radius:17;-fx-border-color:#EB8F05");

                	
                }
                
                TicTacToe.showText("Player " + currentPlayer + " won");

                return currentPlayer;
            }
        }
		return null;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        TicTacToe.showText("Player " + currentPlayer + "'s turn");
    }
}
