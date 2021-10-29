package ch.bmz.bma.nfc_retail_android.Service;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Activities.LoginActivity;
import ch.bmz.bma.nfc_retail_android.Activities.RegisterActivity;
import ch.bmz.bma.nfc_retail_android.Model.User;
import ch.bmz.bma.nfc_retail_android.Model.UserRequest;

public class UserService {
    public static User currentUser;
    public static User testUser = new User("69", "Timon", "Hüppi", "timon.hueppi@gmail.com", "");

    public static void register(RegisterActivity context, User user) {
        String url = "http://bma.timonhueppi.ch:8080/register";
        Gson gson = new Gson();
        UserRequest JsonUser = new UserRequest(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
        String requestBodyStr = gson.toJson(JsonUser);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserRequest user = gson.fromJson(response, UserRequest.class);
                currentUser = new User(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }) {
            //https://stackoverflow.com/questions/48424033/android-volley-post-request-with-json-object-in-body-and-getting-response-in-str/48424181
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBodyStr == null ? null : requestBodyStr.getBytes(StandardCharsets.UTF_8);
            }

            //https://stackoverflow.com/a/46229554
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "username:password";
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        }, context);
    }

    public static void login(LoginActivity context, String email, String password) {
        String url = "http://bma.timonhueppi.ch:8080/login";
        Gson gson = new Gson();
        UserRequest JsonUser = new UserRequest(null, null, null, email, password);
        String requestBodyStr = gson.toJson(JsonUser);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserRequest user = gson.fromJson(response, UserRequest.class);
                currentUser = new User(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }) {
            //https://stackoverflow.com/questions/48424033/android-volley-post-request-with-json-object-in-body-and-getting-response-in-str/48424181
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBodyStr == null ? null : requestBodyStr.getBytes(StandardCharsets.UTF_8);
            }
        }, context);
    }
}
