package ch.bmz.bma.nfc_retail_android.Activities;

import static ch.bmz.bma.nfc_retail_android.Service.UserService.currentUser;
import static ch.bmz.bma.nfc_retail_android.Service.UserService.currentUserToken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.Model.Article;
import ch.bmz.bma.nfc_retail_android.Model.Purchase;
import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.Service.ArticleService;
import ch.bmz.bma.nfc_retail_android.Service.PurchasePaymentService;
import ch.bmz.bma.nfc_retail_android.Service.PurchaseService;
import ch.bmz.bma.nfc_retail_android.Service.UserService;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView purchaseTitle;
    Spinner purchaseProfileSpinner;
    ScrollView purchaseItemsScrollView;
    LinearLayout purchaseItems;
    TextView purchaseTotal;
    Button purchasePay;
    LinearLayout purchaseError;
    TextView purchaseErrorLabel;

    Map<Article, Integer> items = new HashMap<>();
    Float total = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        purchaseTitle = findViewById(R.id.purchaseTitle);
        purchaseProfileSpinner = findViewById(R.id.purchaseProfileSpinner);
        purchaseItemsScrollView = findViewById(R.id.purchaseItemsScrollView);
        purchaseItems = findViewById(R.id.purchaseItems);
        purchaseTotal = findViewById(R.id.purchaseTotal);
        purchasePay = findViewById(R.id.purchasePay);
        purchaseError = findViewById(R.id.purchaseError);
        purchaseErrorLabel = findViewById(R.id.purchaseErrorLabel);

        defineButtonHandlers();
        populateSpinner();

        setTotal();

        hideError();

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key_purchase_items), Context.MODE_PRIVATE);
        Map<String, ?> items = sharedPreferences.getAll();
        for (Map.Entry<String, ?> item : items.entrySet()) {
            ArticleService.getArticleForPurchase(this, new Article(item.getKey(), null, null, null));
        }
    }

    private void defineButtonHandlers() {
        PurchaseActivity that = this;

        purchasePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size() > 0) {
                    hideError();
                    Intent intent = new Intent(that, PaymentActivity.class);
                    startActivity(intent);

                    PurchasePaymentService.currentPurchase = new Purchase(null, currentUser, that.items);
                    PurchasePaymentService.currentTotal = total;
                }else{
                    displayError(getString(R.string.no_purchase_items));
                }

            }
        });
        purchaseProfileSpinner.setOnItemSelectedListener(this);
    }

    //https://developer.android.com/guide/topics/ui/controls/spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Intent intent;
        switch (pos) {
            case 1:
                intent = new Intent(this, ChangePWActivity.class);
                startActivity(intent);
                purchaseProfileSpinner.setSelection(0);
                break;
            case 2:
                intent = new Intent(this, MyPurchasesActivity.class);
                startActivity(intent);
                purchaseProfileSpinner.setSelection(0);
                break;
            case 3:
                UserService.logout(this);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
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

    public void populateList(Article article) {
        if (article != null) {
            SharedPreferences sharedPreferences = getSharedPreferences(
                    getString(R.string.preference_file_key_purchase_items), Context.MODE_PRIVATE);
            Map<String, ?> items = sharedPreferences.getAll();

            for (Map.Entry<String, ?> item : items.entrySet()) {
                if (item.getKey().equals(article.getId())) {
                    addItem(article, (Integer) item.getValue());
                }
            }
        }

    }

    public void addItem(Article article, Integer amount) {
        //https://stackoverflow.com/questions/9807650/dynamically-cloning-a-linearlayout-in-android
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View item =  layoutInflater.inflate(R.layout.article_list_item_deletable, null);

        TextView articleItemAmount = item.findViewById(R.id.articleItemDeletableAmount);
        articleItemAmount.setText(amount.toString() + "*  ");

        TextView articleItemDesc = item.findViewById(R.id.articleItemDeletableDesc);
        articleItemDesc.setText(article.getDescription());

        TextView articleItemPrice = item.findViewById(R.id.articleItemDeletablePrice);
        Float multipliedPrice = article.getPrice() * amount;
        articleItemPrice.setText(getResources().getString(R.string.purchase_chf) + multipliedPrice.toString());

        item.setContentDescription(article.getId());

        ImageButton articleItemDelete = item.findViewById(R.id.articleItemDeletableDelete);
        articleItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(article.getId());
            }
        });

        purchaseItems.addView(item);
        this.items.put(article, amount);
        this.total += multipliedPrice;
        setTotal();
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
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key_purchase_items), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(id);
        editor.apply();

        for (Map.Entry<Article, Integer> item : this.items.entrySet()) {
            if (item.getKey().getId().equals(id)) {
                this.total -= item.getKey().getPrice()*item.getValue();
                setTotal();
                this.items.remove(item.getKey());
            }
        }
    }

    public void setTotal(){
        purchaseTotal.setText(getResources().getString(R.string.purchase_total) + total.toString());
    }

    public void displayError(String message) {
        purchaseError.setVisibility(View.VISIBLE);
        purchaseErrorLabel.setText(message);
    }

    public void hideError() {
        purchaseError.setVisibility(View.GONE);
        purchaseErrorLabel.setText("");
    }
}