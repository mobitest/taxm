package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import mt.taxm.webapp.model.TNsrfpgzqk;
import mt.taxm.webapp.model.TNsrfpgzqkId;

import org.appfuse.service.GenericManager;

@WebService
@Path("fpgz")
public interface TNsrfpgzqkManager extends GenericManager<TNsrfpgzqk, TNsrfpgzqkId> {
    @GET
    @Path("{bm}")
	List<TNsrfpgzqk> findByBm(@PathParam("bm") String email);
}
