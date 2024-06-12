package apiauto;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class negativeCase {
  String baseUrl = "https://reqres.in/api";

  @Test
  public void testPutUserWithInvalidPayload() {
    // initizlie the data
    RestAssured.baseURI = baseUrl;

    RestAssured
        .given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body("test") // invalid payload with string data type instead of json object
        .when().post("/users")
        .then().log().all()
        .assertThat().statusCode(400);
  }

  @Test
  public void testPutUserWithInvalidData() {
    // initilize the data
    RestAssured.baseURI = baseUrl;

    int userId = 2;

    RestAssured
        .given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body("test") // invalid payload
        .when().put("/users/" + userId)
        .then().log().all()
        .assertThat().statusCode(400);
  }

  @Test
  public void testCreateUserWithInvalidPayload() {
    // initizlie the data
    RestAssured.baseURI = baseUrl;

    RestAssured
        .given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body("test") // invalid payload
        .when().post("/users")
        .then().log().all()
        .assertThat().statusCode(400);
  }

  @Test
  public void testPatchUserWithInvalidData() {
    // initilize the data
    RestAssured.baseURI = baseUrl;

    int userId = 2;

    RestAssured
        .given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body("Supper") // invalid payload
        .when().patch("/users/" + userId)
        .then().log().all()
        .assertThat().statusCode(400);
  }
}
