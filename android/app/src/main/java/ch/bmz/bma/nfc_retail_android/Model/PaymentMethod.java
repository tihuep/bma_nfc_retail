package ch.bmz.bma.nfc_retail_android.Model;

public class PaymentMethod {
    String id;
    String name;

    public PaymentMethod(String id, String method) {
        this.id = id;
        this.name = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
