package api.endpoints;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import api.payload.User;
import api.test.UserTests2;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints2 {	
	
	static Properties getURL() {
	    Properties routes = new Properties();
	    try (InputStream input = UserTests2.class.getClassLoader().getResourceAsStream("routes.properties")) {
	    	routes.load(input);
	    } catch (IOException e) {
	        e.printStackTrace(); 
	    }
	    return routes;
	}
	
	public static Response createUser(User payload) 	
	{	
		String post_url = getURL().getProperty("post_url");
		
		Response response =			
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)	
		.when()
			.post(post_url);	
		return response;	
	}	
	
	
	public static Response readUser(String userName) 	
	{			
		String get_url = getURL().getProperty("get_url");
		
		Response response =			
		given()
			.pathParam("username", userName)
		.when()
			.get(get_url);	
		return response;	
	}
	
	public static Response updateUser(String userName, User payload) 	
	{	
		
		String update_url = getURL().getProperty("update_url");
		
		Response response =			
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(payload)	
		.when()
			.put(update_url);	
		return response;	
	}	
	
	
	public static Response deleteUser(String userName) 	
	{	
		String delete_url = getURL().getProperty("delete_url");
		
		Response response =			
		given()
			.pathParam("username", userName)
		.when()
			.delete(delete_url);	
		return response;	
	}
	
	
}