package component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostStockBody;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static helpers.GenerateTestDataHelper.generateRandomString;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class StocksComponentTests {
    public static final String POST_STOCK_RESPONSE_SCHEMA_JSON = "schemas/PostStockResponseSchema.json";
    public static final String GET_STOCK_SCHEMA_JSON = "schemas/GetStockSchema.json";
    private String baseUrl = "http://localhost:8081";
    private String urlPath = "stocks";
    private ObjectMapper mapper;
    private RequestSpecification apiBassSpecification;
    private String symbol;
    private String company;

    @BeforeEach
    public void beforeEach() {
        mapper = new ObjectMapper();
        apiBassSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();

        symbol = generateRandomString(4).toUpperCase();
        company = String.format("%s Co.", symbol);
    }

    @Test
    public void createStock() throws JsonProcessingException {
        String jsonBody = buildStockRequestBody(symbol, company);

        given()
            .spec(apiBassSpecification)
            .body(jsonBody)
        .when()
            .post("stocks")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(POST_STOCK_RESPONSE_SCHEMA_JSON))
            .body("id", greaterThan(0))
            .body("symbol", is(symbol))
            .body("company", is(company));

    }

    @Test
    public void updateStock() throws JsonProcessingException {
        Stock createdStock = createStock(symbol, company);
        String putStockPath = urlPath + "/" + createdStock.id();

        String newSymbol = generateRandomString();
        String newCompany = generateRandomString();

        createdStock.setSymbol(newSymbol).setCompany(newCompany);

        String updateBody = buildStockRequestBody(newSymbol, newCompany);

        Stock updatedStock = given()
            .spec(apiBassSpecification)
            .body(updateBody)
        .when()
            .put(putStockPath)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(POST_STOCK_RESPONSE_SCHEMA_JSON))
            .extract().as(Stock.class);

        assertThat(updatedStock).isEqualTo(createdStock);
    }

    @Test
    public void listAllStocks() throws JsonProcessingException {
        createStockSpecification(symbol, company);
        given()
            .spec(apiBassSpecification)
        .when()
            .get(urlPath)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath(GET_STOCK_SCHEMA_JSON));

        //TODO: It needs more validations
    }

    private Stock createStock(String symbol, String company) throws JsonProcessingException {
        String jsonBody = buildStockRequestBody(symbol, company);

        return given()
                .spec(apiBassSpecification)
                .body(jsonBody)
                .post(urlPath)
                .then()
                .statusCode(200)
                .extract().as(Stock.class);
    }

    private RequestSpecification createStockSpecification(String symbol, String company) throws JsonProcessingException {
        String jsonBody = buildStockRequestBody(symbol, company);

        given()
            .spec(apiBassSpecification)
            .body(jsonBody)
            .post(urlPath)
        .then()
            .statusCode(200);

        return apiBassSpecification;
    }

    private String buildStockRequestBody(String symbol, String company) throws JsonProcessingException {
        PostStockBody body = new PostStockBody();
        body.setSymbol(symbol).setCompany(company);

        String jsonBody = mapper.writeValueAsString(body);
        return jsonBody;
    }
}
