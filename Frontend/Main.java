package Frontend;

import Backend.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main extends Application {
    final String MEDIALAB_PATH = "./medialab/";
    Game game;
    GridPane grid;
    boolean NewGameStarted = true;
    Timer clock;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create Grid to add Board
        grid = new GridPane();

        //Creating a menu
        Menu Details = new Menu("Details");

        //Creating menu Items
        MenuItem Rounds = new MenuItem("Rounds");
        MenuItem Solution = new MenuItem("Solution");

        //Adding all the menu items to the menu
        Details.getItems().addAll(Rounds, new SeparatorMenuItem(), Solution);

        //Creating a menu
        Menu Application = new Menu("Application");
        
        //Creating menu Items
        MenuItem Create = new MenuItem("Create");
        MenuItem Load = new MenuItem("Load");
        MenuItem Start = new MenuItem("Start");
        MenuItem Exit = new MenuItem("Exit");

        //Adding all the menu items to the menu
        Application.getItems().addAll(Create, new SeparatorMenuItem(), Load, new SeparatorMenuItem(), Start, new SeparatorMenuItem(), Exit);

        //Creating a menu bar and adding menu to it.
        MenuBar menuBar = new MenuBar(Application, Details);

        primaryStage.setTitle("MediaLab Minesweeper");
        //VBox vBox = new VBox(menuBar, grid);
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(grid);
        Scene scene = new Scene(root, 512, 32);
        
        primaryStage.setScene(scene);
        primaryStage.show();

        //Add action listeners
        Solution.setOnAction(e -> {
            Custom_Pair pair = game.giveUp();
            Double x = pair.getX()*54 + 128;
            Double y = pair.getY()*54 + 1;
            grid.fireEvent(new MouseEvent(MouseEvent.MOUSE_PRESSED, y, x, 0, 0, MouseButton.PRIMARY, 1, true, false, false, false, false, false, false, false, false, false, null));
        });

        Rounds.setOnAction(e -> {
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setWidth(512);
            popupwindow.setHeight(256);
            popupwindow.setTitle("Rounds");
            
            String text = "";
            try {
                File file = new File(MEDIALAB_PATH + "Rounds.txt"); 
                //Read previous Rounds
                Scanner sc = new Scanner(file);
                sc.useDelimiter("\n");
                while (sc.hasNext())  //returns a boolean value  
                    text += sc.next() + "\n";
                sc.close();
                }
            catch(Exception ne) {
                    ne.printStackTrace();
                }

            TextArea textArea = new TextArea(text);
            
            VBox layout = new VBox(10);
            layout.getChildren().addAll(textArea);
            layout.setAlignment(Pos.CENTER);
                
            Scene CreateScene = new Scene(layout, 300, 250);
            popupwindow.setScene(CreateScene);
            popupwindow.showAndWait();
        });

        Create.setOnAction(event -> {
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Game Parameters");

            TextField ScenarioID = new TextField();
            TextField Level = new TextField();
            TextField No_Mines = new TextField();
            TextField Supermine = new TextField();
            TextField Time_Limit = new TextField();

            Label IDLabel = new Label("SCENARIO-ID:");
            Label LevelLabel = new Label("Level:");
            Label MinesLabel = new Label("Number of Mines:");
            Label SupermineLabel = new Label("Supermine:");
            Label TimeLabel = new Label("Time:");

            HBox hboxID = new HBox(10, IDLabel, ScenarioID);
            HBox hboxLevel = new HBox(10, LevelLabel, Level);
            HBox hboxMines = new HBox(10, MinesLabel, No_Mines);
            HBox hboxSuperMine = new HBox(10, SupermineLabel, Supermine);
            HBox hboxTime = new HBox(10, TimeLabel, Time_Limit);

            Button Enter_Params = new Button("Enter");
            Enter_Params.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    //GET the params
                    final String scenarioId = ScenarioID.getText();
                    final int level = Integer.parseInt(Level.getText());
                    final int no_mines = Integer.parseInt(No_Mines.getText());
                    final int supermine = Integer.parseInt(Supermine.getText());
                    final int time_limit = Integer.parseInt(Time_Limit.getText());
                    File File = new File(MEDIALAB_PATH + "SCENARIO-" + scenarioId + ".txt");
                    try{
                        FileWriter writer = new FileWriter(File);
                        writer.write(level + "\n");
                        writer.write(no_mines + "\n");
                        writer.write(time_limit + "\n");
                        writer.write(supermine + "");
                        writer.close();
                    }
                    catch(Exception error) {
                        Popup pop = new Popup();
                        pop.display("ERROR", error.getMessage());
                    }
                    // Close the stage when the "OK" button is clicked
                    popupwindow.close();
                    }
                });

                VBox layout = new VBox(hboxID, hboxLevel, hboxMines, hboxSuperMine, hboxTime, Enter_Params);
                layout.setAlignment(Pos.CENTER);

                Scene Create_Stage_Scene = new Scene(layout, 500, 200);
                popupwindow.setScene(Create_Stage_Scene);
                popupwindow.show();
            });

        Load.setOnAction(event -> {
            TextField Scenario_ID = new TextField();
    
            Stage popupwindow = new Stage();
            popupwindow.setTitle("Load Game Parameters");
    
            //TextField ID = new TextField();
            Label idLabel = new Label("Enter SCENARIO-ID:");
            HBox hbox = new HBox(10, idLabel, Scenario_ID);
    
            Button Enter_Button = new Button("Enter");
            Enter_Button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
    
                    String scenarioId = Scenario_ID.getText();
                    try{
                        game = new Game(MEDIALAB_PATH, scenarioId);
                    }
                    catch(Exception error) {
                        Popup pop = new Popup();
                        pop.display("ERROR", error.getMessage());
                    }
                    popupwindow.close();
                }
            });
    
            VBox layout = new VBox(hbox, Enter_Button);
            layout.setAlignment(Pos.CENTER);

            Scene Load_Scenario_Scene = new Scene(layout, 400, 350);
            popupwindow.setScene(Load_Scenario_Scene);
            popupwindow.show();
        });

        Start.setOnAction(e -> {
            if(game == null) {
                Popup pop = new Popup();
                pop.display("ERROR", "Load a Game First!");
                return ;
            }

            grid = new GridPane();
            BorderPane Nroot = new BorderPane();
            Nroot.setTop(menuBar);
            Nroot.setCenter(grid);
            Scene Nscene = new Scene(Nroot, game.getSize()*54, (game.getSize() + 1)*54 + 26);
            
            primaryStage.setScene(Nscene);
            primaryStage.show();

            grid.getChildren().clear();
            StartGame(grid, NewGameStarted, Solution);
            NewGameStarted = true;
        });

        Exit.setOnAction(e -> {
            Platform.exit();
        });
    }

    public void StartGame(GridPane grid, boolean NewGameStarted, MenuItem Solution) {
        game.newGame();

        HBox hBox = new HBox();
        Rectangle rec_time = new Rectangle(54, 54);
        rec_time.setFill(new ImagePattern(new Image(new File(MEDIALAB_PATH + "Images/Clock.png").toURI().toString())));
        Rectangle rec_mine = new Rectangle(54, 54);
        rec_mine.setFill(new ImagePattern(new Image(new File(MEDIALAB_PATH + "Images/M.png").toURI().toString())));
        Rectangle rec_flag = new Rectangle(54, 54);
        rec_flag.setFill(new ImagePattern(new Image(new File(MEDIALAB_PATH + "Images/F.png").toURI().toString())));
        Label Mines = new Label(), Flags = new Label(), timeLeft = new Label();
        Mines.setText(String.valueOf(game.getMines()));
        Mines.setStyle("-fx-font-size: 3.5em;");
        Flags.setText(String.valueOf(game.getState().getFlags()));
        Flags.setStyle("-fx-font-size: 3.5em;");
        hBox.getChildren().add(rec_mine);
        hBox.getChildren().add(Mines);
        hBox.getChildren().add(rec_flag);
        hBox.getChildren().add(Flags);
        hBox.getChildren().add(rec_time);
        hBox.getChildren().add(timeLeft);
        
        if(NewGameStarted) 
            clock = new Timer(game.getTime() + 1, Solution);
        else
            clock.update();
        clock.set(timeLeft);
        clock.StartTimer();

        Board board = new Board(game, MEDIALAB_PATH);
        
        grid.add(hBox, 0, 0, 1, 1);
        grid.add(board.getBoard(), 0, 1, 1, 1);
        grid.addEventHandler(MouseEvent.MOUSE_PRESSED,  (event) -> {
            //System.out.println(event);
            //throw board;
            int pixel = 54;
            int X = -1, Y = -1;

            double getX = event.getY(), getY = event.getX();
        
            while(getX >= 54) {
                X++;
                getX -= pixel;
            }

            while(getY >= 0) {
                Y++;
                getY -= pixel;
            }

            int click = event.getButton() == MouseButton.PRIMARY ? 0 : 1;
            if(click == 0) game.Attempt();
            //System.out.println("Mouse pressed: (" + X + "," + Y + ") " + click);
            
            try {
                game.play(new Move(X,Y,click));
                Platform.runLater(() -> {
                    Flags.setText(String.valueOf(game.getState().getFlags()));
                    Flags.setStyle("-fx-font-size: 3.5em;");
                });
            }
            catch (Backend.Exceptions.LoserException newe) {
                handle_game_over(newe);
            }
            catch (Backend.Exceptions.WinnerException newe) {
                handle_game_over(newe);
            }
            catch (Exception e) {}
            finally {
                Board Nboard = new Board(game, MEDIALAB_PATH);
                grid.add(Nboard.getBoard(), 0, 1, 1, 1);
            }
        });
    }

    public void handle_game_over(Exception newe) {
        int Round_mines, Round_attempts, Rounds_time;
        String Rounds_winner = "";
        game.lose();
        clock.StopTimer();
        Board Nboard = new Board(game, MEDIALAB_PATH);
        grid.add(Nboard.getBoard(), 0, 1, 1, 1);

        if (newe.getMessage() == "Lost") {
            Popup pop = new Popup();
            pop.display("Game is Over", "You've Lost");
            Rounds_winner = "Computer";
        }

        if (newe.getMessage() == "Won") {
            Popup pop = new Popup();
            pop.display("Game is Over", "You've Won");
            Rounds_winner = "Player";
        }
        Round_mines = game.getMines();
        Round_attempts = game.getAttempts();
        Rounds_time = game.getTime() - clock.getTime();
        try {
            File file = new File(MEDIALAB_PATH + "Rounds.txt");

            //Read previous Rounds
            Scanner sc = new Scanner(file);
            sc.useDelimiter("\n");
            String[] Rounds_info = new String[5];
            int round = 0;
            while (sc.hasNext())  //returns a boolean value
            {
                String Round_i = sc.next();
                Rounds_info[round++] = Round_i + "\n";
            }
            sc.close();

            //Update Rounds File
            String[] temp = new String[5];
            for (int i = 0; i < 4; i++)
                temp[i + 1] = Rounds_info[i];
            Rounds_info[0] = "Mines " + Round_mines + "," + "Attempts " + Round_attempts + "," + "Time " + Rounds_time + "," + "Winnner " + Rounds_winner + "\n";
            for (int i = 1; i < 5; i++)
                Rounds_info[i] = temp[i];

            FileWriter myWriter = new FileWriter(MEDIALAB_PATH + "Rounds.txt");
            for (String i : Rounds_info) {
                if (i == null) break;
                myWriter.write(i);
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }
}