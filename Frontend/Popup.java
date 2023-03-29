package Frontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {
    public void display(String Title, String Msg)
    {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle(Title);
            
        Label label = new Label(Msg);
        VBox layout = new VBox(10);

        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);
            
        Scene scene1 = new Scene(layout, 400, 350);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();  
    }
}
