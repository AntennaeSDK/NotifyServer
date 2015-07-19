package org.antennae.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class H2Config {

	@Bean
	public DataSource getDatasource(){
		
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		
		builder.setType(EmbeddedDatabaseType.H2);
		builder.addScript("/db/h2/h2-create-db.sql");
		builder.addScript("/db/h2/h2-insert-data.sql");
		
		EmbeddedDatabase database = builder.build();
		
		return database;
	}
}
