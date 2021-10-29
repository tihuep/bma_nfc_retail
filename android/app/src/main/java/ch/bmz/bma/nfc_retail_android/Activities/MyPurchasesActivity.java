package ch.bmz.bma.nfc_retail_android.Activities;

import static ch.bmz.bma.nfc_retail_android.Service.UserService.currentUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.bmz.bma.nfc_retail_android.Model.Purchase;
import ch.bmz.bma.nfc_retail_android.Model.User;
import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.Service.PurchasePaymentService;
import ch.bmz.bma.nfc_retail_android.Service.PurchaseService;

public class MyPurchasesActivity extends AppCompatActivity {

    Button myPurchasesBack;
    TextView myPurchasesTitle;
    LinearLayout myPurchasesError;
    TextView myPurchasesErrorLabel;
    ScrollView myPurchasesScrollView;
    LinearLayout myPurchasesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);

        myPurchasesBack = findViewById(R.id.myPurchasesBack);
        myPurchasesTitle = findViewById(R.id.myPurchasesTitle);
        myPurchasesError = findViewById(R.id.myPurchasesError);
        myPurchasesErrorLabel = findViewById(R.id.myPurchaseErrorLabel);
        myPurchasesScrollView = findViewById(R.id.myPurchasesScrollView);
        myPurchasesItems = findViewById(R.id.myPurchasesItems);

        defineButtonHandlers();

        hideError();

        PurchaseService.getPurchasesOfUser(this);
    }

    private void defineButtonHandlers() {
        myPurchasesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addItem(String id, String date, String currency, Float total) {
        //https://stackoverflow.com/questions/9807650/dynamically-cloning-a-linearlayout-in-android
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View item =  layoutInflater.inflate(R.layout.purchase_list_item_selectable, null);

        TextView purchaseItemDate = item.findViewById(R.id.purchaseItemDate);
        purchaseItemDate.setText(date);

        TextView purchaseItemTotal = item.findViewById(R.id.purchaseItemTotal);
        purchaseItemTotal.setText(currency + total.toString());

        item.setContentDescription(id);

        ImageButton purchaseItemSelect = item.findViewById(R.id.purchaseItemSelect);
        MyPurchasesActivity that = this;
        purchaseItemSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseService.myCurrentPurchase = new Purchase(id, currentUser, null);
                PurchaseService.myCurrentPurchasDate = date;
                PurchaseService.getItemsOfPurchase(that, id);
            }
        });

        myPurchasesItems.addView(item);
    }

    public void removeItem(String id) {
        //https://www.tabnine.com/code/java/methods/android.view.View/findViewsWithText
        ArrayList<View> results = new ArrayList<>();
        myPurchasesItems.findViewsWithText(results, id, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        if (results.size() > 0) {
            for (View view :
                    results) {
                myPurchasesItems.removeView(view);
            }
        }
    }

    public void displayError(String message) {
        myPurchasesError.setVisibility(View.VISIBLE);
        myPurchasesErrorLabel.setText(message);
    }

    public void hideError() {
        myPurchasesError.setVisibility(View.GONE);
        myPurchasesErrorLabel.setText("");
    }
}