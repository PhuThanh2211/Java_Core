package com.phuthanh.java.networking.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
A network is a system of computers connected together so they can share resources and communicate with each other
Networking refers to how the connected computers communicate
The java.net package contains the classes you'll use to establish connections between computers and then send message between them
When writing networking code, you'll need to be familiar with threads and IO streams

When communicating using TCP/IP, the sequence of events is as follows:
1/ The client opens a connection to the server
2/ The client sends a request to the server
3/ The server sends a response to the client
4/ The client closes the connection to the server

Step 2 and 3 may be repeated multiple times before the connection is closed
*/

public class ServerTCP {

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			System.out.println("Start Server......");
			
			// Each client connect - each thread start
			while (true) {
				Socket socket = serverSocket.accept();
				Echoer echoer = new Echoer(socket);
				echoer.start();
			}
			
		} catch (IOException e) {
			System.out.println("Server exception " + e.getMessage());
		}
	}

}
