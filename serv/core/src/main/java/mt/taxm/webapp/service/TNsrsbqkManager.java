package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import mt.taxm.webapp.model.TNsrsbqk;
import mt.taxm.webapp.model.TNsrsbqkId;

import org.appfuse.service.GenericManager;

@WebService
@Path("/sb")
public interface TNsrsbqkManager extends GenericManager<TNsrsbqk, TNsrsbqkId> {
    @GET
    @Path("{bm}")
	List<TNsrsbqk> findByBm(@PathParam("bm") String bm);
}
