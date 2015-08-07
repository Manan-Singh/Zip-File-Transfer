package com.singh.ziptransfer.core;

import javafx.scene.layout.StackPane;

/**
 * Interface which basically connects both Server and Client classes
 * @author Manan Singh
 */
public interface ConnectionMode {

	/**
	 * Communicates with another connection mode object
	 */
	void communicate();
	
	/**
	 * 
	 * @return A StackPane with a visual representation of the connection mode's progress in 
	 * communicating
	 */
	StackPane getView();
	
}
