/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.antennae.server.notifier.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.antennae.server.notifier.db.NotifierConnectionProperties;
import org.antennae.server.notifier.db.H2.H2SimpleDriverDatasourceFactory;
import org.apache.log4j.Logger;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import static org.antennae.server.notifier.config.ApplicationConfig.entityClasses;

@Configuration
public class H2Config {
	
	private static final Logger logger = Logger.getLogger( H2Config.class);

	@Bean
	public EmbeddedDatabase getDatasource(){
		
		String dbName = "notifier";
		String dbUser = "sa";
		String dbPassword = "";
		String dbUrl = "jdbc:h2:file:~/.notifier/" + dbName + ";AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER_PORT=9090";
				
		// check whether the DB is already created.
		String dbPath = System.getProperty("user.home");
		File dbFile = new File( dbPath + File.separator + ".notifier" + File.separator + dbName + ".mv.db");

		boolean isDbCreated = false;
		if( dbFile.exists() == true && dbFile.isFile() ==true ){
			isDbCreated = true;
		}
		
		// create new DB only if it doesn't exist
		if( isDbCreated == true ){
			dbUrl = dbUrl + ";IFEXISTS=TRUE";
		}
		
		H2SimpleDriverDatasourceFactory dataSourceFactory = new H2SimpleDriverDatasourceFactory();
		
		NotifierConnectionProperties connectionProperties = new NotifierConnectionProperties();
		connectionProperties.setUrl( dbUrl);
		connectionProperties.setDriverClass(org.h2.Driver.class);
		connectionProperties.setUsername( dbUser);
		connectionProperties.setPassword(dbPassword);
		
		org.h2.Driver driver = new org.h2.Driver();
		SimpleDriverDataSource datasource = new SimpleDriverDataSource( driver, dbUrl, dbUser, dbPassword );
		
		dataSourceFactory.setConnectionProperties(connectionProperties);
		dataSourceFactory.setDataSource(datasource);

		try {
			Connection h2connection = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		
		builder.setType(EmbeddedDatabaseType.H2);
		builder.setDataSourceFactory( dataSourceFactory);
		builder.setName(dbName);
		
		// the current conf always recreates the DB, so create tables during startup
		
		if( isDbCreated == false ){
			logger.info("Creating the database");
//			builder.addScript("/db/h2/create-db.sql");
//			builder.addScript("/db/h2/insert-data.sql");
		}else{
			logger.info("Database found");
			generateH2Schema( datasource);
		}
		
		EmbeddedDatabase database = builder.build();
		
		return database;
	}

	public void generateH2Schema(SimpleDriverDataSource dataSource){
		//create a minimal configuration
		org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		cfg.setProperty("hibernate.hbm2ddl.auto", "create");

		// create a temporary file to write the DDL
		File ddlFile = null;
		try {
			//File currentDir = Paths.get(".").getFileName().toFile();
			File dir = getDirectoryFromClasspath();
			ddlFile = File.createTempFile("H2_", ".SQL", dir);
			ddlFile.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(Class<?> c : entityClasses ){
			cfg.addAnnotatedClass(c);
		}

		//build all the mappings, before calling the AuditConfiguration
		cfg.buildMappings();
		cfg.getProperties().setProperty(AvailableSettings.HBM2DDL_IMPORT_FILES, ddlFile.getName());

//		<!-- Database connection settings -->
//		<property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
//		<property name="hibernate.connection.url">jdbc:mysql://192.168.1.102:3306/javapapers</property>
//		<property name="hibernate.connection.username">root</property>
//		<property name="hibernate.connection.password">root</property>
		cfg.getProperties().setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		cfg.getProperties().setProperty("hibernate.connection.url", dataSource.getUrl());
		cfg.getProperties().setProperty("hibernate.connection.username", dataSource.getUsername());
		cfg.getProperties().setProperty("hibernate.connection.password", dataSource.getPassword());

		//configure Envers
		//AuditConfiguration.getFor(cfg);

		//execute the export
		SchemaExport export = new SchemaExport(cfg);

		//export.setOutputFile(fileName);

		export.setDelimiter(";");
		export.setFormat(true);
		export.create(true, true);

		//export.execute(true, false, false, true);

	}

	private File getDirectoryFromClasspath(){

		File result=null;
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader)cl).getURLs();

		for( URL url : urls ){
			File f = new File( url.getFile());

			if(f.isDirectory() && f.canWrite() ){
				result = f;
				break;
			}
		}

		return result;
	}
	
}
