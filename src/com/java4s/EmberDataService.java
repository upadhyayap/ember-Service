package com.java4s;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;

import com.entities.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;


@Path("/my-app")
public class EmberDataService {
static Logger log = Logger.getLogger(EmberDataService.class.getName());
  @GET
  @Produces("text/html")
  public Response getLocalCust() {

           String output = "I am from 'getLocalCust' method";
           return Response.status(200).entity(output).build();
  }
  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsersList() {
	  Response res = null;
	    System.out.println("Inside Users");
	    Configuration configuration = new Configuration().configure();
	    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
	    SessionFactory factory = configuration.buildSessionFactory(builder.build());
	    Session session = factory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM User");
		List<User> users = query.list();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		ObjectWriter writer = mapper.writer().withRootName("users");
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES); 
        try {
        	res =  Response.status(200).entity(writer.writeValueAsString(users)).build();
        	System.out.println(writer.writeValueAsString(users));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return res;
  }
  @PUT
  @Path("/users/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response EditUser(@PathParam("id") String id,String user) {
	  System.out.println("user param is----" +user);
	    Response res = null;
	    Configuration configuration = new Configuration().configure();
	    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
	    SessionFactory factory = configuration.buildSessionFactory(builder.build());
	    Session session = factory.openSession();
		session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		try {
			User userFromJson = mapper.readValue(user, User.class);
			System.out.println(userFromJson.getId()+"--"+userFromJson.getName()+"--"+userFromJson.getBio()+"--"+userFromJson.getEmail()
					+"--"+userFromJson.getAvatarUrl());
			User DBuser = (User) session.get(User.class, Integer.parseInt(id));
			DBuser.setName(userFromJson.getName());
			DBuser.setAvatarUrl(userFromJson.getAvatarUrl());
			DBuser.setBio(userFromJson.getBio());
			DBuser.setEmail(userFromJson.getEmail());
			DBuser.setCreationDate(userFromJson.getCreationDate());
			session.saveOrUpdate(DBuser);
			session.getTransaction().commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        res =  Response.status(200).entity("id is ... "+ id ).build();
		
        return res;
  }
  @DELETE
  @Path("/users/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteUser(@PathParam("id") String id) {
	    Response res = null;
	    Configuration configuration = new Configuration().configure();
	    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
	    SessionFactory factory = configuration.buildSessionFactory(builder.build());
	    Session session = factory.openSession();
		session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		User DBuser = (User) session.get(User.class, Integer.parseInt(id));
		session.delete(DBuser);
		session.getTransaction().commit();
		session.close();
        res =  Response.status(200).entity("id is ... "+ id ).build();
        return res;
  }
  @POST
  @Path("/users/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(String user) {
	    Response res = null;
	    Configuration configuration = new Configuration().configure();
	    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
	    SessionFactory factory = configuration.buildSessionFactory(builder.build());
	    Session session = factory.openSession();
		session.beginTransaction();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		try {
			User userFromJson = mapper.readValue(user, User.class);
			session.save(userFromJson);
			session.getTransaction().commit();
			session.close();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        res =  Response.status(200).entity("Create Success" ).build();
        return res;
  }
}