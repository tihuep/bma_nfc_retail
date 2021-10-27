package ch.bmz.bma.nfc_retail_android.Model;

public class ArticleRequest {
    String id;
    String description;
    Float price;
    String image_url;

    public ArticleRequest(String id, String description, Float price, String image_url) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
