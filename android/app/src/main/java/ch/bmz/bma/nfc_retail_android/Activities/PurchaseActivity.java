package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.bmz.bma.nfc_retail_android.R;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
                //TODO
                /*Intent intent = new Intent(this, ScanActivity.class);
                startActivity(intent);*/
            }
        });

        purchasePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                /*Intent intent = new Intent(this, PayActivity.class);
                startActivity(intent);*/
            }
        });
        purchaseProfileSpinner.setOnItemSelectedListener(this);

    }

    //https://developer.android.com/guide/topics/ui/controls/spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (pos) {
            case 1:
                Intent intent = new Intent(this, ChangePWActivity.class);
                startActivityForResult(intent, 1);
                purchaseProfileSpinner.setSelection(0);
                break;
            case 2:
                //TODO
                /*Intent intent = new Intent(this, MyPurchasesActivity.class);
                startActivity(intent);*/
                purchaseProfileSpinner.setSelection(0);
                break;
            case 3:
                //TODO
                /*Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();*/
                purchaseProfileSpinner.setSelection(0);
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void populateSpinner() {
        //https://stackoverflow.com/questions/5178588/how-we-can-get-the-arraylistboth-string-and-integer-from-the-resources-xml
        List<String> profileOptions = Arrays.asList(getResources().getStringArray(R.array.profile_options_array));

        //https://android--code.blogspot.com/2015/08/android-spinner-disable-item.html
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.profile_spinner_item, profileOptions){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                if(position == 0)
                    view.setVisibility(View.GONE);
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.profile_spinner_item);
        purchaseProfileSpinner.setAdapter(spinnerArrayAdapter);
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