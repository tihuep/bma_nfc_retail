package ch.bmz.bma.nfc_retail_android.Service;

import android.content.Context;
import android.util.Base64;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WebProvider {
    public static void doRequest(StringRequest stringRequest, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public static Map<String, String> addAuthHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + UserService.currentUserToken);
        return headers;
    }
}
