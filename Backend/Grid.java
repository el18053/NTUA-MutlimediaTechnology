package Backend;

import java.lang.Math;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class

public class Grid {
    private int size;
    private Block grid[][];
    private int SuperMine;
    private static final int MINE = 9;
    private String MEDIALAB_PATH;

    Grid(int size, int mines, int SuperMine, String path) {
        this.MEDIALAB_PATH = path;
        this.size = size;
        this.SuperMine = SuperMine;
        this.grid = new Block[size][size];
        for (int i=0; i< this.grid.length; i++)
            for (int j=0; j< this.grid.length; j++)
                this.grid[i][j] = new Block();

        int No_of_mines = mines;        
        try {
            File file = new File(MEDIALAB_PATH + "mines.txt");
            file.createNewFile();
            FileWriter myWriter = new FileWriter(MEDIALAB_PATH + "mines.txt");
            while(No_of_mines > 0) {
                int newMineX = Rand(size);
                int newMineY = Rand(size);
                if(this.grid[newMineX][newMineY].Mine == true)
                    continue;
                if(this.SuperMine == 1) {
                    int prob = Rand(mines);
                    if (prob > No_of_mines - 1 / mines) {
                        this.grid[newMineX][newMineY].SuperMine = true;
                        this.SuperMine = 0;
                    }
                    if(No_of_mines == 1) {
                        this.grid[newMineX][newMineY].SuperMine = true;
                        this.SuperMine = 0;
                    }
                }
                this.grid[newMineX][newMineY].Mine = true;
                this.grid[newMineX][newMineY].Num = MINE;
                No_of_mines--;
                int isSuperMine = this.grid[newMineX][newMineY].SuperMine ? 1 : 0;;
                myWriter.write(newMineX + "," + newMineY + "," + isSuperMine);
                if(No_of_mines > 0)
                    myWriter.write("\n");
            }
            //this.SuperMine = SuperMine;
            myWriter.close();
        }  
        catch (IOException e) {
            System.out.println("An error occurred while trying to open mines.txt file.");
            e.printStackTrace();
        }

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(this.grid[i][j].Mine != true)
                    Fix_Border(i, j);
            }
        }
    }


    public Block getBlock(int X, int Y) {
        return grid[X][Y];
    }

    public boolean getOpen(int X, int Y) {
        return grid[X][Y].isOpen;
    }

    public boolean getFlag(int X, int Y) {
        return grid[X][Y].Flag;
    }

    public int getSize() {
        return size;
    }

    public void setFlag(int X, int Y) {
        grid[X][Y].Flag = !grid[X][Y].Flag;
    }

    public void setOpen(int X, int Y) {
        grid[X][Y].isOpen = true;
    }

    public void showMines() {
        for(int i=0; i<getSize(); i++)
            for(int j=0; j<getSize(); j++)
                if(grid[i][j].Mine)
                    grid[i][j].isOpen = true;
    }

    public int loc(int x,int y) {
        return getSize()*x + y;
    }

    public boolean checkBox(int x,int y) {
        return grid[x][y].Mine;
    }

    public boolean checkBox_SuperMine(int x,int y) {
        return grid[x][y].SuperMine;
    }

    public int Rand(int param) {
        double x;
        x = Math.random()*param;
        return (int)x;
    }

    public void Fix_Border(int x,int y) {
        int num = grid[x][y].Num;
        if(x > 0) {
            num = Fix_Line(x-1, y, num);
        }
        num = Fix_Line(x, y, num);
        if(x+1<getSize())
            num = Fix_Line(x+1, y, num);

        grid[x][y].Num = num;
    }

    public int Fix_Line(int x,int y, int num) {
        if(y>0) {
            if(checkBox(x, y-1) == true)
                num++;
        }
        if(checkBox(x, y) == true)
            num++;
        if(y+1<getSize()){
            if(checkBox(x, y+1) == true)
                num++;
        }
        return num;
    }

    public int FloodFill(int X, int Y) {
        if (grid[X][Y].isOpen)
            return 0;
        if(grid[X][Y].Mine)
            return 0;
        if(grid[X][Y].Flag)
            return 0;
        if(grid[X][Y].SuperMine)
            return 0;
        if (grid[X][Y].Num != 0) {
            setOpen(X, Y);
            return 1;
        }
        setOpen(X, Y);
        int opened = 1;
        //Top row
        if(X > 0) {
            if(Y > 0)
            opened += FloodFill(X-1, Y-1);
            opened += FloodFill(X-1, Y);
            if(Y+1 < getSize())
                opened += FloodFill(X-1, Y+1);
        }

        // Left and Rigth
        if(Y > 0)
            opened += FloodFill(X, Y-1);
        if(Y+1 < getSize())
            opened += FloodFill(X, Y+1);
        
        //Bottom row
        if(X+1 < getSize()) {
            if(Y > 0)
                opened += FloodFill(X+1, Y-1);
            opened += FloodFill(X+1, Y);
            if(Y+1 < getSize())
                opened += FloodFill(X+1, Y+1);
        }
        return opened;
    }

    public Custom_Pair HitSuperMine(int X, int Y) {
        int HitMines = 0, ReturnFlags = 0;
        Custom_Pair pair;
        grid[X][Y].Hit = true;
        grid[X][Y].isOpen = true;
        pair = HitRow(X, Y, 1);
        HitMines += pair.getX();
        ReturnFlags += pair.getY();
        pair = HitRow(X, Y, -1);
        HitMines += pair.getX();
        ReturnFlags += pair.getY();
        pair = HitCol(X, Y, 1);
        HitMines += pair.getX();
        ReturnFlags += pair.getY();
        pair = HitCol(X, Y, -1);
        HitMines += pair.getX();
        ReturnFlags += pair.getY();
        return new Custom_Pair(HitMines, ReturnFlags);
    }

    private Custom_Pair HitRow(int X, int Y, int Op) {
        int HitMines = 0, ReturnFlags = 0;
        Custom_Pair pair;
        X += Op;
        if(X>=0 && X<getSize()) {
            grid[X][Y].isOpen = true;
            if(grid[X][Y].Mine) {
                HitMines++;
                grid[X][Y].Hit = true;
            }
            if(grid[X][Y].Flag)
                ReturnFlags++;
            pair = HitRow(X, Y, Op);
            HitMines += pair.getX();
            ReturnFlags += pair.getY();
        }
        return new Custom_Pair(HitMines, ReturnFlags);
    }

    private Custom_Pair HitCol(int X, int Y, int Op) {
        int HitMines = 0, ReturnFlags = 0;
        Custom_Pair pair;
        Y += Op;
        if(Y>=0 && Y<getSize()){
            grid[X][Y].isOpen = true;
            if(grid[X][Y].Mine) {
                HitMines++;
                grid[X][Y].Hit = true;
            }
            if(grid[X][Y].Flag)
                ReturnFlags++;
            pair = HitCol(X, Y, Op);
            HitMines += pair.getX();
            ReturnFlags += pair.getY();
        }
        return new Custom_Pair(HitMines, ReturnFlags);
    }

    public String toString() {
        String out = "";
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                out = out + this.grid[i][j].Num + "\t";
            }
            out = out + "\n";
        }
        return out;
    }
}