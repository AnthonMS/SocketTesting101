package javaFXChatApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientFX implements Runnable
{
    // Globals
    private Socket socket;
    private Scanner input;
    private Scanner send = new Scanner(System.in);
    private PrintWriter writeServer;

    // get the ChatClientFXController to access elements inside
    private ChatClientFXGUIController controller;


    public ChatClientFX(Socket sock)
    {
        this.socket = sock;
    }

    // ---------------------------------------------------------------------------------------

    @Override
    public void run()
    {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatClientFXGUI_fxml.fxml"));
            controller = (ChatClientFXGUIController) loader.getController();

            try {
                input = new Scanner(socket.getInputStream());
                writeServer = new PrintWriter(socket.getOutputStream());
                writeServer.flush();
                checkStream();
            }
            finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------

    private void checkStream()
    {
        while (true)
        {
            recieve();
        }
    }

    // ---------------------------------------------------------------------------------------

    private void recieve()
    {
        if (input.hasNext())
        {
            String message = input.nextLine();

            if (message.contains("#?!"))
            {
                String tempString = message.substring(3);
                tempString = tempString.replace("[", "");
                tempString = tempString.replace("]", "");

                String[] currentUsers = tempString.split(", ");

                //ChatClientFXGUIController.listViewOnlineUsersStatic.setUserData(currentUsers);
                //controller.listViewOnlineUsers.setUserData(currentUsers);
            }
            else
            {
                //ChatClientFXGUIController.txtAConvoStatic.appendText(message + "\n");
                controller.txtAConvo.appendText(message + "\n");
            }
        }
    }

    // ---------------------------------------------------------------------------------------

    public void disconnect() throws IOException
    {
        writeServer.println(LoginFXGUIController.usernameStatic + " has disconnected.");
        writeServer.flush();
        socket.close();
    }

    // ---------------------------------------------------------------------------------------

    public void send(String message)
    {
        writeServer.println(LoginFXGUIController.usernameStatic + ": " + message);
        writeServer.flush();
        //ChatClientFXGUIController.txtFMessageStatic.setText("");
        controller.txtFMessage.setText("");
    }

}
