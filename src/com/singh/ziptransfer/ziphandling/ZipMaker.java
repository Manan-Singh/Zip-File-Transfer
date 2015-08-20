package com.singh.ziptransfer.ziphandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import com.singh.ziptransfer.core.ErrorStage;

public class ZipMaker {

	private File zipFile;
	private List<File> files;
	
	public ZipMaker(File zipFile, List<File> files){
		this.zipFile = zipFile;
		this.files = files;
	}
	
	public File makeZip(){
		ZipOutputStream zipOut = null;
		InputStream inputStream = null;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			ZipParameters params = new ZipParameters();
			params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			params.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			params.setEncryptFiles(false);
			for(int i = 0; i < files.size(); i++){
				zipOut.putNextEntry(files.get(i), params);
				if(files.get(i).isDirectory()){
					continue;
				}
				inputStream = new FileInputStream(files.get(i));
				byte[] buffer = new byte[4096];
				int count = 0;
				while((count = inputStream.read(buffer)) > -1){
					zipOut.write(buffer, 0, count);
				}
				zipOut.closeEntry();
				inputStream.close();
			}
			zipOut.finish();
		} catch (FileNotFoundException e) {
			new ErrorStage("Error 404 - File not found!");
		} catch (ZipException e) {
			new ErrorStage("Something went wrong when trying to make the zip file!");
		} catch (IOException e) {
			new ErrorStage("Something went wrong when trying to read the files");
		} finally {
			if(zipOut != null){
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return zipFile;
	}
}
