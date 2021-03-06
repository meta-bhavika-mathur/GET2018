package com.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.facade.AdvertisementFacade;
import com.facade.CategoryFacade;
import com.google.gson.Gson;
import com.model.Advertisement;
import com.model.Category;

@Path("/categories")
public class CategoryResource
{
    private CategoryFacade categoryFacade = new CategoryFacade();
    final String BATCH_NAME = "GET-2018";
    final String ERROR_MESSAGE = "Error: Unauthorized Access";
   
   @GET
   @Path("/getCategory")
   @Produces(MediaType.APPLICATION_JSON)
   public String getAllCategories(@HeaderParam("Authorization") String authorizationString) 
   {
       if(!BATCH_NAME.equals(authorizationString))
       {
           return ERROR_MESSAGE;
       }
       List<Category> categoryList = categoryFacade.getAll();
       Gson gson = new Gson();
       String categoryJson = gson.toJson(categoryList);
       return categoryJson;
   }
   
   @POST
   @Path("/addCategory")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN)
   public boolean addCategory(String categoryInput, @HeaderParam("Authorization") String authorizationString)
   {
       if(!BATCH_NAME.equals(authorizationString))
       {
           return false;
       }
       Gson g = new Gson();
       Category category = g.fromJson(categoryInput, Category.class);
       return categoryFacade.add(category);
   }
   
   @PUT
   @Path("/updateCategory")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.TEXT_PLAIN)
   public boolean updateCategory(String categoryNames, @HeaderParam("Authorization") String authorizationString)
   {
       if(!BATCH_NAME.equals(authorizationString))
       {
           return false;
       }
       String[] categoryName = categoryNames.split(" to ");
       return categoryFacade.updateName(categoryName[0], categoryName[1]);
   }
}