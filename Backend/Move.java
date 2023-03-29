/**
* Move class implements which block of the grid
* player has opened.
*
* @author  Symeon Porgiotis
*
* @param targetX is the X-axis position to open.
* @param targetY is the Y-axis position to open.
* @param click if click is 0 then 
that means that the user made a left click
and if its 1 then that means that the user
made a right click.
*/

package Backend;

public class Move {
    private int targetX;
    private int targetY;
    private int click;

    public Move(int X, int Y, int click) {
        this.targetX = X;
        this.targetY = Y;
        this.click = click;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getClick() {
        return click;
    }

    public String toString() {
        return "Open Block in position (" + getTargetX() + "," + getTargetY() +")\n";
    }
}