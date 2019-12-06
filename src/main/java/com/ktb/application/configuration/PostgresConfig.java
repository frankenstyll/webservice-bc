package com.ktb.application.configuration;

import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@ComponentScan(basePackages = "com.ktb")
public class PostgresConfig {

//	@Bean
//	public BasicDataSource dataSource() throws URISyntaxException {
//		
//		String username = "kpchliqrvskkyg";
//		String password = "0e30bfa5a215ef71e27bac32fd70927c9504c1502b76690271a239cdd7f230f6";
////		String currentSchema = "LINECARE";
//		String dbUrl = "jdbc:postgres://kpchliqrvskkyg:0e30bfa5a215ef71e27bac32fd70927c9504c1502b76690271a239cdd7f230f6@ec2-174-129-255-21.compute-1.amazonaws.com:5432/d791ucd5o92run";
//
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setUrl(dbUrl);
//		basicDataSource.setUsername(username);
//		basicDataSource.setPassword(password);
//		basicDataSource.setDefaultAutoCommit(false);
//
//		return basicDataSource;
//	}

}
