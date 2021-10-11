package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ch.bmz.bma.nfc_retail_android.R;

public class PurchaseActivity extends AppCompatActivity {

    TextView purchaseTitle;
    Spinner purchaseProfileSpinner;
    ScrollView purchaseItemsScrollView;
    LinearLayout purchaseItems;
    ImageButton purchaseAdd;
    Button purchasePay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        purchaseTitle = findViewById(R.id.purchaseTitle);
        purchaseProfileSpinner = findViewById(R.id.purchaseProfileSpinner);
        purchaseItemsScrollView = findViewById(R.id.purchaseItemsScrollView);
        purchaseItems = findViewById(R.id.purchaseItems);
        purchaseAdd = findViewById(R.id.purchaseAdd);
        purchasePay = findViewById(R.id.purchasePay);

        defineButtonHandlers();
        populateSpinner();
    }

    private void defineButtonHandlers() {
        purchaseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);*/
            }
        });

        purchasePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(this, PayActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void populateSpinner() {
        //https://developer.android.com/guide/topics/ui/controls/spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profile_options_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        purchaseProfileSpinner.setAdapter(adapter);
    }
}