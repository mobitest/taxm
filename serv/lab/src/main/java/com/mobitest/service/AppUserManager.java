package com.mobitest.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.appfuse.service.GenericManager;

import com.mobitest.model.AppUser;
@WebService
@Path("/appuser")
public interface AppUserManager extends GenericManager<AppUser, Integer> {
    @GET
    @Path("{email}")
	List<AppUser> findByEmail(@PathParam("email") String email);
}
