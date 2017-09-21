package ChatApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer
{
    // Unique ID for each connection
    private static int uniqueID;
    // an ArrayList to keep the list of Clients
    public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
    public static ArrayList<String> currentUsers = new ArrayList<String>();

    public static void main(String[] args) throws IOException
    {
        try
        {
            final int PORT = 8001;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Server running...");

            while (true)
            {
                Socket SOCK = SERVER.accept();
                connectionArray.add(SOCK);
                System.out.println("Client connected form: " + SOCK.getLocalAddress().getHostName());

                AddUserName(SOCK);

                ChatServerReturn CHAT = new ChatServerReturn(SOCK);
                Thread X = new Thread(CHAT);
                X.start();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static void AddUserName(Socket sock) throws IOException
    {
        Scanner INPUT = new Scanner(sock.getInputStream());
        String userName = INPUT.nextLine();
        //String userName = sock.getLocalAddress().getHostName();
        currentUsers.add(userName);

        for (int i = 1; i <= ChatServer.connectionArray.size() ; i++)
        {
            Socket TEMP_SOCK = (Socket) ChatServer.connectionArray.get(i - 1);
            PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
            OUT.println("#?!" + currentUsers);
            OUT.flush();
        }
    }

}
