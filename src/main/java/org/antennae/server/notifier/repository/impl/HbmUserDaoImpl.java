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

package org.antennae.server.notifier.repository.impl;

import java.util.List;

import javax.inject.Inject;

import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.repository.IUserDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HbmUserDaoImpl implements IUserDao {

	@Inject
	private SessionFactory sessionFactory;

	@Transactional
	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public User getUser(int id) {
		
		User result=null;
		Query query = sessionFactory.getCurrentSession().createQuery("from User where id = :id");
		query.setInteger("id", id);
		
		List<User> users = query.list();
		
		if( users != null && users.size() == 1 ){
			result = users.get(0);
		}
		
		return result;
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public boolean validateUserPassword(String userId, String password) {
		
		boolean result = false;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from User where userId = :userId and password= :password");
		query.setString("userId", userId);
		query.setString("password", password);
		List<User> users = query.list();
		
		if( users.size() == 1){
			result = true;
		}
		
		return result;
	}

	@Override
	@Transactional
	public List<User> getUsersByLoginId(String userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where userId = :userId");
		query.setString("userId", userId);
		
		List<User> users = query.list();
		return users;
	}

	@Override
	@Transactional
	public List<User> getAllUsers() {
		Query query = sessionFactory.getCurrentSession().createQuery("from User");
		return (List<User>) query.list();
	}

	@Override
	@Transactional
	public List<User> getUsers(List<Integer> userIds) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where id in ( :ID )");
		query.setParameterList("ID", userIds);
		
		List<User> users = query.list();
		return users;
	}

	@Override
	@Transactional
	public List<User> getUsersForLoginIds(List<String> loginIds) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where userId in ( :ID )");
		query.setParameterList("ID", loginIds);
		
		List<User> users = query.list();
		return users;
	}

	

}
