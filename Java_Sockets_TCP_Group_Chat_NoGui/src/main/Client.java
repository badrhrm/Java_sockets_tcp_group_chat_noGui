package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	Socket socket;
	BufferedReader bufferedreader;
	BufferedWriter bufferedWriter;
	String userName;
	Scanner scanner = new Scanner(System.in);
	
	
	public Client() {
		System.out.println("enter your username (no spaces allowed):");
		this.userName = scanner.next();
		scanner.nextLine();
	}

	
	public void connectToChatRoom() {
		try {
			
			socket = new Socket("localhost",7777);
			if(socket != null) {
				System.out.println("connected to the chat room");
			}
			bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void sendMessages() {
		while (true) {
			String msg = scanner.nextLine(); // does scanner also needs to be closed here like others ?
			try {
				bufferedWriter.write(msg);
				bufferedWriter.newLine();
				bufferedWriter.flush();
				
				if(msg.equalsIgnoreCase("quit")) {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connectToChatRoom();
		client.sendMessages();
		
        
    }
}
