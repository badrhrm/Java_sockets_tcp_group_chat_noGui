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
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	String userName;
	Scanner scanner = new Scanner(System.in);
	
	public Client() {
		System.out.println("enter ur username");
		this.userName = scanner.nextLine();
	}
	
	public void connectToChatRoom() {
		try {
			socket = new Socket("localhost",7777);
			if (socket!=null) {
				System.out.println("connection to the server is successful");
			}
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessages() {
		while(true) {
			
			String msg = scanner.nextLine();
			try {
				bufferedWriter.write(msg);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
