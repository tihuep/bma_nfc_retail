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

        addItem("01", 1, "evian", "1.69");
        addItem("02", 1, "airwaves", "2.10");
        addItem("03", 1, "fishermans friend", "4.20");
        addItem("04", 1, "evian", "1.69");
        addItem("05", 1, "evian", "1.69");
        addItem("06", 1, "evian", "1.69");
        addItem("07", 1, "evian", "1.69");
        addItem("08", 1, "evian", "1.69");
        addItem("09", 1, "evian", "1.69");
        addItem("10", 1, "evian", "1.69");
        addItem("11", 1, "evian", "1.69");
        addItem("12", 1, "evian", "1.69");
        addItem("13", 1, "evian", "1.69");
        addItem("14", 1, "evian", "1.69");
        addItem("15", 1, "evian", "1.69");
        addItem("16", 1, "evian", "1.69");
        addItem("17", 1, "evian", "1.69");
        addItem("18", 1, "evian", "1.69");
        addItem("19", 1, "evian", "1.69");
        addItem("20", 1, "evian", "1.69");
        addItem("21", 1, "evian", "1.69");
        addItem("22", 1, "evian", "1.69");
        addItem("23", 1, "evian", "1.69");
        addItem("24", 1, "evian", "1.69");
        addItem("25", 1, "evian", "1.69");
        addItem("26", 1, "evian", "1.69");
        addItem("27", 1, "evian", "1.69");
        addItem("28", 1, "evian", "1.69");
        addItem("29", 1, "evian", "1.69");
        addItem("30", 1, "evian", "1.69");
        addItem("31", 1, "evian", "1.69");
        addItem("32", 1, "evian", "1.69");
        addItem("33", 1, "evian", "1.69");
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

    public void addItem(String id, Integer amount, String desc, String price) {
        //https://stackoverflow.com/questions/9807650/dynamically-cloning-a-linearlayout-in-android
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View item =  layoutInflater.inflate(R.layout.article_list_item_deletable, null);

        TextView articleItemAmount = item.findViewById(R.id.articleItemAmount);
        articleItemAmount.setText(amount.toString() + " ");

        TextView articleItemDesc = item.findViewById(R.id.articleItemDesc);
        articleItemDesc.setText(desc);

        TextView articleItemPrice = item.findViewById(R.id.articleItemPrice);
        articleItemPrice.setText(price);

        item.setContentDescription(id);

        ImageButton articleItemDelete = item.findViewById(R.id.articleItemDelete);
        articleItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(id);
            }
        });

        purchaseItems.addView(item);
    }

    public void removeItem(String id) {
        //https://www.tabnine.com/code/java/methods/android.view.View/findViewsWithText
        ArrayList<View> results = new ArrayList<>();
        purchaseItems.findViewsWithText(results, id, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        if (results.size() > 0) {
            for (View view :
                    results) {
                purchaseItems.removeView(view);
            }
        }
    }
}