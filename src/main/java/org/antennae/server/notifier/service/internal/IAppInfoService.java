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

package org.antennae.server.notifier.service.internal;

import org.antennae.server.notifier.entities.AppInfo;

public interface IAppInfoService {
	
	public void addAppInfo( AppInfo appInfo);
	public AppInfo getAppInfo( int id);
	public void updateAppInfo( AppInfo appInfo );
	public void deleteAppInfo( int id );
	
}
