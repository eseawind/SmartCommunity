package com.smartcommunity.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

//import org.apache.naming.java.javaURLContextFactory;

import net.sf.json.JSONObject;
import antlr.collections.List;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UploadAction extends ActionSupport {

	private java.util.List<File> image;
	private java.util.List<String> imageFileName;
	private java.util.List<String> imageContentType;
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public java.util.List<File> getImage() {
		return image;
	}

	public void setImage(java.util.List<File> image) {
		this.image = image;
	}

	public java.util.List<String> getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(java.util.List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}

	public java.util.List<String> getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(java.util.List<String> imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String execute() throws Exception {

		JSONObject jsonObject = new JSONObject();
		String realPath = org.apache.struts2.ServletActionContext
				.getServletContext().getRealPath("/upload/images");

		if (image != null) {
			System.out.println("realpath: " + realPath);
			for (int i = 0; i < image.size(); i++) {
				File saveFile = new File(new File(realPath),
						imageFileName.get(i));
				if (!saveFile.getParentFile().exists())
					saveFile.getParentFile().mkdirs();
				org.apache.commons.io.FileUtils
						.copyFile(image.get(i), saveFile);
			}

			// com.opensymphony.xwork2.ActionContext.getContext().put("message",
			// "文件上传成功");

			jsonObject.put("success", true);

		} else {
			System.out.println("realpath: " + null);
			jsonObject.put("success", false);
		}
		inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes(
				"utf-8"));
		return SUCCESS;
	}

}