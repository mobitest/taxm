package mt.taxm.webapp.service.imp;

import java.util.HashMap;
import java.util.List;

import mt.taxm.webapp.model.TNsrjbxx;
import mt.taxm.webapp.service.TNsrjbxxManager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TNsrjbxxManagerImp extends GenericManagerImpl<TNsrjbxx, Long> implements
		TNsrjbxxManager {

    private GenericDao<TNsrjbxx,Long> dao;

	@Autowired
    public TNsrjbxxManagerImp(GenericDao<TNsrjbxx,Long> dao) {
        super(dao);
        this.dao = dao;
    }


	@Override
	public List<TNsrjbxx> findByKeyword(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("keyword", "%"+ keyword +"%");
		log.debug("param bm="+keyword);
		return  dao.findByNamedQuery("jbxxByKeyword", params);

	}

}
