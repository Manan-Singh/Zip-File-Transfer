package com.singh.ziptransfer.ziphandling;

import java.io.File;

import com.singh.ziptransfer.core.MainDriver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FilePickPrompt extends VBox {

	private VBox buttonContainer;
	private Button back, createFile, useFile;
	private FileChooser fl;
	
	public FilePickPrompt(){
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setSpacing(10);
		fl = new FileChooser();
		buttonContainer = new VBox();
		buttonContainer.setPadding(new Insets(10, 10, 10, 10));
		buttonContainer.setSpacing(10);
		back = new Button("Back");
		createFile = new Button("Create a new zip file to use");
		useFile = new Button("Use an existing zip files");
		useFile.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				fl.setTitle("Select a zip file to use!");
				File selectedFile = fl.showOpenDialog(new Stage());
				if(selectedFile != null){
					String name = selectedFile.getName();
					if(name.substring(name.indexOf(".") + 1).equals("zip")){
						System.out.println("It worked!");
					}
				}else{
					System.out.println("Please select a file!");
				}
			}
			
		});
		createFile.setMaxWidth(Double.MAX_VALUE);
		useFile.setMaxWidth(Double.MAX_VALUE);
		buttonContainer.getChildren().addAll(createFile, useFile);
		this.getChildren().addAll(back, buttonContainer);
	}
}
