package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.bmz.bma.nfc_retail_android.R;

/**
 * author: Timon Hueppi
 * date: 2021/10/11
 */
public class RegisterActivity extends AppCompatActivity {

    Button registerBack;
    TextView registerTitle;
    TextView registerEmailLabel;
    EditText registerEmailInput;
    TextView registerFirstnameLabel;
    EditText registerFirstnameInput;
    TextView registerLastnameLabel;
    EditText registerLastnameInput;
    TextView registerPWLabel;
    EditText registerPWInput;
    TextView registerPWConfirmLabel;
    EditText registerPWConfirmInput;
    LinearLayout registerError;
    TextView registerErrorLabel;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBack = findViewById(R.id.registerBack);
        registerTitle = findViewById(R.id.registerTitle);
        registerEmailLabel = findViewById(R.id.registerEmailLabel);
        registerEmailInput = findViewById(R.id.registerEmailInput);
        registerFirstnameLabel = findViewById(R.id.registerFirstnameLabel);
        registerFirstnameInput = findViewById(R.id.registerFirstnameInput);
        registerLastnameLabel = findViewById(R.id.registerLastnameLabel);
        registerLastnameInput = findViewById(R.id.registerLastnameInput);
        registerPWLabel = findViewById(R.id.registerPWLabel);
        registerPWInput = findViewById(R.id.registerPWInput);
        registerPWConfirmLabel = findViewById(R.id.registerPWConfirmLabel);
        registerPWConfirmInput = findViewById(R.id.registerPWConfirmInput);
        registerError = findViewById(R.id.registerError);
        registerErrorLabel = findViewById(R.id.registerErrorLabel);
        registerButton = findViewById(R.id.registerButton);

        registerError.setVisibility(View.GONE);

        defineButtonHandlers();
    }

    private void defineButtonHandlers() {
        RegisterActivity that = this;
        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    hideError();
                    setResult(RESULT_OK, new Intent());
                    Intent intent = new Intent(that, PurchaseActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //TODO: implement proper error messages
                    displayError("Bitte die Felder korrekt ausfüllen");
                }
            }
        });
    }

    private boolean validateForm() {
        return validateEmail() && validateName() && validatePassword();
    }

    private boolean validateEmail() {
        //https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else/54122601
        String email = registerEmailInput.getText().toString();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        return pattern.matcher(email).matches() &&
                email.length() > 5 &&
                email.length() < 255;
    }

    private boolean validateName() {
        Pattern pattern = Pattern.compile("\\D+");
        return pattern.matcher(registerFirstnameInput.getText().toString()).matches() &&
                registerFirstnameInput.getText().toString().length() < 255 &&
                pattern.matcher(registerLastnameInput.getText().toString()).matches() &&
                registerLastnameInput.getText().toString().length() < 255;

    }

    private boolean validatePassword() {
        return registerPWInput.getText().toString().equals(registerPWConfirmInput.getText().toString()) &&
                registerPWInput.getText().toString().length() > 5 &&
                registerPWInput.getText().toString().length() < 255;
    }

    public void displayError(String message) {
        registerError.setVisibility(View.VISIBLE);
        registerErrorLabel.setText(message);
    }

    public void hideError() {
        registerError.setVisibility(View.GONE);
        registerErrorLabel.setText("");
    }
}