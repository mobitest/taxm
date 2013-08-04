package com.mobitest.service.imp;

import java.util.HashMap;
import java.util.List;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobitest.model.AppUser;
import com.mobitest.service.AppUserManager;

public class AppUserManagerImp extends GenericManagerImpl<AppUser, Integer> implements AppUserManager {

    private GenericDao<AppUser,Integer> appUserDao;

	@Autowired
    public AppUserManagerImp(GenericDao<AppUser, Integer> appUserDao) {
        super(appUserDao);
        this.appUserDao = appUserDao;
    }

	@Override
	public List<AppUser> findByEmail(String email) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("email", "%"+ email +"%");
		System.out.println("param email="+email);
		return  appUserDao.findByNamedQuery("findByEmail", params);
	}

}
