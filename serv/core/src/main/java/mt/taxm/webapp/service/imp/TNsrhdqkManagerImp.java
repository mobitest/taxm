package mt.taxm.webapp.service.imp;

import java.util.HashMap;
import java.util.List;

import mt.taxm.webapp.model.TNsrhdqk;
import mt.taxm.webapp.model.TNsrhdqkId;
import mt.taxm.webapp.service.TNsrhdqkManager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TNsrhdqkManagerImp extends GenericManagerImpl<TNsrhdqk, TNsrhdqkId> implements
		TNsrhdqkManager {

    private GenericDao<TNsrhdqk,TNsrhdqkId> dao;

	@Autowired
    public TNsrhdqkManagerImp(GenericDao<TNsrhdqk,TNsrhdqkId> dao) {
        super(dao);
        this.dao = dao;
    }


	@Override
	public List<TNsrhdqk> findByBm(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bm", "%"+ keyword +"%");
		log.debug("param bm="+keyword);
		return  dao.findByNamedQuery("hdByBm", params);

	}

}
