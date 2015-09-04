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

package org.antennae.server.notifier.service.internal.impl;

import java.util.List;

import javax.inject.Inject;

import org.antennae.server.notifier.entities.User;
import org.antennae.server.notifier.entities.UserStatusEnum;
import org.antennae.server.notifier.repository.IUserDao;
import org.antennae.server.notifier.service.exception.UserIdAlreadyRegisteredException;
import org.antennae.server.notifier.service.internal.IUserInternalService;
import org.springframework.stereotype.Service;

@Service(value="UserInternalServiceImpl")
public class UserInternalServiceImpl implements IUserInternalService {
	
	@Inject
	private IUserDao userDao;

	@Override
	public User addUser(User user) {
		
		List<User> users = userDao.getUsersByLoginId(user.getUserId());
		
		if( users != null && users.size() > 0 ){
			throw new UserIdAlreadyRegisteredException( user.getUserId() + " is already registered");
		}
		
		userDao.addUser(user);
		return user;
	}

	@Override
	public void updateUser(User user) {
	}

	@Override
	public User getUser(int id) {
		return userDao.getUser(id);
	}

	@Override
	public void deleteUser(int id) {

	}

	@Override
	public boolean validateUserPassword(String userId, String password) {
		return userDao.validateUserPassword(userId, password);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public User getUserByUserId(String userId) {
		
		User result=null;
		List<User> users = userDao.getUsersByLoginId(userId);
		
		// only one active user, by userId
		for( User u : users ){
			if( u.getStatus() == UserStatusEnum.ACTIVE ){
				result = u;
				break;
			}
		}
		
		return result;
	}

	@Override
	public List<User> getUsers(List<Integer> userIds) {
		return userDao.getUsers(userIds);
	}

	@Override
	public List<User> getUsersForLoginIds(List<String> loginIds) {
		return userDao.getUsersForLoginIds(loginIds );
	}

	@Override
	public User getUserByLoginId(String id) {
		User user=null;
		List<User> users= userDao.getUsersByLoginId(id);
		// it should have only one active loginId
		if( users.size() > 0 ){
			for( User u : users){
				if( u.getStatus() == UserStatusEnum.ACTIVE ){
					user = u;
				}
			}
		}
		
		return user;
	}
}
