package edu.ucsb.cs56.S12.ktbui;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/** Implements the Chat Client */

public class Chat_Client implements Runnable
{
    //globals
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;

    public Chat_Client(Socket X) //takes socket as argument
    {
	this.SOCK = X;
    }


    public void run() //thread started from Chat_GUI
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
    
	catch(Exception X) { System.out.print(X); }

    }

    public void disconnect() throws IOException
    {
	OUT.println(Chat_GUI.UserName + " has disconnected.");      //tell who has disconnected
	OUT.flush();                                                //clear out
	SOCK.close();                                               //close socket
	JOptionPane.showMessageDialog(null, "You disconnected!");   // lets users know that they have disconnnected.
	System.exit(0);
    } 

    public void checkStream() //receive
    {
	while(true)
	    {
		accept();
		
            }

    }

	public void accept()
	{
	    if(INPUT.hasNext()) //if there is an input, get next line
		{
		    String MESSAGE = INPUT.nextLine();
		    
		    if(MESSAGE.contains("!@#"))       //characters from chat_server
			{
			    String TEMP1 = MESSAGE.substring(3);  //everything after !@#
			           TEMP1 = TEMP1.replace("[",""); //peels off brackets
			           TEMP1 = TEMP1.replace("]","");
			    
			    String[] CurrentUsers = TEMP1.split(", "); //split string wherever there is ,

			    Chat_GUI.JL_ONLINE.setListData(CurrentUsers); //sets the list of users for the chat gui
		        }
		    else
			{
			    Chat_GUI.TA_CONVERSATION.append(MESSAGE + "\n");  //if not !@# then append what the message is
			}
		}
	}
    

 
    public void send(String X)
    {
	OUT.println(Chat_GUI.UserName + ": " + X); //take username and add message
	OUT.flush();                               //clear text field
	Chat_GUI.textFieldMessage.setText("");           // set text field to clear after message is sent
    }

}




	       