package ch.bmz.bma.nfc_retail_android.Model;

public class LoginRequest {
    UserRequest user;
    String token;

    public LoginRequest(UserRequest user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
