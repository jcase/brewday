package com.novust.brewday.rest;

import com.novust.shared.dao.YeastDataDao;
import com.novust.shared.data.YeastData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/yeast")
public class YeastService {

    @Autowired
    YeastDataDao yeastDataDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<YeastData> getAllYeast() {
        return yeastDataDao.getAllData();
    }
}
