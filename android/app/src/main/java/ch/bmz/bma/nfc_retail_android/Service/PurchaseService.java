package ch.bmz.bma.nfc_retail_android.Service;

import static ch.bmz.bma.nfc_retail_android.Service.UserService.currentUser;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Activities.MyPurchasesActivity;
import ch.bmz.bma.nfc_retail_android.Activities.PaymentConfirmActivity;
import ch.bmz.bma.nfc_retail_android.Activities.ShowPurchaseActivity;
import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.Model.ArticlePurchaseRequest;
import ch.bmz.bma.nfc_retail_android.Model.ArticleRequest;
import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.Model.PaymentRequest;
import ch.bmz.bma.nfc_retail_android.Model.Purchase;
import ch.bmz.bma.nfc_retail_android.Model.PurchaseRequest;

public class PurchaseService {
    public static Purchase myCurrentPurchase;
    public static String myCurrentPurchasDate;
    public static void getPurchasesOfUser(MyPurchasesActivity context) {
        String url = "http://bma.timonhueppi.ch:8080/purchases/user/" + currentUser.getId();

        WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                //https://stackoverflow.com/a/12384156
                ArrayList<PurchaseRequest> purchases = gson.fromJson(response, new TypeToken<List<PurchaseRequest>>() {}.getType());
                for (PurchaseRequest purchase : purchases) {
                    String url = "http://bma.timonhueppi.ch:8080/payments/purchase/" + purchase.getId();
                    WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            GsonBuilder builder = new GsonBuilder();
                            builder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
                            Gson gson = builder.create();

                            //https://stackoverflow.com/a/12384156
                            PaymentRequest payment = gson.fromJson(response, PaymentRequest.class);
                            try {
                                Date confirmationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(payment.getConfirmation_date());
                                java.text.SimpleDateFormat sdf =
                                        new java.text.SimpleDateFormat("dd.MM.yyyy");
                                String currentTime = sdf.format(confirmationDate);
                                context.addItem(purchase.getId(), currentTime, payment.getCurrency(), payment.getTotal());
                            }catch (Exception e){
                                context.addItem(purchase.getId(), payment.getConfirmation_date(), payment.getCurrency(), payment.getTotal());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return WebProvider.addAuthHeader();
                        }
                    }, context);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebProvider.addAuthHeader();
            }
        }, context);
    }

    public static void getItemsOfPurchase(MyPurchasesActivity context, String purchaseId) {
        String url = "http://bma.timonhueppi.ch:8080/purchases/" + purchaseId + "/items";

        WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ArrayList<ArticlePurchaseRequest> items = gson.fromJson(response, new TypeToken<List<ArticlePurchaseRequest>>() {}.getType());
                for (ArticlePurchaseRequest item : items) {
                    Integer amount = item.getAmount();
                    String url = "http://bma.timonhueppi.ch:8080/articles/" + item.getArticle_id();

                    WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            ArticleRequest article = gson.fromJson(response, ArticleRequest.class);
                            myCurrentPurchase.addItem(new Article(article.getId(), article.getDescription(), article.getPrice(), article.getImage_url()), amount);
                            if (items.get(items.size()-1) == item) {
                                Intent intent = new Intent(context, ShowPurchaseActivity.class);
                                context.startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return WebProvider.addAuthHeader();
                        }
                    }, context);
                }
                if (items.size() == 0){
                    Intent intent = new Intent(context, ShowPurchaseActivity.class);
                    context.startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebProvider.addAuthHeader();
            }
        }, context);
    }
}
