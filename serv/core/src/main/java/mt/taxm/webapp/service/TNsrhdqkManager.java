package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import mt.taxm.webapp.model.TNsrhdqk;
import mt.taxm.webapp.model.TNsrhdqkId;

import org.appfuse.service.GenericManager;

@WebService
@Path("/hd")
public interface TNsrhdqkManager extends GenericManager<TNsrhdqk, TNsrhdqkId> {
    @GET
    @Path("{bm}")
	List<TNsrhdqk> findByBm(@PathParam("bm") String bm);
}
