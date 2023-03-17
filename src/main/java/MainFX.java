import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    private static final String TITLE = "SDD2-Project-22/23";
    protected static final BackEnd BACKEND = new BackEnd();

    public static void main(String[] args) {
//       ==BACK-END==
        System.out.println("STARTING THE BACKEND HERE");
        //TODO Activate this
//        try {
//            BackEnd be = new BackEnd();
//            be.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//      ============

//      ==FRONT-END==
        launch(args);
//      =============
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//      ==FRONT-END==

        Stage stage = FXMLLoader.load(getClass().getResource("Page_Launcher.fxml"));
        stage.show();
//      =============


    }
}
