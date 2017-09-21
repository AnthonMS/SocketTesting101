package ChatApp;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable
{
    // Globals
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;

    public ChatClient(Socket sock)
    {
        this.SOCK = sock;
    }

    // ------------------------------------------------------------------------------------------

    @Override
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                checkStream();
            }
            finally
            {
                SOCK.close();
            }
        }
        catch (IOException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
    }

    // ------------------------------------------------------------------------------------------

    public void disconnect() throws IOException
    {
        OUT.println(ChatClientMain.userName + " has disconnected.");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null, "You disconnected!");
        System.exit(0);
    }

    // ------------------------------------------------------------------------------------------

    public void checkStream()
    {
        while (true)
        {
            recieve();
        }
    }

    // ------------------------------------------------------------------------------------------

    public void recieve()
    {
        if (INPUT.hasNext())
        {
            String message = INPUT.nextLine();

            if (message.contains("#?!"))
            {
                String tempString = message.substring(3);
                tempString = tempString.replace("[", "");
                tempString = tempString.replace("]", "");

                String[] currentUsers = tempString.split(", ");

                ChatClientMain.lstOnline.setListData(currentUsers);
            }
            else
            {
                ChatClientMain.txtAreaConvo.append(message + "\n");
            }
        }
    }

    // ------------------------------------------------------------------------------------------

    public void send(String message)
    {
        OUT.println(ChatClientMain.userName + ": " + message);
        OUT.flush();
        ChatClientMain.txtFieldMessage.setText("");
    }

    // ------------------------------------------------------------------------------------------
}
