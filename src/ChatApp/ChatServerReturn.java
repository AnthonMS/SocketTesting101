package ChatApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerReturn implements Runnable
{
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String message = "";

    public ChatServerReturn(Socket x)
    {
        this.SOCK = x;
    }

    public void CheckConnection() throws IOException
    {
        if (!SOCK.isConnected())
        {
            for (int i = 1; i <= ChatServer.connectionArray.size() ; i++)
            {
                if (ChatServer.connectionArray.get(i) == SOCK)
                {
                    ChatServer.connectionArray.remove(i);
                }
            }

            for (int i = 1; i <= ChatServer.connectionArray.size() ; i++)
            {
                Socket TEMP_SOCK = (Socket) ChatServer.connectionArray.get(i - 1);
                PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected!");
                TEMP_OUT.flush();
                // Show disconnetion at SERVER
                System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected!");
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());

                while (true)
                {
                    CheckConnection();

                    if (!INPUT.hasNext())
                    {
                        return;
                    }

                    message = INPUT.nextLine();

                    System.out.println("Client said: " + message);

                    for (int i = 1; i <= ChatServer.connectionArray.size() ; i++)
                    {
                        Socket TEMP_SOCK = (Socket) ChatServer.connectionArray.get(i - 1);
                        PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println(message);
                        TEMP_OUT.flush();
                        System.out.println("Sent to: " + TEMP_SOCK.getLocalAddress().getHostName());
                    } // Close for loop
                } // Close while loop
            } // Close inner try
            finally
            {
                SOCK.close();
            }
        } // Close outer try
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
