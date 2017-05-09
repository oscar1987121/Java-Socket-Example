package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket socket;
	private int clientNum;

	public ServerThread(Socket socket, int clientNum) {

		this.socket = socket;
		this.clientNum = clientNum;
		System.out.println("Current connector: " + clientNum);
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			System.out.println("finish");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
