package mt.taxm.webapp.service;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.appfuse.model.User;

@WebService
@Path("security")
public interface LoginManager {
    @GET
    @Path("login/{username}/{password}")
    /**
     * 登录认证
     * @param username 
     * @param password
     * @return 用户信息对象User，若失败，则id为-1
     */
	public User auth(@PathParam("username") String username, @PathParam("password")String password);
    
    @GET
    @Path("logout")
    /**
     * 注销
     */
    public void logout();
   
}
