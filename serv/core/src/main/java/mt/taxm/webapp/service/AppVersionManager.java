package mt.taxm.webapp.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.appfuse.service.GenericManager;

import mt.taxm.model.AppVersion;

@WebService
@Path("appversion")
/**
 * 版本服务
 * @author Administrator
 *
 */
public interface AppVersionManager extends GenericManager<AppVersion,Long> {
    @GET
    @Path("/anymore/{cur_version}")
    /**
     * 查新版本
     * @param curVersion 当前版本
     * @return新版本信息
     */
	public AppVersion anymore(@PathParam("cur_version") int curVersion);
    
}
