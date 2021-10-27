package ch.bmz.bma.nfc_retail_android.Service;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebProvider {
    public static void doRequest(StringRequest stringRequest, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}
