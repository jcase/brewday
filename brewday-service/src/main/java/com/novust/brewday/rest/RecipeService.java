package com.novust.brewday.rest;

import com.novust.shared.recipe.Recipe;
import com.novust.shared.recipe.RecipeSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Component
@Path("/recipe")
public class RecipeService {
    public final static Logger logger = LoggerFactory.getLogger(RecipeService.class);

    @Autowired
    RecipeSource recipeSource;

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response list() {
        Collection<Recipe> all = recipeSource.getAll();
        return Response.ok().entity(all).build();
    }

    @Path("/{recipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response get(@PathParam("recipeId") String recipeId) {
        Recipe recipe = recipeSource.get(recipeId);
        if(recipe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(recipe).build();
    }
}
