package ch.bmz.bma.nfc_retail_android.Model;

public class ArticlePurchaseRequest {
    Integer amount;
    String purchase_id;
    String article_id;

    public ArticlePurchaseRequest(Integer amount, String purchase_id, String article_id) {
        this.amount = amount;
        this.purchase_id = purchase_id;
        this.article_id = article_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }
}
