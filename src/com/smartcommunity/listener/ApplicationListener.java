package com.smartcommunity.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Application Lifecycle Listener implementation class SystemInit
 *
 */
@WebListener
public class ApplicationListener implements ServletContextListener {
	
	private static WebApplicationContext springContext;

    /**
     * Default constructor. 
     */
    public ApplicationListener() {
    	System.out.println("application start");
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

    	springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
    //	com.smartcommunity.cache.ResourceCache.getResourceMap(2);
	}

    public static ApplicationContext getApplicationContext() {
		return springContext;
	}
	
}
