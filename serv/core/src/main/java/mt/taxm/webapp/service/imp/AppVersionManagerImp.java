package mt.taxm.webapp.service.imp;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mt.taxm.webapp.model.AppVersion;
import mt.taxm.webapp.service.AppVersionManager;

public class AppVersionManagerImp implements AppVersionManager {
	private static final Log log = LogFactory.getLog(AppVersionManagerImp.class);
	String TAG = this.getClass().getName();

	@Override
	public AppVersion anymore(int curVersion) {
		
		AppVersion av = new AppVersion();
		av.setId(3);
		av.setNum(3);
		av.setReleaseDate(new Date());
		av.setTitle("Greate version");
		av.setMustUpdate(1);
		String releaseNotes = "神晚上制作的版本";
		av.setReleaseNotes(releaseNotes );
		String url ="taxm4a.apk";
		av.setUrl(url);
		// TODO Auto-generated method stub
		log.debug("cur version:"+ curVersion + "; last version:"+ av.getNum());
		
		if(curVersion>=av.getNum()) {
			log.debug("already last version!");
			av = new AppVersion();
			av.setNum(-1);
			av.setReleaseNotes("new version is around the corner...");
			return av;
		}
		return av;
	}

}
