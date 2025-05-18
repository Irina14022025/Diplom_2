import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.LoginModel;
import model.OrderModel;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;
import steps.UserSteps;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.*;
import static steps.OrderSteps.*;
import static testData.TestValue.*;

public class CreateOrderTest {
    private UserModel user;
    private UserSteps userSteps;
    private OrderModel order;
    private OrderSteps orderSteps;
    private String accessToken;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        user = new UserModel(USER_EMAIL, USER_PASSWORD, USER_NAME);
        userSteps = new UserSteps();
        createUser(user);
        LoginModel loginModel = LoginModel.from(user);
        Response response = userAuthorization(loginModel);
        accessToken = response.path("accessToken");

    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией и с ингредиентами")
    public void createOrderAuthorizedUserWithIngredientsTest(){
        order = new OrderModel(INGREDIENT);
        orderSteps = new OrderSteps();
        createOrderAuthorizedUser(order, accessToken)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизации и с ингредиентами")
    public void createOrderUnauthorizedUserWithIngredientsTest(){
        order = new OrderModel(INGREDIENT);
        orderSteps = new OrderSteps();
        createOrderUnauthorizedUser(order)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией и без ингредиентов")
    public void createOrderAuthorizedUserWithoutIngredientsTest(){
        order = new OrderModel();
        orderSteps = new OrderSteps();
        createOrderAuthorizedUser(order, accessToken)
                .then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией и c неверным хэшем ингредиентов")
    public void createOrderAuthorizedUserWithInvalidHashIngredientsTest(){
        order = new OrderModel(INGREDIENT_INVALID_HASH);
        orderSteps = new OrderSteps();
        createOrderUnauthorizedUser(order)
                .then()
                .statusCode(500);
    }

    @After
    public void cleanUp(){
        deleteUser(accessToken)
                .then()
                .statusCode(202)
                .body("success", equalTo(true));
    }
}
