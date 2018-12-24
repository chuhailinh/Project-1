package project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
	private ServerSocket listener;

	public void startServer() throws IOException{
		listener = new ServerSocket(1112);
		while(true){
			//tạo 1 cổng nghe.
			Socket socketOfServer = listener.accept();
			
			ClientThread ct = new ClientThread(socketOfServer);
			ct.start();
		}
	}
	public static void main(String[] args) throws IOException{
		new Server().startServer();
	}
}

