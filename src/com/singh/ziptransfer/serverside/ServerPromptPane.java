package com.singh.ziptransfer.serverside;

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

public class ServerPromptPane extends VBox {

	private int port;
	private Button cancel;
	private Button accept;
	private Text explain;
	private TextField portField;
	private StackPane loadingView;
	
	public ServerPromptPane(){
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setSpacing(30);
		explain = new Text("Select a port for the server to be hosted on: ");
		portField = new TextField();
		accept = new Button("Accept Port");
		accept.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				if(verifyPortField()){
					portField.setEditable(false);
					MainDriver.initAsServer(Integer.parseInt(portField.getText()));
					loadingView = MainDriver.getView();
					addLoadingView();
				}
			}
			
		});
		cancel = new Button("Cancel");
		HBox line0 = new HBox();
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		VBox line3 = new VBox();
		line0.getChildren().add(cancel);
		line1.getChildren().addAll(explain, portField);
		line2.getChildren().add(accept);
		line3.getChildren().addAll(line1, line2);
		this.getChildren().addAll(line0, line3);
	}
	
	private void addLoadingView(){
		this.getChildren().add(loadingView);
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
	
}
