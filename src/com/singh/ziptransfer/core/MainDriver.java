package com.singh.ziptransfer.core;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.singh.ziptransfer.clientside.Client;
import com.singh.ziptransfer.clientside.ClientPromptPane;
import com.singh.ziptransfer.serverside.Server;
import com.singh.ziptransfer.serverside.ServerPromptPane;
import com.singh.ziptransfer.ziphandling.FilePickPrompt;

/**
 * @author Manan Singh
 */
public class MainDriver extends Application {

	private static Stage primaryStage;
	private static BorderPane root;
	private static Scene scene;
	private static ConnectionMode server_or_client;
	private static File fileToUse;
	private static Text fileInUse;
	private static VBox initPane;
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Zip Transfer");
		primaryStage.setResizable(false);
		root = new BorderPane();
		
		
		fileInUse = new Text("File in use: None");
		
		initPane = new VBox();
		initPane.setPadding(new Insets(10, 10, 10, 10));
		initPane.setSpacing(10);
		initPane.setAlignment(Pos.CENTER);
		
		
		Button host = new Button("Host File Transfer");
		Button recieve = new Button("Recieve A File");
		Button create = new Button("Create .zip To Use");
		
		host.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				root.setCenter(new ServerPromptPane());
			}});
		
		recieve.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				root.setCenter(new ClientPromptPane());
			}});
		
		create.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				root.setCenter(new FilePickPrompt());
			}
			
		});
		
		host.setMaxWidth(Double.MAX_VALUE);
		recieve.setMaxWidth(Double.MAX_VALUE);
		create.setMaxWidth(Double.MAX_VALUE);
		
		initPane.getChildren().addAll(host, recieve, create);
		root.setCenter(initPane);
		root.setTop(fileInUse);
		scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void initAsServer(int port){
		server_or_client = new Server(port);
	}
	
	public static StackPane getView(){
		return server_or_client.getView();
	}
	
	public static boolean initAsClient(String hostName, int port){
		try {
			server_or_client = new Client(hostName, port);
		} catch (UnknownHostException e) {
			System.out.println("CLIENT - Can't access host");
			return false;
		}
		return true;
	}
	
	public static void setFileToUse(File f){
		fileToUse = f;
	}
	
	public static void updateText() throws IOException {
		fileInUse.setText("File in use: " + fileToUse.getName());
	}
	
	public static void backToHome(){
		root.setCenter(initPane);
	}
	
	public static File getMainFile(){
		return fileToUse;
	}
}
