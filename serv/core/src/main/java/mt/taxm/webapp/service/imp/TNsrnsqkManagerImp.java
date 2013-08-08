package mt.taxm.webapp.service.imp;

import java.util.HashMap;
import java.util.List;

import mt.taxm.webapp.model.TNsrnsqk;
import mt.taxm.webapp.model.TNsrnsqkId;
import mt.taxm.webapp.service.TNsrnsqkManager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TNsrnsqkManagerImp extends GenericManagerImpl<TNsrnsqk, TNsrnsqkId> implements
		TNsrnsqkManager {

    private GenericDao<TNsrnsqk,TNsrnsqkId> dao;

	@Autowired
    public TNsrnsqkManagerImp(GenericDao<TNsrnsqk,TNsrnsqkId> dao) {
        super(dao);
        this.dao = dao;
    }


	@Override
	public List<TNsrnsqk> findByBm(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bm", "%"+ keyword +"%");
		log.debug("param bm="+keyword);
		return  dao.findByNamedQuery("nsByBm", params);

	}

}
