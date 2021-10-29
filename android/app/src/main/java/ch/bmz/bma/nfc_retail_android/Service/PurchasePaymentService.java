package ch.bmz.bma.nfc_retail_android.Service;

import static ch.bmz.bma.nfc_retail_android.Service.UserService.currentUser;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Activities.PaymentConfirmActivity;
import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.Model.ArticlePurchaseRequest;
import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.Model.PaymentRequest;
import ch.bmz.bma.nfc_retail_android.Model.Purchase;
import ch.bmz.bma.nfc_retail_android.Model.PurchaseRequest;
import ch.bmz.bma.nfc_retail_android.Model.User;

public class PurchasePaymentService {
    public static Purchase currentPurchase;
    public static PaymentMethod currentPaymentMethod;
    public static Float currentTotal;

    public static void postPurchase(PaymentConfirmActivity context) {
        String url = "http://bma.timonhueppi.ch:8080/purchases";
        Gson gson = new Gson();
        PurchaseRequest purchaseRequest = new PurchaseRequest(null, currentUser.getId(), null);
        String requestBodyStr = gson.toJson(purchaseRequest);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PurchaseRequest purchase = gson.fromJson(response, PurchaseRequest.class);
                currentPurchase.setId(purchase.getId());
                postPayment(context);
                for (Map.Entry<Article, Integer> item : currentPurchase.getItems().entrySet()) {
                    String requestBodyStrItem = gson.toJson(new ArticlePurchaseRequest(item.getValue(), purchase.getId(), item.getKey().getId()));

                    String url = "http://bma.timonhueppi.ch:8080/purchases/" + purchase.getId() + "/items";
                    WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //nix nada
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
                            return requestBodyStrItem == null ? null : requestBodyStrItem.getBytes(StandardCharsets.UTF_8);
                        }
                    }, context);

                }
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebProvider.addAuthHeader();
            }
        }, context);
    }

    public static void postPayment(PaymentConfirmActivity context) {
        String url = "http://bma.timonhueppi.ch:8080/payments";
        Gson gson = new Gson();

        //https://stackoverflow.com/a/2400981
        Date dt = new Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        PaymentRequest paymentRequest = new PaymentRequest(null, currentTotal, "CHF", true, currentTime, currentPurchase.getId(), currentPaymentMethod.getId());
        String requestBodyStr = gson.toJson(paymentRequest);
        WebProvider.doRequest(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //PaymentRequest payment = gson.fromJson(response, PaymentRequest.class);
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebProvider.addAuthHeader();
            }
        }, context);
    }
}
