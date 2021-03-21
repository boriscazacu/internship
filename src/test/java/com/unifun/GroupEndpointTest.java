package com.unifun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unifun.endpoints.GrupEndPoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GroupEndpointTest {

    @Inject
    GrupEndPoint grupEndPoint;

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/group/list")
          .then()
             .statusCode(200)
             .body("", is("hello"));
    }

    @Test
    void testList() throws JsonProcessingException {
        Assertions.assertFalse(grupEndPoint.getGrupList().isEmpty());
    }
}