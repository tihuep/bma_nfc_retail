package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ch.bmz.bma.nfc_retail_android.Model.PaymentMethod;
import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.service.PaymentMethodService;
import ch.bmz.bma.nfc_retail_android.service.PurchasePaymentService;

public class PaymentActivity extends AppCompatActivity {

    Button paymentBack;
    TextView paymentTitle;
    RadioGroup paymentOptions;
    LinearLayout paymentError;
    TextView paymentErrorLabel;
    Button paymentButton;

    public ArrayList<PaymentMethod> options = new ArrayList<>();;

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

        defineButtonHandlers();

        PaymentMethodService.getPaymentMethods(this);
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
                        PurchasePaymentService.currentPaymentMethod = options.get(optionID);

                        //https://stackoverflow.com/questions/7075349/android-clear-activity-stack
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        SharedPreferences sharedPreferences = getSharedPreferences(
                                getString(R.string.preference_file_key_purchase_items), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                }else {
                    displayError("Bitte Zahlmethode auswählen");
                }
            }
        });
    }

    public void populateRadioButtons() {
        for (PaymentMethod option : options) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            View item =  layoutInflater.inflate(R.layout.payment_option, null);

            RadioButton radioButton = (RadioButton) item;

            radioButton.setText(option.getName());

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