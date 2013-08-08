package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import mt.taxm.webapp.model.TNsrjbxx;

import org.appfuse.service.GenericManager;

@WebService
@Path("/jbxx")
public interface TNsrjbxxManager extends GenericManager<TNsrjbxx, Long> {
    @GET
    @Path("{keyword}")
	List<TNsrjbxx> findByKeyword(@PathParam("keyword") String kw);
}
