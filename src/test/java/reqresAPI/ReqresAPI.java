package reqresAPI;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ReqresAPI {

    String url = "https://reqres.in/";

    String payload = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";

    String token = "QpwL5tke4Pnpja7X4";

    @DisplayName("Get list of users")
    @Test
    public void GetUserLists(){
        given().accept(ContentType.JSON).and().queryParam("page",2)
                .when().get(url+"api/users").then().statusCode(200);
    }
    @DisplayName("Get single user")
    @Test
    public void GetSingleUser(){
        JsonPath jsonPath = given().accept(ContentType.JSON).pathParam("id", "2")
                .when().get(url + "api/users/{id}").prettyPeek().then().statusCode(200).extract().jsonPath();

        System.out.println(jsonPath.getString("data"));
    }

    @DisplayName("Get single user not found")
    @Test
    public void GetSingleUserNotFound(){
        given().accept(ContentType.JSON).pathParam("id",23)
                .when().get(url+"api/user/{id}").prettyPeek().then().statusCode(404);
    }

    @DisplayName("Get list resources")
    @Test
    public void GetListResources(){
        given().accept(ContentType.JSON).when().get(url+"api/unknow").prettyPeek().then().statusCode(200);
    }
    @DisplayName("Get list single resources")
    @Test
    public void GetSingleList(){
        given().accept(ContentType.JSON).when().get(url+"api/unknow/2").prettyPeek().then().statusCode(200);
    }
    @DisplayName("Get list single resources not found")
    @Test
    public void GetSingleListNotFound(){
        given().accept(ContentType.JSON).when().get(url+"api/unknow/23").prettyPeek().then().statusCode(404);
    }
    @DisplayName("Post user")
    @Test
    public void PostUser(){
        given().body(payload).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(url+"api/users").prettyPeek().then().statusCode(201);
    }
    @DisplayName("Put userId2")
    @Test
    public void PutUserId2(){
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        given().body(body).accept(ContentType.JSON).contentType(ContentType.JSON)
                .pathParam("id",2).when().put(url+"api/users/{id}").prettyPeek()
                .then().statusCode(200);
    }
    @DisplayName("Patch userId2")
    @Test
    public void PatchUserId2(){
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        given().body(body).accept(ContentType.JSON).contentType(ContentType.JSON)
                .pathParam("id",2).when().patch(url+"api/users/{id}").prettyPeek()
                .then().statusCode(200);
    }
    @DisplayName("Delete userId2")
    @Test
    public void DeleteUserId2(){
        given().accept(ContentType.JSON).pathParam("id",2).when().delete(url+"api/users/{id}")
                .prettyPeek().then().statusCode(204);
    }

    @DisplayName("Post register")
    @Test
    public void PostRegister(){
        String payload = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";
        given().accept(ContentType.JSON).body(payload).contentType(ContentType.JSON)
                .when().post(url+"api/register").prettyPeek().then().statusCode(200);
    }
    @DisplayName("Post register unsuccessful")
    @Test
    public void PostRegisterUnsuccessful(){
        String email = "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}";
        given().accept(ContentType.JSON).body(email).contentType(ContentType.JSON)
                .when().post(url+"api/register").prettyPeek().then().statusCode(400);
    }
    @DisplayName("Post login successful")
    @Test
    public void PostLoginSuccessful(){
        String usernamePass = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        given().body(usernamePass).contentType(ContentType.JSON).when().post(url+"api/login")
                .prettyPeek().then().statusCode(200);
    }
    @DisplayName("Post login unsuccessful")
    @Test
    public void PostLoginUnsuccessful(){
        String loginEmail = "{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}";
        given().body(loginEmail).contentType(ContentType.JSON)
                .when().post(url+"api/login").prettyPeek().then().statusCode(400);
    }
    @DisplayName("Get delay respond")
    @Test
    public void GetDelayRespond(){
        given().accept(ContentType.JSON).queryParam("id",3).when().get(url+"api/users")
                .prettyPeek().then().statusCode(200);
    }



}
