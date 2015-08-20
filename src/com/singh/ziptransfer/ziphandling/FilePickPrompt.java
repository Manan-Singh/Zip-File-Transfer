package com.singh.ziptransfer.ziphandling;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.singh.ziptransfer.core.ErrorStage;
import com.singh.ziptransfer.core.MainDriver;

public class FilePickPrompt extends VBox {

	private VBox buttonContainer;
	private Button back, createFile, useFile;
	private FileChooser fc;
	
	public FilePickPrompt(){
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setSpacing(10);
		fc = new FileChooser();
		buttonContainer = new VBox();
		buttonContainer.setPadding(new Insets(10, 10, 10, 10));
		buttonContainer.setSpacing(10);
		back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				MainDriver.backToHome();
			}});
		createFile = new Button("Create a new zip file to use");
		createFile.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				fc.setTitle("Select files to add to your zip file!");
				List<File> files =  fc.showOpenMultipleDialog(new Stage());
				if(files != null && !files.isEmpty()){
					removeBack();
					ZipCreatorPane zipPane = new ZipCreatorPane();
					zipPane.setList(files);
					addZipPane(zipPane);
				}
//				else{
//					System.out.println("No files were selected.");
//				}
			}
			
		});
		useFile = new Button("Use an existing zip file");
		useFile.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				fc.setTitle("Select a zip file to use!");
				File selectedFile = fc.showOpenDialog(new Stage());
				if(selectedFile != null){
					String name = selectedFile.getName();
					if(name.substring(name.indexOf(".") + 1).equals("zip")){
						MainDriver.setFileToUse(selectedFile);
						try {
							MainDriver.updateText();
						} catch (IOException e1) {
							new ErrorStage("An I/O error ocurred!");
						}
						MainDriver.backToHome();
					}
				}
//				else{
//					new ErrorStage("Please select a file!");
//				}
			}
			
		});
		createFile.setMaxWidth(Double.MAX_VALUE);
		useFile.setMaxWidth(Double.MAX_VALUE);
		buttonContainer.getChildren().addAll(createFile, useFile);
		this.getChildren().addAll(back, buttonContainer);
	}
	
	public void addZipPane(ZipCreatorPane zcp){
		this.getChildren().add(zcp);
	}
	
	private void removeBack(){
		this.getChildren().remove(back);
		buttonContainer.getChildren().remove(createFile);
	}
}
