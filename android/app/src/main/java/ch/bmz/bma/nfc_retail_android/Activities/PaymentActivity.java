package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ch.bmz.bma.nfc_retail_android.R;

public class PaymentActivity extends AppCompatActivity {

    Button paymentBack;
    TextView paymentTitle;
    RadioGroup paymentOptions;
    LinearLayout paymentError;
    TextView paymentErrorLabel;
    Button paymentButton;

    ArrayList<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentBack = findViewById(R.id.paymentBack);
        paymentTitle = findViewById(R.id.paymentTitle);
        paymentOptions = findViewById(R.id.paymentOptions);
        paymentError = findViewById(R.id.paymentError);
        paymentErrorLabel = findViewById(R.id.paymentErrorLabel);
        paymentButton = findViewById(R.id.paymentButton);

        paymentError.setVisibility(View.GONE);

        options = new ArrayList<>();
        options.add("Twint");
        options.add("PayPal");
        options.add("Kreditkarte");

        defineButtonHandlers();

        populateRadioButtons();
    }

    private void defineButtonHandlers() {
        PaymentActivity that = this;
        paymentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = paymentOptions.getCheckedRadioButtonId();
                if (checkedId > 0) {
                    hideError();
                    Intent intent = new Intent(that, PaymentConfirmActivity.class);
                    int optionID = (checkedId-1) % options.size();
                    if (options.size() > optionID) {
                        System.out.println(options.get(optionID));
                        intent.putExtra("method", options.get(optionID));
                    }
                    //https://stackoverflow.com/questions/7075349/android-clear-activity-stack
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    displayError("Bitte Zahlmethode auswählen");
                }
            }
        });
    }

    private void populateRadioButtons() {
        for (String option : options) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            View item =  layoutInflater.inflate(R.layout.payment_option, null);

            RadioButton radioButton = (RadioButton) item;

            radioButton.setText(option);

            paymentOptions.addView(item);
        }
    }

    public void displayError(String message) {
        paymentError.setVisibility(View.VISIBLE);
        paymentErrorLabel.setText(message);
    }

    public void hideError() {
        paymentError.setVisibility(View.GONE);
        paymentErrorLabel.setText("");
    }
}