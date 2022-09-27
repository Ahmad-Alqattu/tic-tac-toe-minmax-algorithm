package application;

import java.util.Scanner;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Random;

public class Minimax {
	int num[];
    private Game game;
    private int choice;

    public Minimax(Game g) {
        game = g;
    }

    public void aiMakeMove() throws CloneNotSupportedException {
        calculateMove((Game)game.clone());
        game.makeMove(choice);
        TicTacToe.buttons.get(choice).setText("O");
  
    
    }
    
    
    public void airandom()  {
    	ArrayList<Integer> givenList = new ArrayList<>();
    	for(int i=8; i>=0;i--) {
       		if(!(TicTacToe.buttons.get(i).getText()=="X"||TicTacToe.buttons.get(i).getText()=="O")) {
    		givenList.add(i);
       		}
    	}
        Random rand = new Random();
        int randomIndex = 0;
            randomIndex = rand.nextInt(givenList.size());
            randomIndex= givenList.get(randomIndex);
	        game.makeMove(randomIndex);
	        TicTacToe.buttons.get(randomIndex).setText("O");
	
	        
	     
		}

    

    private int calculateMove(Game g) throws CloneNotSupportedException {
        //get score if game is over
        if (isOver(g)) {
            return getScore(g);
        }

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<Integer> moves = new ArrayList<>();
        //itirate through all available moves recursing on each
        for (int i = 0; i < 9; i++) {
            Cell c =  g.getBoard().getCell(i);
            if (!c.getIsTaken()) {
                Game possibleGame = (Game)g.clone();
                possibleGame.makeMove(i);
                possibleGame.switchPlayer();
                scores.add(calculateMove(possibleGame));
                moves.add(i);
            }
        }
        //decide which move will be made
        if (g.getCurrentPlayer().equals("O")) {
            //max, computer's turn
         int maxIndeces=0;
            for (int i = 0; i < scores.size(); i++) {
                int score = scores.get(i);
                int currentMax = scores.get(maxIndeces);
                if (score > currentMax) {
                	maxIndeces=i;

            }
            }

            choice = moves.get(maxIndeces);
            return scores.get(maxIndeces);
        } else {
            //min, human's turn
            int minIndeces=0;

            for (int i = 0; i < scores.size(); i++) {
                int score = scores.get(i);
                int currentMin = scores.get(minIndeces);
                if (score < currentMin) {
                	minIndeces=i;
    
            }
            }
            choice = moves.get(minIndeces);
            return scores.get(minIndeces);
        }
    }

    //checks if the game is over
    private boolean isOver(Game g) {
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
            if (g.playerO.containsAll(comb) || g.playerX.containsAll(comb)) {
                return true;
            }
        }
        return g.getBoard().isFull();
    }

    //gets the score for minimax for an ended game
    private int getScore(Game g) {
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
            if (g.playerX.containsAll(comb)) {
                return -1;
            } else if (g.playerO.containsAll(comb)) {
                return 1;
            }
        }
        return 0;
    }
}