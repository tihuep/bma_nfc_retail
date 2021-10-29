package ch.bmz.bma.nfc_retail_android.Service;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Activities.PaymentActivity;
import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.R;

public class PaymentMethodService {

    public static void getPaymentMethods(PaymentActivity context) {
        //https://developer.android.com/training/volley/simple#java
        String url = "http://bma.timonhueppi.ch:8080/payment_methods";
        WebProvider.doRequest(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                //https://stackoverflow.com/a/12384156
                ArrayList<PaymentMethod> paymentMethods = gson.fromJson(response, new TypeToken<List<PaymentMethod>>() {}.getType());
                for (PaymentMethod paymentMethod :
                        paymentMethods) {
                    context.options.add(paymentMethod);
                }
                context.populateRadioButtons();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.displayError(context.getString(R.string.internet_error) + ": " + error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebProvider.addAuthHeader();
            }
        }, context);
    }
}
