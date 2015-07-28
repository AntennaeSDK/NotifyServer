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

package org.antennae.server.notifier.db;

import java.sql.Driver;

import org.springframework.jdbc.datasource.embedded.ConnectionProperties;

public class NotifierConnectionProperties implements ConnectionProperties{

	private Class<? extends Driver> driverClass;
	private String url;
	private String username;
	private String password;
	
	public Class<? extends Driver> getDriverClass() {
		return driverClass;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public void setDriverClass(Class<? extends Driver> driverClass) {
		this.driverClass = driverClass;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

}
