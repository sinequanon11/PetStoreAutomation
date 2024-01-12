package api.test;

import static io.restassured.RestAssured.*;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserTestsLog {	
	
	Faker faker;
	User userPayload;
	
	public Logger logger;	
	
	@BeforeClass
	public void setup()
	{	
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());			
		
		logger = LogManager.getLogger(this.getClass());		
		
	}
	
	@Test(priority = 1)
	public void testPostUser()	{
		
		logger.debug(" **************** Creating User ******************* ");
		
		Response response = UserEndPoints.createUser(userPayload);		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.debug(" **************** User Is Created ***************** ");
	}
	
	@Test(priority = 2)
	public void testGetUserByName()
	{
		logger.debug(" **************** Reading User Info *************** ");
		
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);		
		
		logger.debug(" **************** User Info Displayed ************* ");
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName()
	{
		logger.debug(" **************** Updating User ******************* ");
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);		
		response.then().log().body();	
		
		// response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(), 200);	
		
		logger.debug(" **************** User Is Updated ***************** ");
		
		//Checking data after update
		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());		
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);	
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName()
	{
		logger.debug(" **************** Deleting User ******************* ");
		
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.debug(" **************** User Is Deleted ***************** ");
	}
		
	
	}