package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.bmz.bma.nfc_retail_android.R;

public class LoginActivity extends AppCompatActivity {

    TextView loginTitle;
    TextView loginEmailLabel;
    EditText loginEmailInput;
    TextView loginPWLabel;
    EditText loginPWInput;
    Button loginButton;
    LinearLayout loginError;
    TextView loginErrorLabel;
    Button loginRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTitle = findViewById(R.id.loginTitle);
        loginEmailLabel = findViewById(R.id.loginEmailLabel);
        loginEmailInput = findViewById(R.id.loginEmailInput);
        loginPWLabel = findViewById(R.id.loginPWLabel);
        loginPWInput = findViewById(R.id.loginPWInput);
        loginButton = findViewById(R.id.loginButton);
        loginError = findViewById(R.id.loginError);
        loginErrorLabel = findViewById(R.id.loginErrorLabel);
        loginRegisterButton = findViewById(R.id.loginRegisterButton);

        defineButtonHandlers();
    }

    private void defineButtonHandlers() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //check credentials
                /*Intent intent = new Intent(this, PurchaseActivity.class);
                startActivity(intent);*/
            }
        });

        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);*/
            }
        });
    }

    public void displayError(String message) {
        loginError.setVisibility(View.VISIBLE);
        loginErrorLabel.setText(message);
    }

    public void hideError() {
        loginError.setVisibility(View.GONE);
        loginErrorLabel.setText("");
    }
}