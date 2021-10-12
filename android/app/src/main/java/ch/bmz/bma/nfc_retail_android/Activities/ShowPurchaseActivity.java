package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.bmz.bma.nfc_retail_android.R;

public class ShowPurchaseActivity extends AppCompatActivity {

    Button showPurchaseBack;
    TextView showPurchaseTitle;
    TextView showPurchaseDate;
    ScrollView showPurchaseScrollView;
    LinearLayout showPurchaseItems;
    TextView showPurchaseTotalLabel;
    TextView showPurchaseTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_purchase);

        showPurchaseBack = findViewById(R.id.showPurchaseBack);
        showPurchaseTitle = findViewById(R.id.showPurchaseTitle);
        showPurchaseDate = findViewById(R.id.showPurchaseDate);
        showPurchaseScrollView = findViewById(R.id.showPurchaseScrollView);
        showPurchaseItems = findViewById(R.id.showPurchaseItems);
        showPurchaseTotalLabel = findViewById(R.id.showPurchaseTotalLabel);
        showPurchaseTotal = findViewById(R.id.showPurchaseTotal);

        defineButtonHandlers();

        Bundle extras = getIntent().getExtras();
        showPurchaseDate.setText(extras.getString("date"));
        showPurchaseTotal.setText(extras.getString("currency") + " " + extras.getFloat("total"));

        addItem("1", 6, "fishermans friend eucalyptus", 3f);
        addItem("2", 5, "fishermans friend mint", 3.5f);
        addItem("3", 4, "fishermans friend cassis", 5f);
        addItem("4", 1, "fishermans friend", 4f);
        addItem("5", 2, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
        addItem("6", 1, "fishermans friend", 3f);
    }

    private void defineButtonHandlers() {
        showPurchaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addItem(String id, Integer amount, String desc, Float price) {
        //https://stackoverflow.com/questions/9807650/dynamically-cloning-a-linearlayout-in-android
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View item =  layoutInflater.inflate(R.layout.article_list_item, null);

        TextView articleItemAmount = item.findViewById(R.id.articleItemAmount);
        articleItemAmount.setText(amount.toString() + " ");

        TextView articleItemDesc = item.findViewById(R.id.articleItemDesc);
        articleItemDesc.setText(desc);

        TextView articleItemPrice = item.findViewById(R.id.articleItemPrice);
        articleItemPrice.setText("CHF " + price.toString());

        showPurchaseItems.addView(item);
    }
}