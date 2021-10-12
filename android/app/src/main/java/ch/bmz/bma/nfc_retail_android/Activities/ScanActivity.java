package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.bmz.bma.nfc_retail_android.R;

public class ScanActivity extends AppCompatActivity {

    Button scanBack;
    TextView scanTitle;
    Button scanButton;
    TextView scanArticleTitle;
    TextView scanArticleID;
    TextView scanArticleDesc;
    TextView scanAmountLabel;
    TextView scanAmount;
    LinearLayout scanError;
    TextView scanErrorLabel;
    Button scanAdd;

    String articleID;
    Integer articleAmount;
    String articleDesc;
    Float articlePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanBack = findViewById(R.id.scanBack);
        scanTitle = findViewById(R.id.scanTitle);
        scanButton = findViewById(R.id.scanButton);
        scanArticleTitle = findViewById(R.id.scanArticleTitle);
        scanArticleID = findViewById(R.id.scanArticleID);
        scanArticleDesc = findViewById(R.id.scanArticleDesc);
        scanAmountLabel = findViewById(R.id.scanAmountLabel);
        scanAmount = findViewById(R.id.scanAmount);
        scanError = findViewById(R.id.scanError);
        scanErrorLabel = findViewById(R.id.scanErrorLabel);
        scanAdd = findViewById(R.id.scanAdd);

        scanError.setVisibility(View.GONE);

        defineButtonHandlers();
    }

    private void defineButtonHandlers() {
        scanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError();

                articleID = String.valueOf(Math.random());
                articleAmount = (int) (Math.random()*100);
                articleDesc = "moin moin";
                articlePrice = (float) Math.floor(Math.random()*10000)/100;

                scanArticleID.setText(articleID);
                scanArticleDesc.setText(articleDesc);
                scanAmount.setText(articleAmount.toString());

                //TODO: trigger NFC read
                //TODO: get article data from server
                //TODO: populate textviews with article data
            }
        });
        scanAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articleID != null && articleAmount != null && articleDesc != null && articlePrice != null
                        && !scanAmount.getText().toString().equals("") && Integer.valueOf(scanAmount.getText().toString()) > 0){
                    hideError();
                    //https://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
                    getIntent().putExtra("id", articleID);
                    getIntent().putExtra("amount", Integer.valueOf(scanAmount.getText().toString()));
                    getIntent().putExtra("desc", articleDesc);
                    getIntent().putExtra("price", articlePrice);

                    setResult(Activity.RESULT_OK, getIntent());
                    finish();
                }else {
                    displayError("Bitte zuerst Artikel scannen");
                }

            }
        });
    }

    public void displayError(String message) {
        scanError.setVisibility(View.VISIBLE);
        scanErrorLabel.setText(message);
    }

    public void hideError() {
        scanError.setVisibility(View.GONE);
        scanErrorLabel.setText("");
    }
}