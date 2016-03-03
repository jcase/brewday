package com.novust.brewday.rest;

import com.novust.shared.dao.VerbDataDao;
import com.novust.shared.data.VerbData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/verb")
public class VerbService {
    static Logger logger = LoggerFactory.getLogger(VerbService.class);

    @Autowired
    VerbDataDao verbDataDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<VerbData> getAllVerbs() {
        List<VerbData> allData = verbDataDao.getAllData();
        return allData;
    }
}
