package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * each client's connection with the server is handled by an obj of this class.
 * so server has a thread for each client so it can listen to its message to broadcast to
 * the Chat Room 
 */
public class ClientConnectionHandler implements Runnable{
	
	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientUserName;
	
	public void broadcastMessageInChatRoom(String msg) {
		for(ClientConnectionHandler clientConHandler : Server.clientsConnections) {
			try {
				BufferedWriter clientSocketWriter = clientConHandler.bufferedWriter;
				// debug 
				System.out.println("Broadcasting to: " + clientConHandler.clientUserName + " message: " + msg);
				clientSocketWriter.write(msg);
				clientSocketWriter.newLine();
				clientSocketWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public ClientConnectionHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			this.clientUserName = bufferedReader.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Server.clientsConnections.add(this);

		broadcastMessageInChatRoom("# Sever: "+ this.clientUserName + " joined the chat room!");
			
		// debug
		System.out.println("Clients in the chat:");
		for (ClientConnectionHandler clientsConn : Server.clientsConnections) {
			System.out.println(clientsConn.clientUserName);
		}
	}

	

	public void listeningForClientSentMessagesToBroadcastInChatRoom() {
		try {
			while(!this.clientSocket.isClosed()) {
				String msg = bufferedReader.readLine();
				
				if (msg.equalsIgnoreCase("quit")) {
					broadcastMessageInChatRoom("# Sever: "+ this.clientUserName+" quitted the chat room!");


					Server.clientsConnections.remove(this);
					
					// debug
					System.out.println("Clients still in the chat:");
					for(ClientConnectionHandler clientsConn : Server.clientsConnections) {
						System.out.println(clientsConn.clientUserName);
					}
					
					this.clientSocket.close();
					this.bufferedReader.close();
					this.bufferedWriter.close();
					
					break;
					
				} else if (msg != null) {
					broadcastMessageInChatRoom(this.clientUserName + ": " + msg);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		listeningForClientSentMessagesToBroadcastInChatRoom();
	}

}
