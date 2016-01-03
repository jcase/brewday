package com.novust.brewday.rest;

import com.novust.shared.dao.HopDataDao;
import com.novust.shared.data.HopData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/hops")
public class HopService {
    static Logger logger = LoggerFactory.getLogger(HopService.class);

    @Autowired
    HopDataDao hopDataDao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{hopname}")
    public String getHopName(@PathParam("hopname") String hopName) {
        return hopName;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHop(HopData newHopData) {
        logger.info("Creating hop " + newHopData.getName());
        newHopData.id = stripNonAlpha(newHopData.getName());
        if(hopDataDao.exists(newHopData.id)) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            hopDataDao.addData(newHopData);
            return Response.ok().entity(newHopData).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HopData> getAllHops() {
        logger.info("Getting hop list");
        List<HopData> allData = hopDataDao.getAllData();
        return allData;
    }

    String stripNonAlpha(String input) {
        String step = input.replaceAll("\\P{Alnum}", "");
        return step.replaceAll("\\s","");
    }

}
