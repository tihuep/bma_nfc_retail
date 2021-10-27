package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.service.PurchasePaymentService;

public class PaymentConfirmActivity extends AppCompatActivity {

    TextView paymentConfirmTitle;
    TextView paymentConfirmSubtitle;
    TextView paymentConfirmMethod;
    Button paymentConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);

        paymentConfirmTitle = findViewById(R.id.paymentConfirmTitle);
        paymentConfirmSubtitle = findViewById(R.id.paymentConfirmSubtitle);
        paymentConfirmMethod = findViewById(R.id.paymentConfirmMethod);
        paymentConfirmButton = findViewById(R.id.paymentConfirmButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            paymentConfirmMethod.setText(extras.getString("method"));

        defineButtonHandlers();

        PurchasePaymentService.postPurchase(this);
    }

    private void defineButtonHandlers() {
        PaymentConfirmActivity that = this;
        paymentConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, MainActivity.class);
                //https://stackoverflow.com/questions/7075349/android-clear-activity-stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}