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

package org.antennae.server.notifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NotifierDashboardApplication.class)
@WebAppConfiguration
public class NotifierDashboardApplicationTests {

	@Test
	public void contextLoads() {
	}

	/*
	 {"appInfo":{"appId":"org.antennae.gcmtests.gcmtest","gcmRegistrationId":"APA91bHBvSwUi8zgoJidZSHXl5GX8j5MUHzFKWatTfjolW7GcEiJgTa0FrULlX2O4HReOhBXVS9F215Q9xhLwXYo2vfZrdNHYfWLPwOSWM9r8UkpxBFyrNvg0eZSG534wOrPqb778jXnb3uIX-Gelk9jtQ90kphtdQ","appVersion":1,"firstInstallTime":1437543423952,"lastUpdateTime":1437543423952},"deviceInfo":{"deviceId":"000000000000000","networkCountryIso":"","networkOperatorId":"","networkOperatorName":""}}
	 */
}
