package com.mobitest.dao;

import org.appfuse.dao.BaseDaoTestCase;
import org.appfuse.dao.GenericDao;

import com.mobitest.model.Tt;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static org.junit.Assert.*;

public class TestTtDao extends BaseDaoTestCase {
	private GenericDao<Tt, Integer> ttDao;

	@Autowired
	public void setTtDao(GenericDao<Tt, Integer> ttDao) {
		this.ttDao = ttDao;
	}

	@Test
	public void testFindAll() throws Exception {


		List<Tt> list = ttDao.getAll();
		assertTrue(list.size() > 0);
	}

	@Test
	public void testNew() throws Exception{
		Tt tt = new Tt();
		tt.setId(2);
		tt.setName("john");
		tt.setUpateDate(new java.util.Date());
		ttDao.save(tt);
		flush();
		
		Tt tt2 = ttDao.get(tt.getId());
		assertEquals("john", tt2.getName());
		
//		ttDao.remove(tt.getId());
	    flush();
	}
}