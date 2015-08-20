package com.singh.ziptransfer.ziphandling;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.singh.ziptransfer.core.MainDriver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ZipCreatorPane extends VBox {
	
	private Text dirName;
	private TextField nameField;
	private DirectoryChooser dc;
	private Button dirButton;
	private ListView<File> filesToAdd;
	private ObservableList<File> theFiles;
	private Button makeAndUse;
	
	
	public ZipCreatorPane(){
		this.setSpacing(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		//dirName = new Text("Input a name for the directory\nyou want the zip file to be created under: ");
		//dirField = new TextField();
		dc = new DirectoryChooser();
		dirButton = new Button("Choose directory for new zip file");
		nameField = new TextField("NewZipFile.zip");
		nameField.setPromptText("Name of new zip file goes here");
		dirButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				if(verifyFileName()){
					dc.setTitle("Choose a directory for you zip file to be saved in!");
					File dirFile = dc.showDialog(new Stage());
					if(dirFile != null){
						File zipFile = new File(dirFile.getAbsolutePath() + "\\" +  nameField.getText());
						if(!zipFile.exists()){
							ZipMaker zm = new ZipMaker(zipFile, filesToAdd.getItems());
							MainDriver.setFileToUse(zm.makeZip());
							try {
								MainDriver.updateText();
							} catch (IOException e1) {
								System.out.println("Text couldn't be properly updated!");
								e1.printStackTrace();
							}
							MainDriver.backToHome();
						}else{
							System.out.println("A file with the selected name already exists!");
						}
					}else{
						System.out.println("Choose a directory!");
					}
				}else{
					System.out.println("Choose a valid name for your zip file!");
				}
			}
			
		});
		VBox dirBox = new VBox();
		//makeAndUse = new Button("Make and use");
		dirBox.getChildren().addAll(nameField, dirButton);
		this.getChildren().add(dirBox);
		
	}
	
	private boolean verifyFileName(){
		String fileName = nameField.getText();
		if(fileName.matches(".+\\.zip")){
			return true;
		}else{
			return false;
		}
	}
	
	public void setList(List<File> list){
		theFiles = FXCollections.observableList(list);
		filesToAdd = new ListView<File>(theFiles);
		Text filesToBeAdded = new Text("Files to be added into zip file: ");
		this.getChildren().add(filesToBeAdded);
		this.getChildren().add(filesToAdd);
	}
}
