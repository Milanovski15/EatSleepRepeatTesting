package data;

import org.json.JSONObject;
import org.testng.annotations.DataProvider;

public class OrderDataProvider {
    @DataProvider(name = "postOrderDataProvider")
    public static Object[][] postObject() {
        final JSONObject postModel = new JSONObject();
        postModel.put("id", "2");
        postModel.put("whoOrdered", "Trajko");
        postModel.put("orderDesc", "hamburger i koka kola");
        postModel.put("whereToBuyTheOrder", "sedmica");
        final Object[][] returnObject = new Object[][]{{postModel}};
        return returnObject;
    }
}

