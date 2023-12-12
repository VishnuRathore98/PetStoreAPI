package api.tests;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User userPayload;

    @BeforeTest
    void setupData(){
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUserName(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        userPayload.setUserStatus(0);
    }

    //Creating user
    @Test(priority = 1)
    void testCreateUser(){
        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
    }

    //Get User Details
    @Test(priority = 2)
    void testGetUser(){
        Response response = UserEndPoints.readUser(userPayload.getUserName());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
    }

    //Updating user details
    @Test(priority = 3)
    void testUpdateUser(){
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());

        Response response = UserEndPoints.updateUser(this.userPayload.getUserName(), userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
    //Checking data after update
        Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUserName());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
    }

    //Deleting the user
    @Test(priority = 4)
    void testDeleteUser(){
        Response response = UserEndPoints.deleteUser(this.userPayload.getUserName());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}