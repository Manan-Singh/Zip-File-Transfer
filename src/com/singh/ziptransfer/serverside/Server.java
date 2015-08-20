package com.singh.ziptransfer.serverside;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.singh.ziptransfer.core.ConnectionMode;
import com.singh.ziptransfer.core.ErrorStage;
import com.singh.ziptransfer.core.MainDriver;

public class Server implements ConnectionMode {

	private ServerSocket server;
	private Socket clientSocket;
	private BufferedInputStream in;
	private OutputStream out;
	private ServerView view;
	
	public Server(int port){
		try {
			server = new ServerSocket(port);
			view = new ServerView();
		} catch (IOException e) {
			new ErrorStage("Server had trouble being set up.");
			e.printStackTrace();
		}
		Task task = new Task(){

			@Override
			protected Object call() throws Exception {
				try{
					System.out.println("SERVER- Waiting for a client to connect to...");
					view.executeConnectionStates();
					clientSocket = server.accept();
				}catch(IOException e){
					System.out.println("Server had trouble connecting to a client.");
					e.printStackTrace();
				}
				communicate();
				return null;
			}
			
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void communicate() {
		File f = MainDriver.getMainFile();
		byte[] buffer = new byte[(int)f.length()];
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			out = clientSocket.getOutputStream();
		} catch (FileNotFoundException e) {
			new ErrorStage("SERVER - Had trouble communicating with the client.");
		} catch (IOException e){
			new ErrorStage("SERVER - There was an i/o error.");
		}
		int count = 0;
		try {
			while((count = in.read(buffer, 0, buffer.length)) > 0){
				out.write(buffer, 0, count);
			}
		} catch (IOException e) {
			new ErrorStage("Error in sending file.");
		} 
		try{
			in.close();
			out.close();
			clientSocket.close();
			server.close();
		}catch(IOException e){
			new ErrorStage("SERVER - Streams couldn't be properly closed.");
		}
		view.displayFinish();
	}
	
	public StackPane getView(){
		return view;
	}
	
	private class ServerView extends StackPane {
		
		private VBox box;
		private Text state;
		private Text finished;
		String[] connectionStates = {"Connecting to client.", "Connecting to client..",
				"Connecting to client..."};
		
		public ServerView(){
			box = new VBox();
			state = new Text("Server is idle");
			finished = new Text("");
			box.getChildren().addAll(state, finished);
			this.getChildren().add(box);
		}
		
		public void setState(String state){
			this.state.setText(state);
		}
		
		public void displayFinish(){
			finished.setText("Finished! Transfer complete!");
		}
		
		public void executeConnectionStates(){
			Task displayConnection = new Task(){

				@Override
				protected Object call() throws Exception {
					int i = 0;
					while(clientSocket == null){
						setState(connectionStates[i]);
						i++;
						if(i > connectionStates.length - 1){
							i = 0;
						}
						Thread.sleep(500);
					}
					setState("Connected to a client. Transfer of file in progress.");
					return null;
				}
			};
			Thread t = new Thread(displayConnection);
			t.setDaemon(true);
			t.start();
		}
	}
}
