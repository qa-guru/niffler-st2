package niffler.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import niffler.model.ISpend;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.oneOf;

public class HttpHandler {

    private final RequestSpecification requestSpecification;

    public HttpHandler(String url) {
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(URI.create(url)).build();
    }

    public ISpend executePost(String path, ISpend pojo) {
        Response response = given()
            .spec(requestSpecification)
                .contentType("application/json")
            .body(pojo)
            .post(path)
                .then().extract().response();
//                .statusCode(oneOf(200, 201))
        if(response.statusCode() == 200 || response.statusCode() == 201){
            return response.as(pojo.getClass());
        }
        return null;
    }

}
