package javaFXChatApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatClientFXGUI extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("ChatClientFXGUI_fxml.fxml"));
        primaryStage.setTitle("ChatClientFXGUI");
        primaryStage.setScene(new Scene(root, 840, 360));
        primaryStage.show();
        primaryStage.setResizable(false);

    }


}
