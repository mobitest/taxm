package mt.taxm.webapp.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.GenericDao;
import org.appfuse.service.GenericManager;
import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import mt.taxm.model.AppVersion;
import mt.taxm.webapp.model.TNsrfpgzqk;
import mt.taxm.webapp.model.TNsrfpgzqkId;
import mt.taxm.webapp.service.AppVersionManager;

public class AppVersionManagerImp extends GenericManagerImpl<AppVersion,Long> implements AppVersionManager {
	private static final Log log = LogFactory.getLog(AppVersionManagerImp.class);
	String TAG = this.getClass().getName();
	
	  private GenericDao<AppVersion,Long> dao;

		@Autowired
	    public AppVersionManagerImp(GenericDao<AppVersion,Long> dao) {
	        super(dao);
	        this.dao = dao;
	    }

	@Override
	public AppVersion anymore(int curVersion) {
		
		List<AppVersion> a= dao.findByNamedQuery("lastVersion", new HashMap<String, Object>());
		if(a==null || a.size()==0) return null;
//		AppVersion av = new AppVersion();
//		av.setId(3L);
//		av.setNum(3L);
//		av.setReleaseDate(new Date());
//		av.setTitle("Greate version");
//		av.setMustUpdate(1L);
//		String releaseNotes = "神晚上制作的版本";
//		av.setReleaseNotes(releaseNotes );
//		String url ="taxm4a.apk";
//		av.setUrl(url);
		// TODO Auto-generated method stub
		log.debug("cur version:"+ curVersion + "; last version:"+ a.get(0).getNum());
		
//		if(curVersion>=av.getNum()) {
//			log.debug("already last version!");
//			av = new AppVersion();
//			av.setNum(-1);
//			av.setReleaseNotes("new version is around the corner...");
//			return av;
//		}
		return a.get(0);
	}

}
