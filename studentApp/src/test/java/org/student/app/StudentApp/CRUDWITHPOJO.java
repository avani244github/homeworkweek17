package org.student.app.StudentApp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.student.path.path.StudentAppEndPoints;
import org.student.path.model_studentPojo.StudentPojo;
import org.student.path.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CRUDWITHPOJO {

    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    int studentId;
    static String firstName = "Avani" + TestUtils.getRandomValue();
    static String lastName = "patel" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "avanid5d5d@gmail.com";
    static String programme = "Testing";

    @Test(priority = 1)
    public void getAllStudentsInfo() {

        response = RestAssured.given().log().all()
                .when()
                .get(StudentAppEndPoints.GET_ALL_STUDENTS);
        response.then().log().all().statusCode(200);
    }

    @Test(priority = 2)
    public void postStudentsInfo002() {

        List<String> courses = new ArrayList<>();
        courses.add("selenium");
        courses.add("api-api");

        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName(firstName);
        studentPojo.setLastName(lastName);
        studentPojo.setEmail(email);
        studentPojo.setProgramme(programme);
        studentPojo.setcourses(courses);


        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(studentPojo)
                        .post(StudentAppEndPoints.Post_Student);
        response.then().log().all().statusCode(201)
                .body("msg", equalTo("Student added"));
    }

    @Test(priority = 3)
    public void getAllStudentAndExtractIdOfPostData003() {
        HashMap<String, Object> studentDetails = given().log().all()
                .when()
                .get(StudentAppEndPoints.GET_ALL_STUDENTS) // refer to endpoints class in path package
                .then()
                .extract()
                .path("findAll{it.firstName=='" + firstName + "'}.get(0)");
        System.out.println(studentDetails);
        studentId = (int) studentDetails.get("id");

    }

    @Test(priority = 4)
    public void getSingleStudentInfo004() {
        given()
                .pathParam("id", studentId)
                .get(StudentAppEndPoints.Get_By_Id)
                .then().statusCode(200)
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName));

    }

    @Test(priority = 5) // delete above id (priority = 1)
    public void verifyNewStudentInfoDeletedByID005() {
        given()
                .pathParam("id", studentId)
                .when()
                .delete(StudentAppEndPoints.Get_By_Id)
                .then()
                .statusCode(204);
    }

    @Test(priority = 6)
    public void verifyStudentInfoDeleted006() {
        given()
                .pathParam("id", studentId)
                .when()
                .get(StudentAppEndPoints.Get_By_Id)
                .then().statusCode(404);
    }





}
