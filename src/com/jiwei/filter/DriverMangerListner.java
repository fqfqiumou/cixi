package com.jiwei.filter;

import java.sql.DriverManager; 
import java.sql.SQLException;
import java.util.Enumeration;
import java.sql.Driver;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class DriverMangerListner implements ServletContextListener {
	private final static Logger logger = Logger.getLogger(DriverMangerListner.class);

	public void contextInitialized(ServletContextEvent sce) {

	}

	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("[DriverMangerListner]:-------DriverManager deregisterDriver start...");
		Enumeration<Driver> enumeration = DriverManager.getDrivers();
		while (enumeration.hasMoreElements()) {
			try {
				DriverManager.deregisterDriver(enumeration.nextElement());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("[DriverMangerListner]:-------DriverManager deregisterDriver end...");
	}
}
