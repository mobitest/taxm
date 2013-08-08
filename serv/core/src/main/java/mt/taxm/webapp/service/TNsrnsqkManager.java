package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import mt.taxm.webapp.model.TNsrnsqk;
import mt.taxm.webapp.model.TNsrnsqkId;

import org.appfuse.service.GenericManager;

@WebService
@Path("/ns")
public interface TNsrnsqkManager extends GenericManager<TNsrnsqk, TNsrnsqkId> {
    @GET
    @Path("{bm}")
	List<TNsrnsqk> findByBm(@PathParam("bm") String bm);
}
