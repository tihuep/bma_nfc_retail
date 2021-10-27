package ch.bmz.bma.nfc_retail_android.service;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ch.bmz.bma.nfc_retail_android.Activities.PurchaseActivity;
import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.R;

public class ArticleService {
    public static void getArticleForPurchase(PurchaseActivity context, Article articleNeeded) {
        String url = "http://bma.timonhueppi.ch:8080/articles/" + articleNeeded.getId();
        WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                //https://stackoverflow.com/a/12384156
                Article article = gson.fromJson(response, Article.class);

                context.populateList(article);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }), context);
    }
}