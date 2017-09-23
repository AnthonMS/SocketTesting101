package javaFXChatApp;

import ChatApp.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginFXGUIController
{

    // globals
    public static ChatClientFX chatClient;
    public static String usernameStatic;

    public TextField txtFUserName;
    public TextField txtFServerHost;

    public void onActionConnect2(ActionEvent actionEvent)
    {
        if (!txtFUserName.getText().trim().equals("") &&
                !txtFServerHost.getText().trim().equals(""))
        {
            // the text fields are not empty
            // we can proceed
            String hostname = txtFServerHost.getText().trim();
            String username = txtFUserName.getText().trim();
            usernameStatic = username;

            try {
                final int PORT = 8001;
                Socket socket = new Socket(hostname, PORT);
                System.out.println("You connected to: " + hostname);

                chatClient = new ChatClientFX(socket);

                // Send username to server so it can be added to online list
                PrintWriter writeServer = new PrintWriter(socket.getOutputStream());
                writeServer.println(username);
                writeServer.flush();

                // Now to start the chatClient as a thread
                Thread clientThread = new Thread(chatClient);
                clientThread.start();

                Parent chatClientGUI;
                try {
                    chatClientGUI = FXMLLoader.load(getClass().getResource("ChatClientFXGUI_fxml.fxml"));
                    Stage chatClientStage = new Stage();
                    chatClientStage.setTitle(username + "'s ChatClientFXGUI");
                    chatClientStage.setScene(new Scene(chatClientGUI, 840, 360));
                    chatClientStage.setResizable(false);
                    chatClientStage.show();

                    Stage thisStage = (Stage) txtFUserName.getScene().getWindow();
                    thisStage.close();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
                // Server not responding... Show popup
            }
            catch (IOException e)
            {
                e.printStackTrace();
                // Don't know why/when this error accure
            }
        }
        else
        {
            // Empty fields, show popup dialog
            System.out.println("Enter something in the fields please...");
        }
    }

    public void onActionCancelConnect(ActionEvent actionEvent)
    {
        // Gets the window that is holding the txtFieldUserName
        // Then closes it, as the user clicks Cancel
        Stage loginStage = (Stage) txtFUserName.getScene().getWindow();
        loginStage.close();
    }
}
