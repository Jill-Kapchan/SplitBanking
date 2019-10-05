package splitbanking.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView mEmail, mPassword;
    Button mLoginBtn, mRegisterBtn;

    private ViewSwitcher mViewSwitcher;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        initializeElements();

        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, CardPage.class);
            startActivity(intent);
        }

        mViewSwitcher = findViewById(R.id.viewswitcher);
        mViewSwitcher.showNext();

    }

    private void initializeElements(){
        mEmail = (TextView)findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);
        mRegisterBtn = findViewById(R.id.register);


        // user logs in or error
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (!email.isEmpty() || !password.isEmpty()) {//checks if either box is empty
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast toast = Toast.makeText(MainActivity.this, "Logging in", Toast.LENGTH_SHORT);
                                toast.show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, CardPage.class);
                                finish();
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else
                {
                    //if fails
                    Toast.makeText(MainActivity.this, "One or more empty field",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterStep1.class);
                startActivity(intent);
            }
        });

    }
}
