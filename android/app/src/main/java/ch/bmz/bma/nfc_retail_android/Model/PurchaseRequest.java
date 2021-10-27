package ch.bmz.bma.nfc_retail_android.Model;

import java.util.List;

public class PurchaseRequest {
    String id;
    String user_id;
    List<?> items;

    public PurchaseRequest(String id, String user_id, List<?> items) {
        this.id = id;
        this.user_id = user_id;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
