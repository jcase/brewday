package com.novust.brewday.rest;

import com.novust.shared.dao.HopDataDao;
import com.novust.shared.data.HopData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<HopData> getAllHops() {
        logger.info("Getting hop list");
        List<HopData> allData = hopDataDao.getAllData();
        return allData;
    }
}
