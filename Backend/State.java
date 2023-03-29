package Backend;

public class State {
    private int No_blocks;
    private int No_Mines;
    private int No_flags;
    private boolean loser;
    private Grid grid;
    private String MEDIALAB_PATH;

    State(int mines, int size, int SuperMine, String path) {
        this.MEDIALAB_PATH = path;
        this.No_Mines = mines;
        this.No_flags = mines;
        this.loser = false;
        this.No_blocks = size*size;
        this.No_blocks -= mines;
        this.grid = new Grid(size, No_Mines, SuperMine, MEDIALAB_PATH);
    }

    public int getFlags() {
        return No_flags;
    }

    protected Grid getGrid() {
        return grid;
    }

    protected boolean getLoser() {
        return loser;
    }

    protected Block getBlock(int X, int Y) {
        return grid.getBlock(X, Y);
    }

    protected boolean getWinner() {
        if(No_Mines == 0 || No_blocks == 0)
            return true;
        return false;
    }

    protected boolean check(int x, int y) {
        return grid.checkBox(x, y);
    }

    protected void nextState(Move move, int Attempts) {
        int X = move.getTargetX(), Y = move.getTargetY(), click = move.getClick();
        boolean isMine = check(move.getTargetX(), move.getTargetY());

        if (grid.getOpen(X, Y) )
                return ;

        if (click == 0) {
            if( grid.getFlag(X, Y) )
                nextState(new Move(X, Y, 1), Attempts);
            if (isMine) {
                grid.setOpen(X, Y);
                loser = true;
            }
            else
                No_blocks -= grid.FloodFill(X, Y);
        }
        else {
            if(No_flags == 0 && grid.getFlag(X, Y) == false)
                return ;
            grid.setFlag(X, Y);
            if (isMine)
            {
                if(grid.getFlag(X, Y)) {
                    No_Mines--;
                    if(grid.checkBox_SuperMine(X, Y) && Attempts <=4) {
                        Custom_Pair pair = grid.HitSuperMine(X, Y);
                        No_Mines -= pair.getX();
                        No_flags -= pair.getX() - pair.getY();
                        No_blocks -= 2*grid.getSize();
                        No_blocks += pair.getX();
                    }
                }
                else
                    No_Mines++;
            }
            if(grid.getFlag(X, Y))
                No_flags--;
            else
                No_flags++;
        }
    }

    public String toString() {
        return "Number of Closed Blocks remaining are : " + No_blocks + "\n" + "Number of Mines remaining are : " + No_Mines + "\n" + "Number of Flags remaining are : " + No_flags + "\n";
    }
}