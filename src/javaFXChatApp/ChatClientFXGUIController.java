package javaFXChatApp;

import ChatApp.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ChatClientFXGUIController implements Initializable
{

    // Globals for ChatClientFXGUI
    public Button btnDisconnect;
    public Button btnConnect;
    public Label lblUsername;
    public ListView listViewOnlineUsers;
    public TextArea txtAConvo;
    public TextField txtFMessage;
    public Button btnSend;

    //public static TextArea txtAConvoStatic;
    //public static TextField txtFMessageStatic;
    //public static Button btnSendStatic;
    //public static ListView listViewOnlineUsersStatic;


    // globals for the chatClient
    private static ChatClientFX chatClient;

    // Globals for the login / Connect screen
    public TextField txtFUserName;
    public TextField txtFServerHost;

    // ----------------------------------------------------------------------------------------------

    // Disconnect button handler for ChatClientFXGUI
    public void onActionDisconnect(ActionEvent actionEvent)
    {

    }

    // ----------------------------------------------------------------------------------------------

    // Connect  button handler for ChatClientFXGUI
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

            Stage thisStage = (Stage) btnDisconnect.getScene().getWindow();
            thisStage.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------------------------------

    // Send  button handler for ChatClientFXGUI
    public void onActionSend(ActionEvent actionEvent)
    {
        //txtAConvo.appendText("Helloooooooo\n");
        if (txtFMessage.getText().equals(""))
        {
            // not empty message
            chatClient.send(txtFMessage.getText());
            txtFMessage.requestFocus();
            //txtFMessage.setText("");
        }
    }

    // ----------------------------------------------------------------------------------------------

    // method that initializes the GUI when it has loaded
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // This runs once the GUI has loaded.

        chatClient = LoginFXGUIController.chatClient;



        if (chatClient == null)
        {
            // ChatClient is null, therefor the client is not connected
            // and should not have the option to disconnect or send
            btnDisconnect.setDisable(true);
            btnSend.setDisable(true);
        }
        else
        {
            // client is connected, therefor no option to connect again
            btnConnect.setDisable(true);
        }
    }

    // -------------------------------------------------------------------------------------------------

    public void setUserData(Object o)
    {
        listViewOnlineUsers.setUserData(o);
    }

    private ChatClientFXGUIController getController()
    {
        return this;
    }

}























