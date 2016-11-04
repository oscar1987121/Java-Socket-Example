package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public SocketServer() {

		boolean listening = true;
		int connectionCount = 0;
		try {
			ServerSocket socket = new ServerSocket(8888);
			System.out.println("Server starts...");

			Socket clientSocket = socket.accept();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			
			while (true) {
				System.out.println("Read Line");
				String message = reader.readLine();
				if(message.equals("end")){
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(clientSocket.getOutputStream())), true);
					out.println("Hello");
					out.flush();
				}
				System.out.println("[From Client]"+message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new SocketServer();
	}

}
