package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * author: Timon Hueppi
 * date: 2021/10/11
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: implement activity switch
        //Intent intent = new Intent(this, LoginActivity.class);
        Intent intent = new Intent(this, PurchaseActivity.class);
        startActivity(intent);
        finish();
    }
}