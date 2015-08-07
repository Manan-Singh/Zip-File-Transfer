package com.singh.ziptransfer.clientside;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.singh.ziptransfer.core.MainDriver;

public class ClientPromptPane extends VBox {
	
	private Text portPrompt;
	private Text hostPrompt;
	private TextField portField;
	private TextField hostField;
	private Button cancel, accept;
	private StackPane loadingView;
	
	public ClientPromptPane(){
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setSpacing(10);
		cancel = new Button("Cancel");
		accept = new Button("Accept");
		accept.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				if(verifyPortField()){
					if(MainDriver.initAsClient(hostField.getText(), Integer.parseInt(portField.getText()))){
						loadingView = MainDriver.getView();
						addLoadingView();
					}
				}
			}
			
		});
		portPrompt = new Text("Select what port on the server you would like to connect to: ");
		hostPrompt = new Text("What is the host name server: ");
		portField = new TextField();
		hostField = new TextField();
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		HBox line3 = new HBox();
		HBox line4 = new HBox();
		line1.getChildren().add(cancel);
		line2.getChildren().addAll(portPrompt, portField);
		line3.getChildren().addAll(hostPrompt, hostField);
		line4.getChildren().add(accept);
		this.getChildren().addAll(line1, line2, line3, line4);
	}
	
	private boolean verifyPortField(){
		int x;
		if(portField.getText() != ""){
			try{
				x = Integer.parseInt(portField.getText());
				return true;
			}catch(NumberFormatException e){
				//Error handling
				System.out.println("Please enter a valid, unused port on your computer.");
				return false;
			}
		}else{
			return false;
		}
	}
	
	private void addLoadingView(){
		this.getChildren().add(loadingView);
	}
}
