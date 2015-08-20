package com.singh.ziptransfer.clientside;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.singh.ziptransfer.core.ConnectionMode;
import com.singh.ziptransfer.core.ErrorStage;

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
			new ErrorStage("CLIENT - Can't connect to host.");
		}
		if(connection != null){
			view.setState("Connected to " + connection.getInetAddress().getHostName() + " at port " + 
					connection.getPort());
			this.communicate();
		}
	}

	@Override
	public void communicate() {
		File zipFile = new File("NewZipFile.zip");
		int counter = 1;
		while(zipFile.exists()){
			zipFile = new File("NewZipFile" + counter + ".zip");
			counter++;
		}
		try {
			in = connection.getInputStream();
			out = new BufferedOutputStream(new FileOutputStream(zipFile));
		} catch (IOException e) {
			new ErrorStage("CLIENT - Streams could not be set up properly!");
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
			new ErrorStage("CLIENT - Data could not be read.");
		}
		try{
			in.close();
			out.close();
			connection.close();
		}catch(IOException e){
			new ErrorStage("CLIENT - Streams couldn't be properly closed.");;
		}
		view.setFinishMessage("Recieved file! File location:");
		view.setPath(zipFile.getAbsolutePath());
		
	}

	@Override
	public StackPane getView() {
		return view;
	}
	
	private class ClientView extends StackPane {
		
		private VBox box;
		private Text state;
		private Text finished;
		private Text path;
		
		public ClientView(){
			box = new VBox();
			state = new Text();
			finished = new Text();
			path = new Text();
			path.setWrappingWidth(450);
			box.getChildren().addAll(state, finished, path);
			this.getChildren().add(box);
		}
		
		public void setState(String s){
			state.setText(s);
		}
		
		public void setFinishMessage(String s){
			finished.setText(s);
		}
		
		public void setPath(String s){
			path.setText(s);
		}
	}
}
