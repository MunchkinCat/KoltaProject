/**
 * Created by egrstudent on 11/2/16.
 */

import gui.ScreenLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SimulATM extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("gui/SimulatedATM.fxml"));

        Scene scene = new Scene(root, 600, 600);

        ScreenLoader loader = new ScreenLoader();
        String[] welcomeScreen = loader.getScreen(1);

        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);



        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
