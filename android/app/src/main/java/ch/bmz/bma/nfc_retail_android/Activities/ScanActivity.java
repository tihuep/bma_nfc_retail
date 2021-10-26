package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import ch.bmz.bma.nfc_retail_android.R;

public class ScanActivity extends AppCompatActivity {

    Button scanBack;
    TextView scanTitle;
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

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    Tag myTag;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanBack = findViewById(R.id.scanBack);
        scanTitle = findViewById(R.id.scanTitle);
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

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(context, this.getResources().getString(R.string.no_nfc), Toast.LENGTH_LONG).show();
            finish();
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(context, this.getResources().getString(R.string.activate_nfc), Toast.LENGTH_LONG).show();
            finish();
        }
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
        IntentFilter tagDetected = new IntentFilter((NfcAdapter.ACTION_TAG_DISCOVERED));
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters = new IntentFilter[]{tagDetected};
    }

    private void defineButtonHandlers() {
        ScanActivity that = this;
        scanBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
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

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++){
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    private void buildTagViews(NdefMessage[] msgs){
        if (msgs == null || msgs.length == 0) return;

        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        try {
            articleID = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
            articleAmount = 1;
            scanArticleID.setText(articleID);
            scanAmount.setText(articleAmount.toString());

        }catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
    }

    @Override
    protected  void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
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