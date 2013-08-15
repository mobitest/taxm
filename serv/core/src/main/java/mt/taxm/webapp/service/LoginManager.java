package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@WebService
@Path("login")
public interface LoginManager {
    @POST
    @Path("{userid}/{password}")
	public List<String> validate(String userid, String password);
}
