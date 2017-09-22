package javaFXChatApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatClientFXGUIController implements Initializable
{

    public Button btnDisconnect;
    public Button btnConnect;
    public Label lblUsername;
    public ListView listViewOnlineUsers;
    public TextArea txtAConvo;
    public TextField txtFMessage;
    public Button btnSend;


    public void onActionDisconnect(ActionEvent actionEvent)
    {

    }

    public void onActionConnect(ActionEvent actionEvent)
    {
        // Connect clicked
        // Open op connect windows
        Parent loginParent;
        try {
            loginParent = FXMLLoader.load(getClass().getResource("LoginFXGUI_fxml.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Connect / Login");
            loginStage.setScene(new Scene(loginParent, 380, 200));
            loginStage.setResizable(false);
            loginStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onActionSend(ActionEvent actionEvent)
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // This runs once the GUI has loaded.
        btnDisconnect.setDisable(true);
        btnSend.setDisable(true);
    }
}
