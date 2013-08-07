package com.mobitest.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.appfuse.dao.BaseDaoTestCase;
import org.appfuse.dao.GenericDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobitest.model.AppUser;

public class TestUserDao extends BaseDaoTestCase {
	private GenericDao<AppUser, Integer> appUserDao;

	@Autowired
	public void setAppUserDao(GenericDao<AppUser, Integer> appUserDao) {
		this.appUserDao = appUserDao;
	}
	
	@Test
	public void testFind(){
		List<AppUser> list;
		HashMap<String, Object> params = new HashMap<String, Object>();
		String email = "123";
		params.put("email", "%"+ email +"%");
		list = appUserDao.findByNamedQuery("findByEmail", params);
		for(AppUser user: list){
			System.out.println("email:"+ user.getEmail() );
			assertTrue(user.getEmail().indexOf(email)>-1);
		}
		assertTrue(list.size()>0);

		list = appUserDao.getAll();
		for(AppUser user: list){
			System.out.println("email-"+ user.getEmail() );
		}
		
	}
}
