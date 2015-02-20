package edu.ucsb.cs56.S12.ktbui;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.lang.*;
import java.io.PrintWriter;

/** The server on which our Chatroom takes place */ 
public class Chat_Server
{

    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();  // list of sockets: holds Connections
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();     // list of strings holds users in the chat

    public static void main(String[] args) throws IOException
    {
	try
	    {
		final int PORT = 5000;     //port
		ServerSocket server = new ServerSocket(PORT);  //new server socket on 5000, sits and listens.
		System.out.println("Waiting for clients...");

		while(true)           //infinite loop
		    {
			Socket SOCK = server.accept();   //new socket accepts client
			ConnectionArray.add(SOCK);       // add socket connection into connection array

			System.out.println("Client connected from: " + SOCK.getLocalAddress().getHostName());  //print who connects and from where

			addUserName(SOCK); // Adds username to list of users that are online at all times

			Chat_Server_Return CHAT = new Chat_Server_Return(SOCK);  //new chat_server_return object(socket object in constructor)
			Thread X = new Thread(CHAT);  //starts a new thread
		        X.start(); //implements the run method within chat_server_return

		    }
	    }
    
	catch(Exception X) { System.out.print(X); }  // catches exceptions
    }


			
public static void addUserName(Socket X) throws IOException
{

    Scanner INPUT = new Scanner(X.getInputStream());  //gets input stream via scanner object
    String UserName = INPUT.nextLine(); //call next line
    CurrentUsers.add(UserName); //append username to CurrentUsers

    for(int i = 1; i <= Chat_Server.ConnectionArray.size(); i++) // cycle through every connection
	{ 
	    Socket TEMP_SOCK = (Socket) Chat_Server.ConnectionArray.get(i-1); //assigns connection array socket to temporary socket
	    PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream()); //pass in outputstream of temporary socket into PrintWriter
	    OUT.println("!@#" + CurrentUsers); // sends entire arrayList and fills the Jlist with Current users
	    OUT.flush(); //clear outputstream

	}
}

}