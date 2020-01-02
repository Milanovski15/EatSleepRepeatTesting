package eatsleeprepeat.api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eatsleeprepeat.api.ApiDefinition;
import eatsleeprepeat.api.model.EatSleepRepeatReturnTypeModel;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;


public class EatSleepRepeatService extends ApiDefinition {

    private static final String URI_FOR_GETANDPOST = "/orders";
    private static final String URI_FOR_GETBYIDPUTDELETE = "/orders/{id}";
    private static Logger log = LoggerFactory.getLogger(EatSleepRepeatService.class);
    private HttpHeaders requestHeaders = new HttpHeaders();

    public EatSleepRepeatService() {
    }

    public ResponseEntity<EatSleepRepeatReturnTypeModel[]> getOrders() {
        HttpEntity<?> httpEntity = getHttpEntity();
        ResponseEntity<EatSleepRepeatReturnTypeModel[]> response;
        try {
            response = restTemplate.exchange(getBaseURI() + getPort() + URI_FOR_GETANDPOST, HttpMethod.GET, httpEntity, EatSleepRepeatReturnTypeModel[].class);
            return response;
        } catch (HttpClientErrorException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            log.info("Unhandled Exception! ");
        }
        return null;
    }

    public ResponseEntity<EatSleepRepeatReturnTypeModel> getOrderById(String id) {
        HttpEntity<?> httpEntity = getHttpEntity();
        ResponseEntity<EatSleepRepeatReturnTypeModel> firstReturnTypeModelResponseEntity;
        try {
            firstReturnTypeModelResponseEntity = restTemplate.exchange(getBaseURI() + getPort() + URI_FOR_GETBYIDPUTDELETE, HttpMethod.GET, httpEntity, EatSleepRepeatReturnTypeModel.class, id);
            return firstReturnTypeModelResponseEntity;
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(" any other kind of exc.");
        }
        return null;
    }

    public ResponseEntity<EatSleepRepeatReturnTypeModel> postOrder(JSONObject object) {
        HttpEntity<?> httpEntity = prepareHttpEntity(object);
        ResponseEntity<EatSleepRepeatReturnTypeModel> firstReturnTypeModelResponseEntity;
        try {
            firstReturnTypeModelResponseEntity = restTemplate.exchange(getBaseURI() + getPort() + URI_FOR_GETANDPOST, HttpMethod.POST, httpEntity, EatSleepRepeatReturnTypeModel.class);
            return firstReturnTypeModelResponseEntity;
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(" any other kind of exc.");
        }
        return null;
    }

    public ResponseEntity<EatSleepRepeatReturnTypeModel> updateOrder(JSONObject object, String id) throws IOException {
        HttpEntity<?> httpEntity = prepareHttpEntity(object);
        ResponseEntity<EatSleepRepeatReturnTypeModel> firstReturnTypeModelResponseEntity;
        try {
            firstReturnTypeModelResponseEntity = restTemplate.exchange(getBaseURI() + getPort() + URI_FOR_GETBYIDPUTDELETE, HttpMethod.PUT, httpEntity, EatSleepRepeatReturnTypeModel.class, id);
            return firstReturnTypeModelResponseEntity;
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(" any other kind of exc.");
        }
        return null;
    }

    public ResponseEntity<String> deleteOrder(String id) {
        HttpEntity<?> httpEntity = getHttpEntity();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(getBaseURI() + getPort() + URI_FOR_GETBYIDPUTDELETE, HttpMethod.DELETE, httpEntity, String.class, id);
            return response;
        } catch (HttpClientErrorException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            log.info("Unhandled Exception! ");
        }
        return null;
    }

    private HttpEntity<?> prepareHttpEntity(JSONObject object) {
        EatSleepRepeatReturnTypeModel eatSleepRepeatModel = parseJSONObjectToFirstModel(object);
        log.info("Http Entity is : " + eatSleepRepeatModel.toString());
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<EatSleepRepeatReturnTypeModel>(eatSleepRepeatModel, requestHeaders);
    }

    private EatSleepRepeatReturnTypeModel parseJSONObjectToFirstModel(JSONObject object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            EatSleepRepeatReturnTypeModel toReturn = objectMapper.readValue(object.toString(), EatSleepRepeatReturnTypeModel.class);
            log.info("Builded model is : " + toReturn.toString());
            return toReturn;
        } catch (IOException ex) {
            System.out.println("An error has occured" + ex);
            return null;
        }
    }
}
