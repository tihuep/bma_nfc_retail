package ch.bmz.bma.nfc_retail_android.Model;

import java.util.Map;

public class Purchase {
    String id;
    User user;
    Map<Article, Integer> items;
    PaymentMethod paymentMethod;
    Payment payment;

    public Purchase(String id, User user, Map<Article, Integer> items, PaymentMethod paymentMethod, Payment payment) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.payment = payment;
    }

    public Purchase(User user, Map<Article, Integer> items, PaymentMethod paymentMethod, Payment payment) {
        this.user = user;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Article, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Article, Integer> items) {
        this.items = items;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void addItem(Article item, Integer amount){
        if (item != null && amount > 0){
            items.put(item, amount);
        }
    }

    public void removeItem(Article item, Integer amount){
        Integer currentAmount = items.get(item);
        if (item != null && currentAmount != null && amount > 0) {
            Integer newAmount = currentAmount - amount;
            if (newAmount <= 0){
                items.remove(item);
            }else{
                items.put(item, newAmount);
            }
        }
    }
}
