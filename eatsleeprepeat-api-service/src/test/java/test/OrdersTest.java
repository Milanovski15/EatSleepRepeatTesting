package test;
import api.BaseTest;
import data.OrderDataProvider;
import eatsleeprepeat.api.model.EatSleepRepeatModel;
import eatsleeprepeat.api.model.EatSleepRepeatReturnTypeModel;
import eatsleeprepeat.api.rest.EatSleepRepeatService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrdersTest extends BaseTest {

    private EatSleepRepeatService eatSleepRepeatService= server.initialise(EatSleepRepeatService.class);
    EatSleepRepeatModel orderForUpdating = new EatSleepRepeatModel("","","","");
    JSONObject jsonObj = new JSONObject();

    @BeforeClass()
    public void postOrderForUpdating(){
            jsonObj.put("id", "2");
            jsonObj.put("whoOrdered", "Trajko");
            jsonObj.put("orderDesc", "leskovacka so pomfrit");
            jsonObj.put("whereToBuyTheOrder", "Vili");
    }


    @Test(priority = 1)
    public void shouldGetAllOrders(){
        ResponseEntity<EatSleepRepeatReturnTypeModel[]> eatSleepRepeatModelResponseEntity = eatSleepRepeatService.getOrders();
        assertTrue(eatSleepRepeatModelResponseEntity.getStatusCode().equals(HttpStatus.OK),"status not as expected");
        List<EatSleepRepeatReturnTypeModel> orderList = Arrays.asList(eatSleepRepeatModelResponseEntity.getBody());
        assertEquals(orderList.get(0).getId(),"1","id not as expected");
        assertEquals(orderList.size(),1,"size not as expected");
    }

    @Test(priority = 2,dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldGetOrderById(JSONObject object){
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntityPost = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntityPost.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel postedOrder = eatSleepRepeatResponseEntityPost.getBody();
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatReturnTypeModelResponseEntity = eatSleepRepeatService.getOrderById(postedOrder.getId());
        assertTrue(eatSleepRepeatReturnTypeModelResponseEntity.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel returnedOrder = eatSleepRepeatReturnTypeModelResponseEntity.getBody();
        assertTrue(returnedOrder.getId().equals("2"));
        assertEquals(returnedOrder.getWhoOrdered(),"Trajko");
        assertEquals(returnedOrder.getOrderDesc(), "hamburger i koka kola");
        assertEquals(returnedOrder.getWhereToBuyTheOrder(),"sedmica");
    }

    @Test(priority = 3, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldPostOrder(JSONObject object){
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntity = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntity.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel orderById = eatSleepRepeatResponseEntity.getBody();
        assertEquals(orderById.getId(), "2");
        assertEquals(orderById.getWhoOrdered(),"Trajko");
        assertEquals(orderById.getOrderDesc(), "hamburger i koka kola");
        assertEquals(orderById.getWhereToBuyTheOrder(), "sedmica");
    }

    @Test(priority = 4, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldUpdateOrder(JSONObject object) throws IOException {
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntity = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntity.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel postedOrder = eatSleepRepeatResponseEntity.getBody();
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatReturnTypeModelResponseEntity = eatSleepRepeatService.updateOrder(jsonObj,postedOrder.getId());
        EatSleepRepeatReturnTypeModel finalOrder = eatSleepRepeatReturnTypeModelResponseEntity.getBody();
       assertTrue(eatSleepRepeatReturnTypeModelResponseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(finalOrder.getId(),"2");
        assertEquals(finalOrder.getWhoOrdered(), "Trajko");
        assertEquals(finalOrder.getOrderDesc(),"leskovacka so pomfrit");
        assertEquals(finalOrder.getWhereToBuyTheOrder(),"Vili");
    }

    @Test(priority = 5, dataProvider = "postOrderDataProvider", dataProviderClass = OrderDataProvider.class)
    public void shouldDeleteOrder(JSONObject object){
        ResponseEntity<EatSleepRepeatReturnTypeModel> eatSleepRepeatResponseEntity = eatSleepRepeatService.postOrder(object);
        assertTrue(eatSleepRepeatResponseEntity.getStatusCode().equals(HttpStatus.OK));
        EatSleepRepeatReturnTypeModel orderBody = eatSleepRepeatResponseEntity.getBody();
        ResponseEntity<String> eatSleepRepeatResponseEntityDelete = eatSleepRepeatService.deleteOrder(orderBody.getId());
        assertTrue(eatSleepRepeatResponseEntityDelete.getStatusCode().equals(HttpStatus.OK));
    }

}
