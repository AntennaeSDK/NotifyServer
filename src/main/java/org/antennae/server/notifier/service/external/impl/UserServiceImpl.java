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

package org.antennae.server.notifier.service.external.impl;

import java.util.List;

import javax.inject.Inject;

import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.service.external.IUserService;
import org.antennae.server.notifier.service.internal.IUserInternalService;
import org.springframework.stereotype.Service;

@Service(value="UserServiceImpl")
public class UserServiceImpl implements IUserService {
	
	@Inject
	private IUserInternalService userInternalSvc;

	@Override
	public User registerUser(User user) {
		return userInternalSvc.addUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userInternalSvc.getAllUsers();
	}
	
	public User getUser( int id ){
		return userInternalSvc.getUser(id);
	}
	
	public User getUser( String userId ){
		return userInternalSvc.getUserByUserId(userId );
	}

	@Override
	public List<User> getUsers(List<Integer> userIds) {
		return userInternalSvc.getUsers( userIds );
	}

	@Override
	public List<User> getUsersForLoginIds(List<String> loginIds) {
		return userInternalSvc.getUsersForLoginIds(loginIds);
	}

	@Override
	public User getUserByLoginId(String id) {
		return userInternalSvc.getUserByLoginId(id);
	}
}
