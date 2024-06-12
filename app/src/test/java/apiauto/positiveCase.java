package apiauto;

import java.util.HashMap;
import java.io.File;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

public class positiveCase {
  // Initialize all the data
  String baseUrl = "https://reqres.in/api";

  @Test
  public void testGetListUsers() {
    RestAssured.baseURI = baseUrl;

    // create file for jsonSchema
    File file = new File("src/test/resources/jsonSchema/getListUserSchema.json");

    RestAssured
        .given().when()
        .get("/users?page=2")
        .then().log().all()
        .assertThat().statusCode(200)
        .assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
  }

  @Test
  public void testCreateUser() {
    // initizlie the data
    RestAssured.baseURI = baseUrl;

    String name = "Dimas", job = "QA";

    JSONObject bodyObj = new JSONObject();
    bodyObj.put("name", name);
    bodyObj.put("job", job);

    File file = new File("src/test/resources/jsonSchema/createUserSchema.json");

    RestAssured
        .given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body(bodyObj.toString())
        .when().post("/users")
        .then().log().all()
        .assertThat().statusCode(201)
        .assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
  }

  @Test
  public void testPutUser() {
    // initilize the data
    RestAssured.baseURI = baseUrl;
    int userId = 2;
    String name = "Updated User";

    // Get the current Data from API
    String fname = RestAssured.given().when().get("/users/" + userId).getBody().jsonPath().getString("data.name");
    String job = RestAssured.given().when().get("/users/" + userId).getBody().jsonPath().getString("data.job");

    System.out.println("First Name before udpate: " + fname);

    // change first name
    // create hashMap for the body
    HashMap<String, Object> bodyMap = new HashMap<>();
    bodyMap.put("id", userId);
    bodyMap.put("name", name);
    bodyMap.put("job", job);

    JSONObject jsonObj = new JSONObject(bodyMap);

    RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body(jsonObj.toString())
        .when().put("/users/" + userId)
        .then().log().all()
        .assertThat().statusCode(200)
        .assertThat().body("name", Matchers.equalTo(name));
  }

  @Test
  public void testPatchUser() {
    // initilize the data
    RestAssured.baseURI = baseUrl;

    File file = new File("src/test/resources/jsonSchema/updatedUser.json");

    String userId = "2";
    String customName = "Updated User 2";

    // Get the current Data from API
    String currentName = RestAssured.given().when().get("/users/" + userId).getBody().jsonPath().getString("data.name");

    // create json object
    HashMap<String, Object> bodyObj = new HashMap<>();
    bodyObj.put("name", customName);
    JSONObject jsonObj = new JSONObject(bodyObj);

    System.out.println("First Name before udpate: " + currentName);

    RestAssured.given()
        .header("Content-Type", "application/json")
        .body(jsonObj.toString())
        .when().patch("/users/" + userId)
        .then().log().all()
        .assertThat().statusCode(200)
        .assertThat().body("name", Matchers.equalTo(customName))
        .assertThat().body(JsonSchemaValidator.matchesJsonSchema(file));
  }

  @Test
  public void testDeleteUser() {

    // initialize the data
    RestAssured.baseURI = baseUrl;

    String userId = "2";

    RestAssured.given()
        .when().delete("/users/" + userId)
        .then().log().all()
        .assertThat().statusCode(204);
  }
}
