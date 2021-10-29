package ch.bmz.bma.nfc_retail_android.Service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import ch.bmz.bma.nfc_retail_android.Activities.ChangePWActivity;
import ch.bmz.bma.nfc_retail_android.Activities.LoginActivity;
import ch.bmz.bma.nfc_retail_android.Activities.PurchaseActivity;
import ch.bmz.bma.nfc_retail_android.Activities.RegisterActivity;
import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.Model.LoginRequest;
import ch.bmz.bma.nfc_retail_android.Model.User;
import ch.bmz.bma.nfc_retail_android.Model.UserRequest;
import ch.bmz.bma.nfc_retail_android.R;

public class UserService {
    public static User currentUser = null;
    public static String currentUserToken = null;

    public static void putUserToSP(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key_user), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", gson.toJson(currentUser));
        editor.putString("token", currentUserToken);
        editor.apply();
    }

    public static void removeUserFromSP(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key_user), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user");
        editor.remove("token");
        editor.apply();
    }

    public static void getUserFromSP(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key_user), Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("user", null) == null ? null : gson.fromJson(sharedPreferences.getString("user", null), User.class);
        currentUserToken = sharedPreferences.getString("token", null);
    }

    public static void register(RegisterActivity context, User user) {
        String url = "http://bma.timonhueppi.ch:8080/register";
        Gson gson = new Gson();
        UserRequest JsonUser = new UserRequest(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
        String requestBodyStr = gson.toJson(JsonUser);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LoginRequest loginRequest = gson.fromJson(response, LoginRequest.class);
                UserRequest user = loginRequest.getUser();
                currentUser = new User(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
                currentUserToken = loginRequest.getToken();
                putUserToSP(context);

                context.hideError();
                context.setResult(context.RESULT_OK, new Intent());
                Intent intent = new Intent(context, PurchaseActivity.class);
                context.startActivity(intent);
                context.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
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

    public static void login(LoginActivity context, String email, String password) {
        String url = "http://bma.timonhueppi.ch:8080/login";
        Gson gson = new Gson();
        UserRequest JsonUser = new UserRequest(null, null, null, email, password);
        String requestBodyStr = gson.toJson(JsonUser);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LoginRequest loginRequest = gson.fromJson(response, LoginRequest.class);
                UserRequest user = loginRequest.getUser();
                currentUser = new User(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
                currentUserToken = loginRequest.getToken();
                putUserToSP(context);

                Intent intent = new Intent(context, PurchaseActivity.class);
                context.startActivity(intent);
                context.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
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

    public static void logout(Activity context) {
        currentUser = null;
        currentUserToken = null;
        removeUserFromSP(context);
    }

    public static void changePW(ChangePWActivity context, String newPW){
        String url = "http://bma.timonhueppi.ch:8080/users/" + currentUser.getId();
        Gson gson = new Gson();
        UserRequest JsonUser = new UserRequest(currentUser.getId(), currentUser.getFirstname(), currentUser.getLastname(), currentUser.getEmail(), newPW);
        String requestBodyStr = gson.toJson(JsonUser);
        WebProvider.doRequest(new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserRequest userRequest = gson.fromJson(response, UserRequest.class);
                currentUser.setPassword(userRequest.getPassword());
                putUserToSP(context);
                context.hideError();
                context.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebProvider.addAuthHeader();
            }
        }, context);
    }
}
