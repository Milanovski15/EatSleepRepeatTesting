package eatsleeprepeat.api.model;

public class EatSleepRepeatModel {
    private String id, whoOrdered, orderDesc, whereToBuyTheOrder;

    public EatSleepRepeatModel(){
    };

    public EatSleepRepeatModel(String id, String whoOrdered, String orderDesc, String whereToBuyTheOrder) {
        super();
        this.id = id;
        this.whoOrdered = whoOrdered;
        this.orderDesc = orderDesc;
        this.whereToBuyTheOrder = whereToBuyTheOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWhoOrdered() {
        return whoOrdered;
    }

    public void setWhoOrdered(String whoOrdered) {
        this.whoOrdered = whoOrdered;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getWhereToBuyTheOrder() {
        return whereToBuyTheOrder;
    }

    public void setWhereToBuyTheOrder(String whereToBuyTheOrder) {
        this.whereToBuyTheOrder = whereToBuyTheOrder;
    }
}
