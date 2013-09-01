package mt.taxm.webapp.action;

import com.opensymphony.xwork2.Preparable;
import org.appfuse.service.GenericManager;
import org.appfuse.dao.SearchException;
import mt.taxm.model.AppVersion;
import mt.taxm.webapp.action.BaseAction;

import java.util.List;

public class AppVersionAction extends BaseAction implements Preparable {
    private GenericManager<AppVersion, Long> appVersionManager;
    private List appVersions;
    private AppVersion appVersion;
    private Long id;
    private String query;
    private Long isnew;


	public void setAppVersionManager(GenericManager<AppVersion, Long> appVersionManager) {
        this.appVersionManager = appVersionManager;
    }

    public List getAppVersions() {
        return appVersions;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    public void prepare() {
        if (getRequest().getMethod().equalsIgnoreCase("post")) {
            // prevent failures on new
            String appVersionId = getRequest().getParameter("appVersion.id");
            if (appVersionId != null && !appVersionId.equals("")) {
                appVersion = appVersionManager.get(new Long(appVersionId));
            }
        }
    }

    public void setQ(String q) {
        this.query = q;
    }

    public String list() {
        try {
            appVersions = appVersionManager.search(query, AppVersion.class);
        } catch (SearchException se) {
            addActionError(se.getMessage());
            appVersions = appVersionManager.getAll();
        }
        return SUCCESS;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getIsnew() {
		return isnew;
	}

	public void setIsnew(Long isnew) {
		this.isnew = isnew;
	}

    public AppVersion getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion;
    }

    public String delete() {
        appVersionManager.remove(appVersion.getId());
        saveMessage(getText("appVersion.deleted"));

        return SUCCESS;
    }

    public String edit() {
        if (id != null) {
            appVersion = appVersionManager.get(id);
        } else {
            appVersion = new AppVersion();
        }

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null) {
            return "cancel";
        }

        if (delete != null) {
            return delete();
        }

        boolean isNew = (appVersion.getId() == null);

        appVersionManager.save(appVersion);

        String key = (isNew) ? "appVersion.added" : "appVersion.updated";
        saveMessage(getText(key));

        if (!isNew) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }
}