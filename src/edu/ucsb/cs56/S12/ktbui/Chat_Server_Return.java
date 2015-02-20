package edu.ucsb.cs56.S12.ktbui;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;

/** Return class for the chat server */

public class Chat_Server_Return implements Runnable //threaded object
{
    //globals
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";



    public Chat_Server_Return(Socket X) //constructer accepts a Socket object
    {
	this.SOCK = X;                                   //assigns socket to global sock

    }



    public void CheckConnection() throws IOException
    {
	if(!SOCK.isConnected())   //if socket is not connected
	    {
		for(int i =1; i<= Chat_Server.ConnectionArray.size(); i++) //iterate through connection array
		    {
			if(Chat_Server.ConnectionArray.get(i) == SOCK)
			    {
				Chat_Server.ConnectionArray.remove(i); //remove socket
			    }
		    }
		for(int i =1; i <= Chat_Server.ConnectionArray.size();i++)
		    {
			Socket TEMP_SOCK = (Socket) Chat_Server.ConnectionArray.get(i-1);   //
			PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
			TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected "); //says who and from where they disconnected
			TEMP_OUT.flush();
		         //shows disconnection at server
			
			System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected" ) ;

		    }
	    }
      

    }


    public void run()  //called from Chat_server through starting thread, goes if here socket is connected
    {
	try
	    {       try
                    {
			INPUT = new Scanner(SOCK.getInputStream());    //input stream
                        OUT = new PrintWriter(SOCK.getOutputStream()); // output stream
		
			
		
		        
	      
			while(true)   //infinite loop
			    {
				CheckConnection();

				if(!INPUT.hasNext()) //if theres no input
				    {
				    return;  //return
				    }

				MESSAGE = INPUT.nextLine();  //message

				System.out.println("Client said: " + MESSAGE); // print what client said

				for (int i = 1; i <= Chat_Server.ConnectionArray.size(); i++) //iterate through connection array
				    {
					Socket TEMP_SOCK =  Chat_Server.ConnectionArray.get(i-1);
					PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream()); 
					TEMP_OUT.println(MESSAGE);                    //print message
					TEMP_OUT.flush();
					System.out.println("Sent to: " + TEMP_SOCK.getLocalAddress().getHostName()); // who recieved message
				    }

			    }

		    }
	    
		finally
		    {
			SOCK.close();
		    }
	    
	    }
	catch(Exception X) { System.out.print(X);}

    }


}


	       