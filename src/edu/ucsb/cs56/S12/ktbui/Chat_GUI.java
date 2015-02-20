package edu.ucsb.cs56.S12.ktbui;
import javax.swing.*;
import java.io.PrintWriter;
import java.net.*;
import java.lang.String;

/** The Graphical User Interfce for our Chatroom */
public class Chat_GUI
{
    //globals
    private static Chat_Client ChatClient;
    public static String UserName = "";

    //main window
    public static JFrame MainWindow = new JFrame();  //main window
    private static JButton AboutButton = new JButton();  //about button
    private static JButton CONNECT = new JButton(); // connect button
    private static JButton DISCONNECT = new JButton(); //disconnect button
    private static JButton HELP = new JButton(); //help button
    private static JButton SEND = new JButton(); //send button
    private static JLabel messageLabel = new JLabel(" Message: " ); //label
    public static JTextField textFieldMessage = new JTextField(20);
    private static JLabel conversationLabel = new JLabel(); //conversation label
    public static JTextArea TA_CONVERSATION = new JTextArea(); //conversation of users
    private static JScrollPane SP_CONVERSATION = new JScrollPane(); //scroll bar
    private static JLabel L_ONLINE = new JLabel();  // users logged in label
    public static JList JL_ONLINE = new JList(); // users that are logged in
    private static JScrollPane SP_ONLINE = new JScrollPane();
    private static JLabel L_LoggedInAs = new JLabel(); //currently logged in as
    private static JLabel L_LoggedInAsBox = new JLabel(); // box for users logged in

    
    // login window

    public static JFrame LogInWindow = new JFrame();  //log in window jframe
    public static JTextField TF_UserNameBox = new JTextField(20); //log in text field
    private static JButton B_ENTER = new JButton("ENTER"); //enter button
    private static JLabel L_EnterUserName = new JLabel(" Enter username: ");
    private static JPanel P_LogIn = new JPanel(); //log in panel

    public static void main(String args[]) //main
    {
	createMainWindow(); //builds main window
	setButtons();  //sets  up initial buttons

    }

        public static void Connect() //connects to socket
    {
	try
	    {
		final int PORT = 5000;
		final String HOST = "localhost";
		Socket SOCK = new Socket(HOST,PORT);  //connects to socket using local host and port
		System.out.println ("You Connected to: " + HOST);

		ChatClient = new Chat_Client(SOCK);

		PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
		OUT.println(UserName); //prints the username
		OUT.flush();

		Thread X = new Thread(ChatClient); //new chat client thread
		X.start(); //begins runnable method of chat_client

	    }
	catch(Exception X)
	    {
		System.out.print(X);   //exits on exception
		System.exit(0);

	    }
            
    }


  
    public static void createMainWindow()
    {
	MainWindow.setTitle(UserName + " 's Chat Box");
	MainWindow.setSize(450,500);
	MainWindow.setLocation(220,180);
	MainWindow.setResizable(false);
	buildMainWindow();
	mainWindowAction();
	MainWindow.setVisible(true);
    }

    public static void buildMainWindow() //configures everything needed in the main window
    {
	MainWindow.setBackground(new java.awt.Color(255,255,255));
	MainWindow.setSize(500,320);
	MainWindow.getContentPane().setLayout(null);
	SEND.setBackground(new java.awt.Color(0,0,255));
        SEND.setForeground(new java.awt.Color(255,255,255));
        SEND.setText("SEND");
        MainWindow.getContentPane().add(SEND);
        SEND.setBounds(250,40,110,26);

        DISCONNECT.setBackground(new java.awt.Color(0,0,255));
        DISCONNECT.setForeground(new java.awt.Color(255,255,255));
        DISCONNECT.setText("DISCONNECT");
        MainWindow.getContentPane().add(DISCONNECT);
        DISCONNECT.setBounds(10,40,110,26);

        CONNECT.setBackground(new java.awt.Color(0,0,255));
        CONNECT.setForeground(new java.awt.Color(255,255,255));
        CONNECT.setText("CONNECT");
        CONNECT.setToolTipText("");
        MainWindow.getContentPane().add(CONNECT);
        CONNECT.setBounds(130,40,110,26);

        HELP.setBackground(new java.awt.Color(0,0,255));
        HELP.setForeground(new java.awt.Color(255,255,255));
        HELP.setText("HELP");
        MainWindow.getContentPane().add(HELP);
        HELP.setBounds(370,40,110,26);

	
        messageLabel.setText("Message: " );
        MainWindow.getContentPane().add(messageLabel);
        messageLabel.setBounds(10,10,100,20);

        textFieldMessage.setForeground(new java.awt.Color(0,0,255));
	textFieldMessage.requestFocus();
        MainWindow.getContentPane().add(textFieldMessage);
	textFieldMessage.setBounds(100,4,225,30);

        conversationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        conversationLabel.setText("Conversation");
        MainWindow.getContentPane().add(conversationLabel);
        conversationLabel.setBounds(100,65,140,30);

        TA_CONVERSATION.setColumns(20);
        TA_CONVERSATION.setFont(new java.awt.Font("Tahoma",0,12));

        TA_CONVERSATION.setForeground(new java.awt.Color(0,0,255));
        TA_CONVERSATION.setLineWrap(true);
        TA_CONVERSATION.setRows(5);
	TA_CONVERSATION.setEditable(false);

        SP_CONVERSATION.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_CONVERSATION.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_CONVERSATION.setViewportView(TA_CONVERSATION);
        MainWindow.getContentPane().add(SP_CONVERSATION);
        SP_CONVERSATION.setBounds(10,90,330,160);

        L_ONLINE.setHorizontalAlignment(SwingConstants.CENTER);
        L_ONLINE.setText("Currently Online");
        L_ONLINE.setToolTipText("");
        MainWindow.getContentPane().add(L_ONLINE);
        L_ONLINE.setBounds(350, 70 , 130, 16);

	
       JL_ONLINE.setForeground(new java.awt.Color(0,0,255));
	

       SP_ONLINE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       SP_ONLINE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       SP_ONLINE.setViewportView(JL_ONLINE);
       MainWindow.getContentPane().add(SP_ONLINE);
       SP_ONLINE.setBounds(350,90,130,180);

       L_LoggedInAs.setFont(new java.awt.Font("Tahoma",0,12));
       L_LoggedInAs.setText("Currently Logged In As");
       MainWindow.getContentPane().add(L_LoggedInAs);
       L_LoggedInAs.setBounds(348,0,140,15);

       L_LoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
       L_LoggedInAsBox.setFont(new java.awt.Font("Tahoma",0,12));
       L_LoggedInAsBox.setForeground(new java.awt.Color(255,0,0));
       L_LoggedInAsBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0)));
       MainWindow.getContentPane().add(L_LoggedInAsBox);
       L_LoggedInAsBox.setBounds(340,17,150,20);

    }

    public static void setButtons()  //turns off Send and Disconnect button until user is connected
    {
	SEND.setEnabled(false);
	DISCONNECT.setEnabled(false);
	CONNECT.setEnabled(true);

    }

    public static void BuildLogInWindow()  //Builds log in window at the beginning
    {
	LogInWindow.setTitle("What's your name?");
	LogInWindow.setSize(400,100);
	LogInWindow.setLocation(250,200);
	LogInWindow.setResizable(false);
	P_LogIn.add(L_EnterUserName);
        P_LogIn.add(TF_UserNameBox);	
	P_LogIn.add(B_ENTER);
	LogInWindow.add(P_LogIn);

	loginAction();
	LogInWindow.setVisible(true);

    }

     
    public static void loginAction() //listener for after log in name is entered
    {
	B_ENTER.addActionListener(
	  new java.awt.event.ActionListener()
	  {
	      public void actionPerformed(java.awt.event.ActionEvent evt)
	      { enterAction(); }

	  }

				  );

    }

  

    public static void enterAction() //upon entering of username
    {
	if(!TF_UserNameBox.getText().equals(""))
	    {

		UserName = TF_UserNameBox.getText().trim();  //get username and trim
		L_LoggedInAsBox.setText(UserName); //set name of log in box to user
		Chat_Server.CurrentUsers.add(UserName); //add username to array
		MainWindow.setTitle(UserName + "'s Chat Box"); //change name
		LogInWindow.setVisible(false); //close log in window
		SEND.setEnabled(true); //set button to on
		DISCONNECT.setEnabled(true); //set button to on
		CONNECT.setEnabled(false); //set button off
		Connect(); // connect to socket

	    }
	else
	    { JOptionPane.showMessageDialog(null, "Please enter a name: "); }
	
    }


    public static void mainWindowAction()
    {
	SEND.addActionListener(
	new java.awt.event.ActionListener()
	{
	    public void actionPerformed(java.awt.event.ActionEvent evt)
	    { sendAction(); }
	}

    );

    DISCONNECT.addActionListener(
    new java.awt.event.ActionListener()
    {
	public void actionPerformed(java.awt.event.ActionEvent evt)
	{ disconnectAction(); }

    }

				   );

     HELP.addActionListener(
    new java.awt.event.ActionListener()
    {
	public void actionPerformed(java.awt.event.ActionEvent evt)
	{ helpAction(); }

    }

				   );

     
     CONNECT.addActionListener(
    new java.awt.event.ActionListener()
    {

	public void actionPerformed(java.awt.event.ActionEvent evt)
	{ BuildLogInWindow(); }

    }

				 );

    

     
   
    }

  


    public static void sendAction()  //sends chatclient message to users
    {

	if(!textFieldMessage.getText().equals(""))
	    {
		ChatClient.send(textFieldMessage.getText());
		textFieldMessage.requestFocus();
	    }

    }

    public static void disconnectAction() //disconnects
    {
	try
	    {
		ChatClient.disconnect(); // disconnect
	    }
	catch(Exception Y) {Y.printStackTrace();}

    }

    public static void helpAction()
    {
	JOptionPane.showMessageDialog(null, "Press connect and enter a name to start chatting. Run the Chat_Gui again to add more users.");

    }
    
				   
     










    

} // class Chat_GUI


			     
							   
			     
					

			    			  
 
      
			 