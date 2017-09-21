import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server1
{
    private static ServerSocket serverSocket;
    private static Socket socket;
    public static void main(String args[])
    {
        try
        {
            // Create socket that listens on port 1337
            serverSocket = new ServerSocket(8001);
            System.out.println("Server running...");
            boolean running = true;

            while(running)
            {
                // If someone connects,
                // accept and notify server
                socket = serverSocket.accept();
                System.out.println("Client connected..");

                // To communicate with the client,
                // we need to specify input & output stream.
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                Scanner in = new Scanner(inputStream);
                // Når vi skriver til output streamen bruger vi her en PrintWriter
                PrintWriter writeClient = new PrintWriter(outputStream, true);


                boolean done = false;
                while (!done && in.hasNextLine())
                {
                    // Her starter scanneren arbejde
                    String stream = in.nextLine();
                    if (stream.equals("Close now"))
                    {
                        done = true;
                    }
                    else
                    {
                        // Når vi skriver, sender vi en linje emed PrintWriter
                        writeClient.println(stream);
                        //System.out.println(stream);
                    }
                }

                socket.close();
                System.out.println("Forbindelsen blev lukket!");
                running = false;


            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
