package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientMain
{
    private static ChatClient chatClient;
    public static String userName = "Anon";
    public static String hostName = "";

    // GUI Globals
    public static JFrame mainWindow = new JFrame();
    private static JButton btnAbout = new JButton();
    private static JButton btnConnect = new JButton();
    private static JButton btnDisconnect = new JButton();
    private static JButton btnHelp = new JButton();
    private static JButton btnSend = new JButton();
    private static JLabel lblMessage = new JLabel("Message: ");
    public static JTextField txtFieldMessage = new JTextField(20);
    private static JLabel lblConvo = new JLabel();
    public static JTextArea txtAreaConvo = new JTextArea();
    private static JScrollPane scrlPaneConvo = new JScrollPane();
    private static JLabel lblOnline = new JLabel();
    public static JList lstOnline = new JList();
    private static JScrollPane scrlPaneOnline = new JScrollPane();
    private static JLabel lblLoggedinAs = new JLabel();
    private static JLabel lblLoggedinAsBox = new JLabel();

    // GUI Globals - Login window
    public static JFrame loginWindow = new JFrame();
    public static JTextField txtFieldUserName = new JTextField(20);
    public static JTextField txtFieldHost = new JTextField(20);
    private static JLabel lblEnterHost = new JLabel("Enter Hostname/ip: ");
    private static JButton btnEnter = new JButton("Enter");
    private static JLabel lblEnterName = new JLabel("Enter username: ");
    private static JPanel pnlLogin = new JPanel();

    //----------------------------------------------------------------------------

    public static void main(String[] args)
    {
        buildMainWindow();
        initialize();
    }

    public static void connect()
    {
        try
        {
            final int PORT = 8001;
            final String HOST = "127.0.0.1";
            Socket SOCK = new Socket(hostName, PORT);
            System.out.println("You connected to: " + hostName);

            chatClient = new ChatClient(SOCK);

            // Send name to add to online list
            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(userName);
            OUT.flush();

            Thread X = new Thread(chatClient);
            X.start();

        }
        catch (UnknownHostException e)
        {
            //e.printStackTrace();
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Server not responding...");
            System.exit(0);
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Server not responding2...");
            System.exit(0);
        }
    }


    private static void initialize()
    {
        btnSend.setEnabled(false);
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
    }

    public static void buildLoginWindow()
    {
        loginWindow.setTitle("What's your name?");
        loginWindow.setSize(400, 200);
        loginWindow.setLocation(750, 550);
        loginWindow.setResizable(false);

        pnlLogin.add(lblEnterName);
        pnlLogin.add(txtFieldUserName);
        pnlLogin.add(lblEnterHost);
        pnlLogin.add(txtFieldHost);
        pnlLogin.add(btnEnter);
        loginWindow.add(pnlLogin);

        loginAction();
        loginWindow.setVisible(true);
    }

    private static void buildMainWindow()
    {
        mainWindow.setTitle(userName + "'s Chat Box");
        mainWindow.setSize(450, 500);
        mainWindow.setLocation(750, 550);
        mainWindow.setResizable(false);
        configureMainWindow();
        mainWindowAction();
        mainWindow.setVisible(true);
    }



    public static void loginAction()
    {
        btnEnter.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        loginEnterBtnAction();
                    }
                }
        );
    }

    public static void loginEnterBtnAction()
    {
        if (!txtFieldUserName.getText().equals(""))
        {
            // not empty
            if (txtFieldHost.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please enter a hostname or ip!");
                return;
            }
            hostName = txtFieldHost.getText().trim();
            userName = txtFieldUserName.getText().trim();
            lblLoggedinAsBox.setText(userName);
            ChatServer.currentUsers.add(userName);
            mainWindow.setTitle(userName + "'s Chat Box");
            loginWindow.setVisible(false);
            btnSend.setEnabled(true);
            btnDisconnect.setEnabled(true);
            btnConnect.setEnabled(false);
            connect();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please enter a name!");
        }
    }

    // ---------------------------------------------------------------------------------------

    public static void sendBtnAction()
    {
        if(!txtFieldMessage.getText().equals(""))
        {
            // not empty message
            chatClient.send(txtFieldMessage.getText());
            txtFieldMessage.requestFocus();
        }
    }

    public static void disconnectBtnAction()
    {
        try
        {
            chatClient.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void helpBtnAction()
    {
        JOptionPane.showMessageDialog(null,
                "Multi-Threaded Chat Application \n Anthon Steiness 2017");
    }

    public static void aboutBtnAction()
    {

    }

    //--------------------------------------------------------------------------------------

    public static void mainWindowAction()
    {
        btnSend.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        sendBtnAction();
                    }
                }
        );

        btnDisconnect.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        disconnectBtnAction();
                    }
                }
        );

        btnConnect.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        buildLoginWindow();
                    }
                }
        );

        btnHelp.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        helpBtnAction();
                    }
                }
        );

        btnAbout.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)
                    {
                        aboutBtnAction();
                    }
                }
        );

    }


    // ----------------------------------------------------------------------------------
    private static void configureMainWindow()
    {
        mainWindow.setBackground(new java.awt.Color(255, 255, 255));
        mainWindow.setSize(500, 320);
        mainWindow.getContentPane().setLayout(null);

        btnSend.setBackground(new Color(0, 0, 255));
        btnSend.setForeground(new Color(255, 255, 255));
        btnSend.setText("Send");
        mainWindow.getContentPane().add(btnSend);
        btnSend.setBounds(250, 40, 81, 25);

        btnDisconnect.setBackground(new Color(0, 0, 255));
        btnDisconnect.setForeground(new Color(255, 255, 255));
        btnDisconnect.setText("Disconnect");
        mainWindow.getContentPane().add(btnDisconnect);
        btnDisconnect.setBounds(10, 40, 110, 25);

        btnConnect.setBackground(new Color(0, 0, 255));
        btnConnect.setForeground(new Color(255, 255, 255));
        btnConnect.setText("Connect");
        btnConnect.setToolTipText("");
        mainWindow.getContentPane().add(btnConnect);
        btnConnect.setBounds(130, 40, 110, 25);

        btnHelp.setBackground(new Color(0, 0, 255));
        btnHelp.setForeground(new Color(255, 255, 255));
        btnHelp.setText("Help");
        mainWindow.getContentPane().add(btnHelp);
        btnHelp.setBounds(420, 40, 70, 25);

        btnAbout.setBackground(new Color(0, 0, 255));
        btnAbout.setForeground(new Color(255, 255, 255));
        btnAbout.setText("About");
        mainWindow.getContentPane().add(btnAbout);
        btnAbout.setBounds(340, 40, 75, 25);

        lblMessage.setText("Message:");
        mainWindow.getContentPane().add(lblMessage);
        lblMessage.setBounds(10, 10, 60, 20);

        txtFieldMessage.setForeground(new Color(0, 0, 255));
        txtFieldMessage.requestFocus();
        mainWindow.getContentPane().add(txtFieldMessage);
        txtFieldMessage.setBounds(70, 4, 260, 30);

        lblConvo.setHorizontalAlignment(SwingConstants.CENTER);
        lblConvo.setText("Conversation");
        mainWindow.getContentPane().add(lblConvo);
        lblConvo.setBounds(100, 70, 140, 16);

        txtAreaConvo.setColumns(20);
        txtAreaConvo.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtAreaConvo.setForeground(new Color(0, 0, 255));
        txtAreaConvo.setLineWrap(true);
        txtAreaConvo.setRows(5);
        txtAreaConvo.setEditable(false);

        scrlPaneConvo.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrlPaneConvo.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrlPaneConvo.setViewportView(txtAreaConvo);
        mainWindow.getContentPane().add(scrlPaneConvo);
        scrlPaneConvo.setBounds(10, 90, 330, 180);

        lblOnline.setHorizontalAlignment(SwingConstants.CENTER);
        lblOnline.setText("Currently Online");
        lblOnline.setToolTipText("");
        mainWindow.getContentPane().add(lblOnline);
        lblOnline.setBounds(350, 70, 130, 16);

        lstOnline.setForeground(new Color(0, 0, 255));


        scrlPaneOnline.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrlPaneOnline.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrlPaneOnline.setViewportView(lstOnline);
        mainWindow.getContentPane().add(scrlPaneOnline);
        scrlPaneOnline.setBounds(350, 90, 130, 180);

        lblLoggedinAs.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblLoggedinAs.setText("Currently Logged In As");
        mainWindow.getContentPane().add(lblLoggedinAs);
        lblLoggedinAs.setBounds(348, 0, 140, 15);

        lblLoggedinAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoggedinAsBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblLoggedinAsBox.setForeground(new Color(255, 0, 0));
        lblLoggedinAsBox.setBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0)));
        lblLoggedinAsBox.setText("Anon");
        mainWindow.getContentPane().add(lblLoggedinAsBox);
        lblLoggedinAsBox.setBounds(340, 17, 150, 20);
    }

}





























