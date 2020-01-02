package test;

import api.BaseTest;
import data.OrderDataProvider;
import eatsleeprepeat.api.model.EatSleepRepeatReturnTypeModel;
import eatsleeprepeat.api.rest.EatSleepRepeatService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrdersTest extends BaseTest {

    private EatSleepRepeatService eatSleepRepeatService = server.initialise(EatSleepRepeatService.class);
    private JSONObject jsonObj = new JSONObject();
    private static Logger log = LoggerFactory.getLogger(OrdersTest.class);

    @BeforeClass()
    public void postOrderForUpdating() {
        jsonObj.put("id", "2");
        jsonObj.put("whoOrdered", "Trajko");
        jsonObj.put("orderDesc", "leskovacka so pomfrit");
        jsonObj.put("whereToBuyTheOrder", "Vili");
        log.info("JSON object filled successfully.");
    }

    @Test(priority = 1)
    public void shouldGetAllOrders() {
        ResponseEntity<EatSleepRepeatReturnTypeModel[]> eatSleepRepeatModelResponseEntity = eatSleepRepeatService.getOrders();
        assertTrue(eatSleepRepeatModelResponseEntity.getStatusCode().equals(HttpStatus.OK), "status not as expected");
        log.info("Asserted response is with status: " + eatSleepRepeatModelResponseEntity.getStatusCode());
        List<EatSleepRepeatReturnTypeModel> orderList = Arrays.asList(eatSleepRepeatModelResponseEntity.getBody());
//        assertEquals(orderList.get(0).getId(),"1","id not as expected");
//        assertEquals(orderList.size(),1,"size not as expected");
    }

    @Test(priority = 2, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldGetOrderById(JSONObject object) {
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntityPost = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntityPost.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel postedOrder = eatSleepRepeatResponseEntityPost.getBody();
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatReturnTypeModelResponseEntity = eatSleepRepeatService.getOrderById(postedOrder.getId());
        assertTrue(eatSleepRepeatReturnTypeModelResponseEntity.getStatusCode().equals(HttpStatus.OK));
        log.info("Asserted response is with status: " + eatSleepRepeatReturnTypeModelResponseEntity.getStatusCode());
        EatSleepRepeatReturnTypeModel returnedOrder = eatSleepRepeatReturnTypeModelResponseEntity.getBody();
        assertTrue(returnedOrder.getId().equals("2"));
        assertEquals(returnedOrder.getWhoOrdered(), "Trajko");
        assertEquals(returnedOrder.getOrderDesc(), "hamburger i koka kola");
        assertEquals(returnedOrder.getWhereToBuyTheOrder(), "sedmica");
        log.info("Successfully asserted values.");
    }

    @Test(priority = 3, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldPostOrder(JSONObject object) {
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntity = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntity.getStatusCode().equals(HttpStatus.OK));
        log.info("Asserted response is with status: " + eatSleepRepeatResponseEntity.getStatusCode());
        EatSleepRepeatReturnTypeModel orderById = eatSleepRepeatResponseEntity.getBody();
        assertEquals(orderById.getId(), "2");
        assertEquals(orderById.getWhoOrdered(), "Trajko");
        assertEquals(orderById.getOrderDesc(), "hamburger i koka kola");
        assertEquals(orderById.getWhereToBuyTheOrder(), "sedmica");
        log.info("Succesfully asserted values. ");
    }

    @Test(priority = 4, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldUpdateOrder(JSONObject object) throws IOException {
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntity = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntity.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel postedOrder = eatSleepRepeatResponseEntity.getBody();
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatReturnTypeModelResponseEntity = eatSleepRepeatService.updateOrder(jsonObj, postedOrder.getId());
        EatSleepRepeatReturnTypeModel finalOrder = eatSleepRepeatReturnTypeModelResponseEntity.getBody();
        assertTrue(eatSleepRepeatReturnTypeModelResponseEntity.getStatusCode().equals(HttpStatus.OK));
        log.info("Asserted response is with status: " + eatSleepRepeatReturnTypeModelResponseEntity.getStatusCode());
        assertEquals(finalOrder.getId(), "2");
        assertEquals(finalOrder.getWhoOrdered(), "Trajko");
        assertEquals(finalOrder.getOrderDesc(), "leskovacka so pomfrit");
        assertEquals(finalOrder.getWhereToBuyTheOrder(), "Vili");
        log.info("Succesfully asserted values. ");
    }

    @Test(priority = 5, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldDeleteOrder(JSONObject object) {
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntity = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntity.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel orderBody = eatSleepRepeatResponseEntity.getBody();
        ResponseEntity<String> eatSleepRepeatResponseEntityDelete = eatSleepRepeatService.deleteOrder(orderBody.getId());
        assertTrue(eatSleepRepeatResponseEntityDelete.getStatusCode().equals(HttpStatus.OK));
        log.info("Asserted response is with status: " + eatSleepRepeatResponseEntityDelete.getStatusCode());
    }

    @AfterClass
    public void afterClassMessage() {
        log.info("Successfully finished all tests !!! ");
    }
}
