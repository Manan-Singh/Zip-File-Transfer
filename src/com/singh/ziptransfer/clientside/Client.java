package com.singh.ziptransfer.clientside;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import com.singh.ziptransfer.core.ConnectionMode;

public class Client implements ConnectionMode {
	
	private Socket connection;
	private InputStream in;
	private BufferedOutputStream out;
	private ClientView view;

	public Client(String hostName, int port) throws UnknownHostException {
		view = new ClientView();
		try {
			connection = new Socket(hostName, port);
		} catch (IOException e) {
			System.out.println("CLIENT - Can't connect to host.");
			e.printStackTrace();
		}
		view.setState("Connected to " + connection.getInetAddress().getHostName() + " at port " + 
				connection.getPort());
		System.out.println("CLIENT - Connected to host at " + connection.getInetAddress().getHostAddress());
		this.communicate();
	}

	@Override
	public void communicate() {
		//Test for now
		try {
			in = connection.getInputStream();
			out = new BufferedOutputStream(new FileOutputStream("NewZipFile.zip"));
		} catch (IOException e) {
			System.out.println("");
			e.printStackTrace();
		}
		byte[] buffer = new byte[2048];
		int count = 0;
		try {
			while((count = in.read(buffer, 0, buffer.length)) > 0){
				out.write(buffer, 0, count);
			}
			for(byte x : buffer){
				System.out.print(x + " | ");
			}
		} catch (IOException e) {
			System.out.println("CLIENT - Data could not be read.");
			e.printStackTrace();
		}
		try{
			in.close();
			out.close();
			connection.close();
		}catch(IOException e){
			System.out.println("SERVER - Streams couldn't be properly closed.");
			e.printStackTrace();
		}
		System.out.println("CLIENT - Done!");
	}

	@Override
	public StackPane getView() {
		return view;
	}
	
	private class ClientView extends StackPane {
		
		private Text state;
		
		public ClientView(){
			state = new Text();
			this.getChildren().add(state);
		}
		
		public void setState(String s){
			state.setText(s);
		}
	}
}
