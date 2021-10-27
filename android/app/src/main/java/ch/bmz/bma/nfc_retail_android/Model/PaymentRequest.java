package ch.bmz.bma.nfc_retail_android.Model;


import java.util.Date;

public class PaymentRequest {
    String id;
    Float total;
    String currency;
    Boolean confirmed;
    String confirmation_date;
    String purchase_id;
    String payment_method_id;

    public PaymentRequest(String id, Float total, String currency, Boolean confirmed, String confirmation_date, String purchase_id, String payment_method_id) {
        this.id = id;
        this.total = total;
        this.currency = currency;
        this.confirmed = confirmed;
        this.confirmation_date = confirmation_date;
        this.purchase_id = purchase_id;
        this.payment_method_id = payment_method_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getConfirmation_date() {
        return confirmation_date;
    }

    public void setConfirmation_date(String confirmation_date) {
        this.confirmation_date = confirmation_date;
    }

    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
