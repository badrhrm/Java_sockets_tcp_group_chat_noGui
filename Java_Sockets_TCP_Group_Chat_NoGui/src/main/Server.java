package main;

import java.io.*;
import java.net.*;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	
	public void run() {
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("Chat server started...");
			
			while (true) {
				try {
		            System.out.println("Waiting for a client to connect...");
		            socket = serverSocket.accept();
		            if(socket != null) {
		            	System.out.println("Client connected!");
		            }
		            
		            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		            
		           
		            
		            while (true) {
		            	String receivedMsg = bufferedReader.readLine();
		            	if(receivedMsg != null) {
		            		System.out.println("Client: " + receivedMsg);
		            	}
		            }
		            
				} catch (IOException e) {
	                e.printStackTrace();
	            }    
			}
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) {
    	Server server = new Server();
    	server.run();
    }
    
}

