package ch.bmz.bma.nfc_retail_android.service;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Activities.PaymentActivity;
import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.Model.ArticlePurchaseRequest;
import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.Model.Purchase;
import ch.bmz.bma.nfc_retail_android.Model.PurchaseRequest;
import ch.bmz.bma.nfc_retail_android.Model.User;
import ch.bmz.bma.nfc_retail_android.R;

public class PurchasePaymentService {
    public static Purchase currentPurchase;
    public static PaymentMethod currentPaymentMethod;
    public static User testUser = new User("69", "Timon", "Hüppi", "timon.hueppi@gmail.com", "");

    public static void postPurchase(PaymentActivity context) {
        String url = "http://bma.timonhueppi.ch:8080/purchases";
        Gson gson = new Gson();
        PurchaseRequest purchaseRequest = new PurchaseRequest(null, testUser.getId(), null);
        String requestBodyStr = gson.toJson(purchaseRequest);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PurchaseRequest purchase = gson.fromJson(response, PurchaseRequest.class);
                for (Map.Entry<Article, Integer> item : currentPurchase.getItems().entrySet()) {
                    String requestBodyStrItem = gson.toJson(new ArticlePurchaseRequest(item.getValue(), purchase.getId(), item.getKey().getId()));

                    String url = "http://bma.timonhueppi.ch:8080/purchases/" + purchase.getId() + "/items";
                    WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            Object object = gson.fromJson(response, new TypeToken<List<PaymentMethod>>() {}.getType());
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
                            return requestBodyStrItem == null ? null : requestBodyStrItem.getBytes(StandardCharsets.UTF_8);
                        }
                    }, context);

                }
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

    public static void postPayment(PaymentActivity context) {
        String url = "http://bma.timonhueppi.ch:8080/purchases";
        Gson gson = new Gson();
        PurchaseRequest purchaseRequest = new PurchaseRequest(null, testUser.getId(), null);
        String requestBodyStr = gson.toJson(purchaseRequest);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PurchaseRequest purchase = gson.fromJson(response, PurchaseRequest.class);

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
}
