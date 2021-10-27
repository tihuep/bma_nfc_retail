package ch.bmz.bma.nfc_retail_android.Service;

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

import ch.bmz.bma.nfc_retail_android.Activities.MyPurchasesActivity;
import ch.bmz.bma.nfc_retail_android.Activities.PaymentConfirmActivity;
import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.Model.PaymentRequest;
import ch.bmz.bma.nfc_retail_android.Model.Purchase;
import ch.bmz.bma.nfc_retail_android.Model.PurchaseRequest;

public class PurchaseService {
    public static void getPurchasesOfUser(MyPurchasesActivity context) {
        String url = "http://bma.timonhueppi.ch:8080/purchases/user/" + PurchasePaymentService.testUser.getId();

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
                                context.addItem(payment.getId(), currentTime, payment.getCurrency(), payment.getTotal());
                            }catch (Exception e){
                                context.addItem(payment.getId(), payment.getConfirmation_date(), payment.getCurrency(), payment.getTotal());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
                        }
                    }), context);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }), context);
    }
}
