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

import org.antennae.server.notifier.NotifierDashboardApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * This class is scanned by a servlet 3.0 container (SCI) during startup.
 * This initializes WebSocket when deployed as a WAR in Tomcat.
 * 
 * Not used when using spring-boot ( embedded tomcat ).
 * 
 */
//public class WsServletInitializer extends SpringBootServletInitializer {
public class WsServletInitializer {

	//@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NotifierDashboardApplication.class);
	}
}
