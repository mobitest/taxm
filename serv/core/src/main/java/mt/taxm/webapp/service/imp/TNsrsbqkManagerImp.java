package mt.taxm.webapp.service.imp;

import java.util.HashMap;
import java.util.List;

import mt.taxm.webapp.model.TNsrsbqk;
import mt.taxm.webapp.model.TNsrsbqkId;
import mt.taxm.webapp.service.TNsrsbqkManager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TNsrsbqkManagerImp extends GenericManagerImpl<TNsrsbqk, TNsrsbqkId> implements
		TNsrsbqkManager {

    private GenericDao<TNsrsbqk,TNsrsbqkId> dao;

	@Autowired
    public TNsrsbqkManagerImp(GenericDao<TNsrsbqk,TNsrsbqkId> dao) {
        super(dao);
        this.dao = dao;
    }


	@Override
	public List<TNsrsbqk> findByBm(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bm", "%"+ keyword +"%");
		log.debug("param bm="+keyword);
		return  dao.findByNamedQuery("sbByBm", params);

	}

}
