package ch.bmz.bma.nfc_retail_android.Service;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import ch.bmz.bma.nfc_retail_android.Activities.PurchaseActivity;
import ch.bmz.bma.nfc_retail_android.Activities.ScanActivity;
import ch.bmz.bma.nfc_retail_android.Model.Article;

public class ArticleService {
    public static void getArticleForPurchase(PurchaseActivity context, Article articleNeeded) {
        String url = "http://bma.timonhueppi.ch:8080/articles/" + articleNeeded.getId();
        WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
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

    public static void getArticleForScan(ScanActivity context, Article articleNeeded) {
        String url = "http://bma.timonhueppi.ch:8080/articles/" + articleNeeded.getId();
        WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Article article = gson.fromJson(response, Article.class);

                context.setScanArticleID(article.getDescription());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }), context);
    }
}
