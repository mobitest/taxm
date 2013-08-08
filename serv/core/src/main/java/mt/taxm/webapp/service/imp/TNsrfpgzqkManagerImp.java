package mt.taxm.webapp.service.imp;

import java.util.HashMap;
import java.util.List;

import mt.taxm.webapp.model.TNsrfpgzqk;
import mt.taxm.webapp.model.TNsrfpgzqkId;
import mt.taxm.webapp.service.TNsrfpgzqkManager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TNsrfpgzqkManagerImp extends GenericManagerImpl<TNsrfpgzqk, TNsrfpgzqkId> implements
		TNsrfpgzqkManager {

    private GenericDao<TNsrfpgzqk,TNsrfpgzqkId> dao;

	@Autowired
    public TNsrfpgzqkManagerImp(GenericDao<TNsrfpgzqk,TNsrfpgzqkId> dao) {
        super(dao);
        this.dao = dao;
    }


	@Override
	public List<TNsrfpgzqk> findByBm(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bm", "%"+ keyword +"%");
		log.debug("param bm="+keyword);
		return  dao.findByNamedQuery("fpgzByBm", params);

	}

}
