package ch.bmz.bma.nfc_retail_android.Activities;

import static ch.bmz.bma.nfc_retail_android.Service.UserService.currentUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ch.bmz.bma.nfc_retail_android.Service.UserService;

/**
 * author: Timon Hueppi
 * date: 2021/10/11
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserService.getUserFromSP(this);
        Intent intent;
        if (currentUser == null) {
            intent = new Intent(this, LoginActivity.class);
        }else {
            intent = new Intent(this, PurchaseActivity.class);
        }
        startActivity(intent);
        finish();
    }
}