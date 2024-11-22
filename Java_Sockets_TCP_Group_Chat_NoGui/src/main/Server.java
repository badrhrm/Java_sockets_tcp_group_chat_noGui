package main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	private ServerSocket serverSocket;
	static ArrayList<ClientConnectionHandler> clientsConnections = new ArrayList();
	
	
	public void handleEachConnection() {
		while (true) {
			try {
	            System.out.println("Waiting for a client to connect...");
	            Socket clientSocket = this.serverSocket.accept();
	            System.out.println("Client connected!");
	            new Thread(new ClientConnectionHandler(clientSocket)).start();
			} catch (IOException e) {
                e.printStackTrace();
            }    
		}
	}
	public void run() {
		try {
			this.serverSocket = new ServerSocket(7777);
			System.out.println("Chat server started...");
			handleEachConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
    public static void main(String[] args) {
    	Server server = new Server();
    	server.run();
    }
    
}

