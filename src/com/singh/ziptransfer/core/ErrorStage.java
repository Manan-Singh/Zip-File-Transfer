package com.singh.ziptransfer.core;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ErrorStage extends Stage {

	private Label message;
	
	public ErrorStage(String s){
		this.setResizable(false);
		message = new Label(s);
		message.setWrapText(true);
		StackPane root = new StackPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setPrefWidth(message.getWidth());
		root.setPrefHeight(100);
		root.getChildren().add(message);
		Scene scene = new Scene(root);
		this.setScene(scene);
		this.show();
	}
}
