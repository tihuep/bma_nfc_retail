package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Pattern;

import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.Service.UserService;

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
                if (validateForm())
                    UserService.login(that, loginEmailInput.getText().toString(), loginPWInput.getText().toString());
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

    private Boolean validateForm(){
        String email = loginEmailInput.getText().toString();
        String password = loginPWInput.getText().toString();
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        if (!emailPattern.matcher(email).matches() ||
            email.length() < 5 ||
            email.length() > 255) {
            displayError(getString(R.string.invalid_email));
            return false;
        }else if (password.length() < 5 ||
            password.length() > 255) {
            displayError(getString(R.string.invalid_pw));
            return false;
        }
        hideError();
        return true;
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