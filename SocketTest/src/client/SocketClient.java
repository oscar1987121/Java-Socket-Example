package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	private String a;
	public SocketClient() {
		try {
			Socket socket = new Socket("127.0.0.1", 8888);
			System.out.println("Established a connection...");

			InputStreamReader inputStreamReader = new InputStreamReader(
					System.in);

			BufferedReader reader = new BufferedReader(inputStreamReader);

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);

			String message = reader.readLine();
			while (true) {
				if (message.equals("88")) {
					break;
				}
				System.out.println("[Client]" + message);
				out.println(message);
//				inputStreamReader = new InputStreamReader(
//						System.in);
				reader = new BufferedReader(inputStreamReader);
				System.out.println("reader start");
				message = reader.readLine();
				System.out.println("reader Finish");
			}
			reader.close();
			inputStreamReader.close();
			socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SocketClient();
	}

}
