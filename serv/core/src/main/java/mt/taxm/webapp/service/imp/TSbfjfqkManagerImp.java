package mt.taxm.webapp.service.imp;

import java.util.HashMap;
import java.util.List;

import mt.taxm.webapp.model.TSbfjfqk;
import mt.taxm.webapp.model.TSbfjfqkId;
import mt.taxm.webapp.service.TSbfjfqkManager;

import org.appfuse.dao.GenericDao;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TSbfjfqkManagerImp extends GenericManagerImpl<TSbfjfqk, TSbfjfqkId> implements
		TSbfjfqkManager {

    private GenericDao<TSbfjfqk,TSbfjfqkId> dao;

	@Autowired
    public TSbfjfqkManagerImp(GenericDao<TSbfjfqk,TSbfjfqkId> dao) {
        super(dao);
        this.dao = dao;
    }


	@Override
	public List<TSbfjfqk> findByBm(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bm", "%"+ keyword +"%");
		log.debug("param bm="+keyword);
		return  dao.findByNamedQuery("sbfjfByBm", params);

	}

}
