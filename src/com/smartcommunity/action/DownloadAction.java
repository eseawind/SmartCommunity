package com.smartcommunity.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadAction extends ActionSupport {

	 private String    inputPath;
	 private String    contentType;
	 private String filename;
	 private Integer contentLength;
	 private InputStream inputStream;
	 public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getInputPath() {
		return inputPath;
	}
	 
	 
	public Integer getContentLength() {
		return contentLength;
	}


	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}


	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getInputStream() throws Exception {
        return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
  }

	 public String execute() throws Exception {
		  inputPath="/upload/images/toucan.png";//要下载的文件名称

		  filename= inputPath.substring(inputPath.lastIndexOf('/') +1); //保存文件时的名称
		     contentType="application/octet-stream";//强制下载
		     String realPath = ServletActionContext.getServletContext().getRealPath(inputPath);
		     java.io.File file = new java.io.File(realPath);
		     contentLength = (int) file.length();

		     HttpServletRequest httpServletRequest= ServletActionContext.getRequest();

		     System.out.println(getInputStream() );
		     return SUCCESS;
		 }
	 
	 public String downloadWithBreakPoint() {

		return null;
	 }
}
