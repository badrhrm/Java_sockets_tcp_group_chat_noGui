package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
	
	ServerSocket serverSocket;
	Socket socket;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	
	
	
	public void run() {
		
		try {
			
			this.serverSocket = new ServerSocket(7777);
			System.out.println("waiting for connections...");
			
			while(true) {
				try {
					
					this.socket = serverSocket.accept();
					if (this.socket != null) {
						System.out.println("client is connected.");
					}
					this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					while(true) {
						String receivedMsg = this.bufferedReader.readLine();
						System.out.println("client : " + receivedMsg ); 
						
						} 
					}catch (IOException e) {
						e.printStackTrace();
					}
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
	
}
