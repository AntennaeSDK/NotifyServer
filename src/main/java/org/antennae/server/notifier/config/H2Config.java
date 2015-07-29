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

import javax.sql.DataSource;

import org.antennae.server.notifier.db.NotifierConnectionProperties;
import org.antennae.server.notifier.db.H2.H2SimpleDriverDatasourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class H2Config {

	@Bean
	public DataSource getDatasource(){
		
		String dbName = "notifier";
		String dbUrl = "jdbc:h2:file:~/.notifier/" + dbName ;
		String dbUser = "sa";
		String dbPassword = "";
				
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
		
		// check whether the DB is already created.
		String dbPath = System.getProperty("user.home");
		File dbFile = new File( dbPath + File.separator + ".notifier" + File.separator + dbName + ".mv.db");
		
		boolean isDbCreated = false;
		if( dbFile.exists() == true && dbFile.isFile() ==true ){
			isDbCreated = true;
		}
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		
		builder.setType(EmbeddedDatabaseType.H2);
		//builder.setName("notifier.db");
		builder.setDataSourceFactory( dataSourceFactory);
		if( isDbCreated == false ){
			builder.addScript("/db/h2/create-db.sql");
			builder.addScript("/db/h2/insert-data.sql");
		}
		
		EmbeddedDatabase database = builder.build();
		
		return database;
	}
}
