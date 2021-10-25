package ch.bmz.bma.nfc_retail_android.Model;

public class Payment {
    String id;
    Integer amount;
    String currency;
    Boolean confirmed;

    public Payment(String id, Integer amount, String currency, Boolean confirmed) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.confirmed = confirmed;
        if (confirmed == null)
            this.confirmed = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
}
