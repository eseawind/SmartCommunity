package com.smartcommunity.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;


import org.apache.struts2.ServletActionContext;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;












import antlr.collections.List;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
import com.mysql.jdbc.Util;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

public class AndroidUpdateAction extends BaseActionSupport<AndroidUpdteParams> {
	
	 private String    contentType;
	 private String filename;
	 private Integer contentLength;
	
	public String checkUpdate() {
		String version = parameters.getVersion();
		try {
			getLatestVersion();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 获取当前版本 */
	public String getLatestVersion() throws DocumentException {
		  String realpath = org.apache.struts2.ServletActionContext.
					getServletContext().getRealPath(UTIL.ANDROID_VERSION_PATH);

		 SAXReader sr = new SAXReader();
		  Document doc = sr.read(realpath);
		  
		  Element root = doc.getRootElement();
		 java.util.List<Element> elements=  root.elements();
		 // Attribute attribute = root.elementByID(UTIL.ANDROID_VERSION_VERSION).attribute(UTIL.ANDROID_VERSION_VALUE);
		 // System.out.println(attribute);
		 String latestVersion = null;
		 java.util.List<String> features = new java.util.ArrayList<>();
		  for (Element element : elements) {
			  if (element.getName().equals(UTIL.ANDROID_VERSION_VERSION)) {
				
			  Attribute attribute = element.attribute(UTIL.ANDROID_VERSION_VALUE);

				  latestVersion = attribute.getValue();
			  
			  }
			  if (element.getName().equals("feature")) {
					
				  Attribute attribute = element.attribute(UTIL.ANDROID_VERSION_VALUE);

				  features.add(attribute.getValue());
				  }
		  }
		  
		  JSONObject jsonObject = null;
		  if (latestVersion == null) {
			   jsonObject = JSONUtil.getJsonObject(false);
			  JSONUtil.putCause(jsonObject, "没有要查询的版本号");
		  } else {
			  jsonObject = JSONUtil.getJsonObject(true);
			  jsonObject.put(UTIL.ANDROID_VERSION_VERSION, latestVersion);
			  jsonObject.put("features", features);
		  }
		inputStream = InputStreamUtil.getInputStream(jsonObject);

		return SUCCESS;
	}
	 public void writeVersion()
	 {
		  Document doc = DocumentHelper.createDocument();
		  doc.addProcessingInstruction("xml-stylesheet", "type='text/xsl href='students.xsl'"); 
		  Element root = doc.addElement("update");
		  root.addElement("version").addAttribute("value", "1-1-1");
		  String realpath = org.apache.struts2.ServletActionContext.
					getServletContext().getRealPath("./apk/update.xml");
			System.out.println(realpath);
		  try {   
			  OutputFormat format = new OutputFormat("\t", true);
			  format.setEncoding("utf-8");   
			  // 可以把System.out改为你要的流。
			  XMLWriter xmlWriter = new XMLWriter(new PrintWriter(new PrintStream(new File(realpath))), format);   
			  xmlWriter.write(doc);  
			  xmlWriter.close();  
			  } catch (IOException e) {
				  e.printStackTrace(); 
				  } 
		 
	 }
	 /** 下载 最新版本的 android 客户端 */
	 public String download() throws Exception {
	
		 String downloadPath = UTIL.ANDROID_DOWNLOAD_PATH;
		  filename= downloadPath.substring(downloadPath.lastIndexOf('/') +1); //保存文件时的名称
		     contentType="application/octet-stream";//强制下载
		     String realPath = ServletActionContext.getServletContext().getRealPath(downloadPath);
		     java.io.File file = new java.io.File(realPath);
		     contentLength = (int) file.length();
		     super.setInputStream(ServletActionContext.getServletContext().getResourceAsStream(downloadPath));
		     return SUCCESS;
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
	public Integer getContentLength() {
		return contentLength;
	}
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}

}
