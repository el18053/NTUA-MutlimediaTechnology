/**
* Blcok class implements a single
* block of the grid.
*
* @author  Symeon Porgiotis
*
* @param isOpen whether or not we have clicked this block.
* @param Flag whether or not this block is a Flag.
* @param Mine whether or not this block is a Mine.
* @param SuperMine whether or not this block is a SuperMine.
* @param Hit wheter or not this block was opened by a SuperMine
* @param Num how many Mines exist around this block.
*/

package Backend;

public class Block {
    protected boolean isOpen;
    protected boolean Flag;
    protected boolean Mine;
    protected boolean SuperMine;
    protected boolean Hit;
    protected int Num;

    protected Block() {
        this.isOpen = false;
        this.Flag = false;
        this.Mine = false;
        this.SuperMine = false;
        this.Hit = false;
        this.Num = 0;
    }
    
    protected Block(boolean isOpen, boolean Flag, boolean Mine, boolean SuperMine, boolean Hit, int Num) {
        this.isOpen = isOpen;
        this.Flag = Flag;
        this.Mine = Mine;
        this.SuperMine = SuperMine;
        this.Hit = Hit;
        this.Num = Num;
    }

    public boolean getisOpen() {
        return isOpen;
    }

    public boolean getFlag() {
        return Flag;
    }

    public boolean getMine() {
        return Mine;
    }

    public boolean getSuperMine() {
        return SuperMine;
    }

    public boolean getHit() {
        return Hit;
    }

    public int getNum() {
        return Num;
    }
}
