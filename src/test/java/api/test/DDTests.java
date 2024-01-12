package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	
	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String userName, String fname, String lname, String useremail, String pwd, String ph) {
	    try {
	        int userIdInt = Integer.parseInt(userID);

	        User userPayload = new User();

	        userPayload.setId(userIdInt);
	        userPayload.setUsername(userName);
	        userPayload.setFirstName(fname);
	        userPayload.setLastName(lname);
	        userPayload.setEmail(useremail);
	        userPayload.setPassword(ph);
	        userPayload.setPhone(ph);

	        Response response = UserEndPoints.createUser(userPayload);
	        Assert.assertEquals(response.getStatusCode(), 200);

	    } catch (NumberFormatException e) {	        
	        System.out.println("Skipping test for invalid user ID: " + userID);
	    }
	}
	

	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {	   	   
	    
	    Response response = UserEndPoints.deleteUser(userName);
	    Assert.assertEquals(response.getStatusCode(), 200);
	}

	
	
}
