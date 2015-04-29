/**
 * 
 */
package com.smartcommunity.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.StreamResult;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * @author Administrator
 * 
 */
public class MultiStreamResult extends StreamResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {

		// 获取 http 请求与响应
		HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(HTTP_REQUEST);
		
		// 获取配置参数 
		resolveParamsFromStack(invocation.getStack(), invocation);
		
		String rangeStr = ""; // 表示字节范围的字符串
		long fromLength = 0; // 需要下载的数据的起始字节
		long toLength = 0; // 需要下载数据的最后一个字节
		long rangeLength = 0; // 需要下载数据的总字节长度
		OutputStream out = null; // 输出流

		if (inputStream == null) {
			// Find the inputstream from the invocation variable stack
			inputStream = (InputStream) invocation.getStack().findValue(
					conditionalParse(inputName, invocation));
		}
		if (inputStream == null) {
			String msg = ("Can not find a java.io.InputStream with the name ["
					+ inputName + "] in the invocation stack. " + "Check the tag specified for this action.");
			LOG.error(msg);
			throw new IllegalArgumentException(msg);
		}
		if (contentLength == null || contentLength.length() < 1) {
			throw new IllegalArgumentException("支持断点续传时，[Content-Length]不能为空.");
		}
		// Set the content type
		if (contentCharSet != null && !contentCharSet.equals("")) {
			response.setContentType(conditionalParse(contentType, invocation)
					+ ";charset=" + contentCharSet);
		} else {
			response.setContentType(conditionalParse(contentType, invocation));
		}

		// 设置下载名
		if (contentDisposition != null) {
			response.addHeader("Content-Disposition",
					conditionalParse(contentDisposition, invocation));
		}
		// Set the cache control headers if neccessary
		if (!allowCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		}
		// Set the content length
		//String _contentLength = conditionalParse(contentLength, invocation);
		// 文件长度
	//	System.out.println("len : " + contentLength);
		// 文件总长度 
		int fileLength = Integer.parseInt(contentLength);
		response.setContentLength(fileLength);
		// 需要使用断点续传下载
		if (isHeadOfRange(request)) {
			//LOG.debug("断点续传下载.");
			
			// 设置状态 HTTP/1.1 206 Partial Content
			response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
			// 表示使用了断点续传（默认是“none”，可以不指定）
			response.setHeader("Accept-Ranges", "bytes");
			// 设置Content-Range
			StringBuilder crBuf = new StringBuilder("bytes ");
			rangeStr = request.getHeader("Range").replaceAll("bytes=", "")
					.trim();
			// 指定位置到文件结束
			if (rangeStr.endsWith("-")) {
				//LOG.debug("开区间下载.");
				rangeStr = StringUtils.substringBefore(rangeStr, "-");
				// 开始下载位置
				fromLength = Long.parseLong(rangeStr);
				rangeLength = fileLength - fromLength + 1;
				crBuf.append(rangeStr).append("-").append(fromLength - 1)
						.append("/").append(fileLength);
			} else {
				//LOG.debug("闭区间下载.");
				String num1 = StringUtils.substringBefore(rangeStr, "-");
				String num2 = StringUtils.substringAfter(rangeStr, "-");
				// 开始与结束位置
				fromLength = Long.parseLong(num1);
				toLength = Long.parseLong(num2);
				rangeLength = toLength - fromLength + 1;
				crBuf.append(rangeStr).append("/").append(fileLength);
			}
			// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
			response.setHeader("Content-Range", crBuf.toString());
			// 普通下载
		} else {
			//LOG.debug("普通下载.");
			// 默认返回 HTTP/1.1 200 OK
			rangeLength = fileLength; // 客户端要求全文下载
		}
		// 输出文件操作
		try {
			out = response.getOutputStream();
			byte[] outBuff = new byte[bufferSize];
			int readLen = 0;
			// 闭区间处理
			if (toLength > 0) {
				//LOG.debug("闭区间下载开始...");
				inputStream.skip(fromLength);
				int readBufSize = (int) Math.min(bufferSize, rangeLength);
				long pFrom = fromLength;
				while (pFrom < toLength) {
					readLen = inputStream.read(outBuff, 0, readBufSize);
					pFrom += readBufSize;
					readBufSize = (int) Math.min(readBufSize, toLength - pFrom
							+ 1);
					out.write(outBuff, 0, readLen);
				}
				// 开区间处理
			} else {
				//LOG.debug("开区间下载开始...");
				inputStream.skip(fromLength);
				while ((readLen = inputStream.read(outBuff, 0, bufferSize)) != -1) {
					out.write(outBuff, 0, readLen);
				}
			}
		} catch (ClientAbortException e) {
			// 忽略（迅雷等下载工具，支持多线程下载，但有些线程会被中途取消，导致异常。）
			// LOG.debug(e.getMessage(), e);
		} catch (SocketException e) {
			// 忽略（迅雷等下载工具，支持多线程下载，但有些线程会被中途取消，导致异常。）
			// LOG.debug(e.getMessage(), e);
		} catch (Exception e) {
			// 其他异常记录日志
			LOG.error(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
			if (out != null) {
				try {
					out.flush();
				} catch (Exception e1) {
				}
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private static boolean isHeadOfRange(HttpServletRequest request) {
		return request.getHeader("Range") != null;
	}
}
