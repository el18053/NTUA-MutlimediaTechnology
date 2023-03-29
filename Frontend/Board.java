package Frontend;

import Backend.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.io.File;


public class Board
{
    VBox board;
    final String Image_PATH;
    final double BUTTON_HEIGHT = 54;

    public Board(Game game, String path)
    {
        this.Image_PATH = path + "Images/";
        board = new VBox();
        try {
            for(int i = 0; i < game.getSize(); ++i)
            {
                HBox row = new HBox();
                for(int j = 0; j < game.getSize(); ++j)
                {
                    //Creating a Cell with 54 pixel Height and Length
                    Cell cell = new Cell(BUTTON_HEIGHT, BUTTON_HEIGHT);
                    //Set proper Image
                    cell.update(game.getBlock(i,j));

                    row.getChildren().add(cell);
                }
                board.getChildren().add(row);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public VBox getBoard()
    {
        return board;
    }

    private class Cell extends Rectangle
    {
        public Cell(double SizeX, double SizeY)
        {
            super(SizeX, SizeY);
        }

        public void update(Block block) {
            if (block.getisOpen()){
                if(block.getHit()) {
                    setFill(Color.YELLOWGREEN);
                }

                else if(block.getMine()) {
                    //Create image
                    Image imag = new Image(new File(Image_PATH + "M.png").toURI().toString());
                    ImagePattern view = new ImagePattern(imag);

                    //Attach image to the button
                    setFill(view);
                }

                else {
                    Image imag;
                    switch(block.getNum()) {
                        case 0:
                            imag = new Image(new File(Image_PATH + "0.png").toURI().toString());
                            break;
                        case 1:
                            imag = new Image(new File(Image_PATH + "1.png").toURI().toString());
                            break;
                        case 2:
                            imag = new Image(new File(Image_PATH + "2.png").toURI().toString());
                            break;
                        case 3:
                            imag = new Image(new File(Image_PATH + "3.png").toURI().toString());
                            break;
                        case 4:
                            imag = new Image(new File(Image_PATH + "4.png").toURI().toString());
                            break;
                        case 5:
                            imag = new Image(new File(Image_PATH + "5.png").toURI().toString());
                            break;
                        case 6:
                            imag = new Image(new File(Image_PATH + "6.png").toURI().toString());
                            break;
                        case 7:
                            imag = new Image(new File(Image_PATH + "7.png").toURI().toString());
                            break;
                        case 8:
                            imag = new Image(new File(Image_PATH + "8.png").toURI().toString());
                            break;
                        default :
                            imag = new Image(new File(Image_PATH + "X.png").toURI().toString());
                            break;
                    }
                    //Create image
                    ImagePattern view = new ImagePattern(imag);

                    //Attach image to the button
                    setFill(view);
                }
            }
            else {
                if(block.getFlag()) {
                    //Create image
                    Image imag = new Image(new File(Image_PATH + "F.png").toURI().toString());
                    ImagePattern view = new ImagePattern(imag);

                    //Attach image to the button
                    setFill(view);
                }

                else {

                    Image imag = new Image(new File(Image_PATH + "X.png").toURI().toString());
                    ImagePattern view = new ImagePattern(imag);
                    setFill(view);
                }
            }
        }

    }

}