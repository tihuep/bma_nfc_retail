package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.bmz.bma.nfc_retail_android.R;

/**
 * author: Timon Hueppi
 * date: 2021/10/11
 */
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

        loginError.setVisibility(View.GONE);

        defineButtonHandlers();
    }

    private void defineButtonHandlers() {
        LoginActivity that = this;
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: check credentials
                Intent intent = new Intent(that, PurchaseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(that, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    //https://stackoverflow.com/questions/41194680/close-another-activity-from-current-one/41195489
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
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