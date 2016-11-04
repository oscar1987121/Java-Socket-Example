package handle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputHandle implements Runnable {

	private Socket socket;
	private boolean isConnect;
	private IMessageReceiver receiver;

	public InputHandle(Socket socket, IMessageReceiver receiver) {
		this.socket = socket;
		this.receiver = receiver;
		isConnect = true;
	}

	@Override
	public void run() {
		if (socket != null) {
			InputStream inputStream = null;
			InputStreamReader reader = null;
			BufferedReader br = null;
			try {
				inputStream = socket.getInputStream();
				reader = new InputStreamReader(inputStream);
				br = new BufferedReader(reader);
				while (isConnect) {
					String message = br.readLine();
					if (receiver != null) {
						receiver.receive(message);
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
					if (reader != null) {
						reader.close();
					}
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

	public void close() {
		isConnect = false;
	}

}
