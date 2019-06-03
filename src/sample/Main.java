package sample;
/**
 * VERSION 1.0.1
 * Battlehip
 * Kiselev Ivan
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application {
    Logic log=new Logic();
    private void setEffect(Logic lo) throws IOException {
        lo.effect();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        setEffect(log);
        StackPane pane = new StackPane();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        pane.getChildren().add(root);
        pane.getChildren().add(log.setPane());
        Scene sc = new Scene(pane);
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(sc);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
