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
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.Service.PurchaseService;

public class ShowPurchaseActivity extends AppCompatActivity {

    Button showPurchaseBack;
    TextView showPurchaseTitle;
    TextView showPurchaseDate;
    ScrollView showPurchaseScrollView;
    LinearLayout showPurchaseItems;
    TextView showPurchaseTotal;

    Float total = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_purchase);

        showPurchaseBack = findViewById(R.id.showPurchaseBack);
        showPurchaseTitle = findViewById(R.id.showPurchaseTitle);
        showPurchaseDate = findViewById(R.id.showPurchaseDate);
        showPurchaseScrollView = findViewById(R.id.showPurchaseScrollView);
        showPurchaseItems = findViewById(R.id.showPurchaseItems);
        showPurchaseTotal = findViewById(R.id.showPurchaseTotal);

        showPurchaseDate.setText(PurchaseService.myCurrentPurchasDate);

        setTotal();

        defineButtonHandlers();

        if (PurchaseService.myCurrentPurchase.getItems() != null) {
            for (Map.Entry<Article, Integer> item : PurchaseService.myCurrentPurchase.getItems().entrySet()) {
                addItem(item.getKey().getId(), item.getValue(), item.getKey().getDescription(), item.getKey().getPrice());
            }
        }
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
        articleItemAmount.setText(amount.toString() + "* ");

        TextView articleItemDesc = item.findViewById(R.id.articleItemDesc);
        articleItemDesc.setText(desc);

        TextView articleItemPrice = item.findViewById(R.id.articleItemPrice);
        Float multipliedPrice = price * amount;
        articleItemPrice.setText(getResources().getString(R.string.purchase_chf) + multipliedPrice.toString());

        showPurchaseItems.addView(item);
        total += multipliedPrice;
        setTotal();
    }

    public void setTotal() {
        showPurchaseTotal.setText(getResources().getString(R.string.show_purchase_total_label) + total.toString());
    }
}