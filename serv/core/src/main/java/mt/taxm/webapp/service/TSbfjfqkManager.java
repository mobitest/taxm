package mt.taxm.webapp.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import mt.taxm.webapp.model.TSbfjfqk;
import mt.taxm.webapp.model.TSbfjfqkId;

import org.appfuse.service.GenericManager;

@WebService
@Path("/sbfjf")
public interface TSbfjfqkManager extends GenericManager<TSbfjfqk, TSbfjfqkId> {
    @GET
    @Path("{bm}")
	List<TSbfjfqk> findByBm(@PathParam("bm") String bm);
}
