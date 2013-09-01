package mt.taxm.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.appfuse.service.GenericManager;
import mt.taxm.model.AppVersion;
import mt.taxm.webapp.action.BaseActionTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppVersionActionTest extends BaseActionTestCase {
    private AppVersionAction action;

    @Before
    public void onSetUp() {
        super.onSetUp();

        action = new AppVersionAction();
        GenericManager appVersionManager = (GenericManager) applicationContext.getBean("appVersionManager");
        action.setAppVersionManager(appVersionManager);

        // add a test appVersion to the database
        AppVersion appVersion = new AppVersion();

        // enter all required fields
        appVersion.setNum(1L);

        appVersionManager.save(appVersion);
    }

    @Test
    public void testGetAllAppVersions() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getAppVersions().size() >= 1);
    }

    @Test
    public void testSearch() throws Exception {
        // regenerate indexes
        GenericManager<AppVersion, Long> appVersionManager = (GenericManager<AppVersion, Long>) applicationContext.getBean("appVersionManager");
        appVersionManager.reindex();

        action.setQ("*");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertEquals(4, action.getAppVersions().size());
    }

    @Test
    public void testEdit() throws Exception {
        log.debug("testing edit...");
        action.setId(-1L);
        assertNull(action.getAppVersion());
        assertEquals("success", action.edit());
        assertNotNull(action.getAppVersion());
        assertFalse(action.hasActionErrors());
    }

    @Test
    public void testSave() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setId(-1L);
        assertEquals("success", action.edit());
        assertNotNull(action.getAppVersion());

        AppVersion appVersion = action.getAppVersion();
        // update required fields
        appVersion.setNum(1L);

        action.setAppVersion(appVersion);

        assertEquals("input", action.save());
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());
        assertNotNull(request.getSession().getAttribute("messages"));
    }

    @Test
    public void testRemove() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setDelete("");
        AppVersion appVersion = new AppVersion();
        appVersion.setId(-2L);
        action.setAppVersion(appVersion);
        assertEquals("success", action.delete());
        assertNotNull(request.getSession().getAttribute("messages"));
    }
}