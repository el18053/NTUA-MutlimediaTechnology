package Backend;

import java.io.File;
import java.util.Scanner;
import Backend.Exceptions.*;

public class Game {
    private int Level;
    private int Size;
    private int Mines;
    private int Time;
    private int SuperMine;
    private State game;
    private int Over;
    private int Attempts;
    private String MEDIALAB_PATH;

    public Game(String path, String SCENARIO_ID) throws Exception {
        this.MEDIALAB_PATH = path;
        File file = new File(MEDIALAB_PATH + "SCENARIO-" + SCENARIO_ID + ".txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
            try {
                this.Level = Integer.parseInt(sc.nextLine());
                this.Mines = Integer.parseInt(sc.nextLine());
                this.Time = Integer.parseInt(sc.nextLine());
                this.SuperMine = Integer.parseInt(sc.nextLine());
            }
            catch (Exception e) {
                throw new InvalidDescriptionException("Missing Parameters");
            }
            finally {
                sc.close();
            }
        }
        catch (Exception e) {
            throw e;
        }
        check_params();
    }

    /**
     * @throws Exception
     * 
     * 
     * 
     */
    protected void check_params() throws Exception {
        if(Level < 1 || Level > 2)
            throw new InvalidValueException("Level is eiter 1 or 2");
        switch (Level) {
            case 1:
                this.Size = 9;
                if(Mines < 9 || Mines > 11)
                    throw new InvalidValueException("Number of Mines should be between 9-11 not " + Mines);
                if(Time < 120 || Time > 180)
                    throw new InvalidValueException("Time should be between 120-180(s) not " + Time + "(s)");
                if(SuperMine != 0)
                    throw new InvalidValueException("Level 1 does't have any SuperMine(s)");
                break;
            case 2:
                this.Size = 16;
                if(Mines < 35 || Mines > 45)
                    throw new InvalidValueException("Number of Mines should be between 35-45 not " + Mines);
                if(Time < 240 || Time > 360)
                    throw new InvalidValueException("Time should be between 240-360(s) not " + Time + "(s)");
                if(SuperMine != 1)
                    throw new InvalidValueException("Level 2 must have one SuperMine");
                break;
            default:
                break;
        }
    }

    public void newGame() {
        Attempts = 0;
        game = new State(Mines, Size, SuperMine, MEDIALAB_PATH);
        Over = 0;
    }

    public int getAttempts() {
        return Attempts;
    }

    public int getTime() {
        return Time;
    }

    public int getMines() {
        return Mines;
    }

    public int getSize() {
        return Size;
    }

    public State getState() {
        return game;
    }

    public Grid getGrid() {
        return game.getGrid();
    }

    public Block getBlock(int X, int Y){
        return game.getGrid().getBlock(X,Y);
    }

    public void Attempt() {
        Attempts++;
    }

    public void lose() {
        game.getGrid().showMines();
    }

    public Custom_Pair giveUp() {
        Custom_Pair pair = null;
        for(int i=0; i<Size; i++)
            for(int j=0; j<Size; j++)
                if(game.getBlock(i,j).Mine)
                    pair = new Custom_Pair(i,j);
                    //play(new Move(i, j, 0));

        return pair;
    }

    public void play(Move move) throws Exception {
        if(Over != 0)
            return ;
        else {
            //Player makes a Move;
            //System.out.println(move);
            game.nextState(move, Attempts);
            //System.out.println(game);
            //System.out.println(getGrid());
            if (game.getLoser())
                Over = -1;
            if (game.getWinner())
                Over = 1;
        }

        if(Over == -1)
            throw new LoserException();

        else if (Over == 1)
            throw new WinnerException();   
    }
    

    public String toString() {
        return "Difficulty level is: " + Level + "\nNumber of Mines is: " + Mines + "\nTime is: " + Time + "(s)\nSuper Mine excistance is: " + SuperMine + "\nTable Size is: (" + Size +"," + Size + ")";
    }
}