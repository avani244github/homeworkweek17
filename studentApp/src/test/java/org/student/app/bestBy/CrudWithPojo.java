package org.student.app.bestBy;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.student.path.model_studentPojo.BestByProductPojo;
import org.student.path.path.BestByProductEndPoint;
import org.student.path.utils.TestUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CrudWithPojo {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int id;
    static String name = "Monitor"+ TestUtils.getRandomValue();
    static String type = "FullScreen"+ TestUtils.getRandomValue();
    static int price = 500 +TestUtils.getRandomNumber();
    static int shipping = 2+TestUtils.getRandomNumber();
    static String upc = "xyz"+ TestUtils.getRandomValue();
    static String description = "string"+ TestUtils.getRandomValue();
    static String manufacturer = "string"+ TestUtils.getRandomValue();
    static String model = "tab32"+ TestUtils.getRandomValue();
    static String url = "string"+ TestUtils.getRandomValue();
    static String image = "string"+ TestUtils.getRandomValue();
//    static String updatedAt = "string"+ TestUtils.getRandomValue();
//    static String createdAt = "string"+ TestUtils.getRandomValue();

    @Test(priority = 1)
    public void getAllProductsInfo() {

        response = RestAssured.given().log().all()
                .when()
                .get(BestByProductEndPoint.GET_ALL_Products);
        response.then().log().all().statusCode(200);
    }

    @Test(priority = 2)
    public void postProductsInfo002() {

        BestByProductPojo bestbyProductPojo = new BestByProductPojo();
        bestbyProductPojo.setName(name);
        bestbyProductPojo.setType(type);
        bestbyProductPojo.setPrice(price);
        bestbyProductPojo.setShipping(shipping);
        bestbyProductPojo.setUpc(upc);
        bestbyProductPojo.setDescription(description);
        bestbyProductPojo.setManufacturer(manufacturer);
        bestbyProductPojo.setModel(model);
        bestbyProductPojo.setUrl(url);
        bestbyProductPojo.setImage(image);
        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(bestbyProductPojo)
                .post(BestByProductEndPoint.GET_ALL_Products);
        response.then().log().all().statusCode(201)
                .body("name", equalTo(name));
        id = response.path("id");
        System.out.println(id);
    }


    @Test(priority = 3)
    public void getSingleProductInfo003() {
        given()
                .pathParam("id", id)
                .get(BestByProductEndPoint.Get_By_Id)
                .then().statusCode(200)
                .body("name", equalTo(name))
                .body("model", equalTo(model));

    }

    @Test(priority = 4)
    public void verifyNewProductInfoDeletedByID004() {
        given()
                .pathParam("id", id)
                .when()
                .delete(BestByProductEndPoint.Get_By_Id)
                .then()
                .statusCode(200);
    }

    @Test(priority = 5)
    public void verifyProductInfoDeleted005() {
        given()
                .pathParam("id", id)
                .when()
                .get(BestByProductEndPoint.Get_By_Id)
                .then().statusCode(404);
    }

}

