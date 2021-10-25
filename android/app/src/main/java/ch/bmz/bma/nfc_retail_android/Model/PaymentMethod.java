package ch.bmz.bma.nfc_retail_android.Model;

public class PaymentMethod {
    String id;
    String method;

    public PaymentMethod(String id, String method) {
        this.id = id;
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
