package ch.bmz.bma.nfc_retail_android.Model;

public class Article {
    String id;
    String description;
    Float price;
    String image_url;

    public Article(String id, String description, Float price, String image_url) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }

    public Article(String id, String description, Float price) {
        this.id = id;
        this.description = description;
        this.price = price;
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
