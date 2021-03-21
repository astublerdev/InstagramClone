package com.codepath.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick signup button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUpUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                // TODO: navigate to the main activity if the user has signed in
                goMainActivity();
            }
        });
    }

    private void signUpUser(String username, String password) {
        Log.i(TAG, "Attempting to signup user " + username);

            ParseUser newUser = new ParseUser();
            newUser.setUsername(username);
            newUser.setPassword(password);
            try {
                newUser.signUp();
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e != null) {
                            Log.e(TAG, "Issue with login", e);
                            Toast.makeText(LoginActivity.this, "Issue with login!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        goMainActivity();
                    }
                });
            } catch (ParseException e) {
                Log.e(TAG, "Issue with signup", e);
                Toast.makeText(LoginActivity.this, "Username is taken", Toast.LENGTH_SHORT).show();
                etUsername.setText("");
                e.printStackTrace();
            }
    }

    protected boolean queryUsers(String username) {
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        final boolean[] result = {true};
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                boolean b = true;
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (ParseUser user : users) {
                    if (user.getUsername().equals(username)) {
                        Toast.makeText(LoginActivity.this, "Username is taken", Toast.LENGTH_SHORT).show();
                        result[0] = false;
                        break;
                    }
                }
            }
        });
        return result[0];
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}