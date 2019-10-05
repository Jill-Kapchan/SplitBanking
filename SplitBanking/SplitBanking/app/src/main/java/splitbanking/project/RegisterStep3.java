package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterStep3 extends AppCompatActivity {
    EditText mUsername;
    EditText mPassword;
    EditText mEmail;
    Button mSubmitButton;

    Bundle b;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private static DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        initializeElements();
        b = new Bundle();
        b = getIntent().getExtras();
        final String fName = b.getString("fName");
        final String mName = b.getString("mName");
        final String lName = b.getString("lName");
        final String location = b.getString("location");

        userRef = FirebaseDatabase.getInstance().getReference().child("UserAccounts");
        mAuth = FirebaseAuth.getInstance();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidEmail(mEmail.getText().toString()) == false){
                    Toast.makeText(RegisterStep3.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }
                else if(mEmail.getText().toString().matches("") || mUsername.getText().toString().matches("") || mPassword.getText().toString().matches("")){
                    Toast.makeText(RegisterStep3.this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseCallback.isEmailDuplicate(mEmail.getText().toString(), new BooleanCallback() {
                        @Override
                        public void callback(boolean data) {
                            if(data == true){
                                Toast.makeText(RegisterStep3.this, "Email is already registered. Enter a different email.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                final String username =  mUsername.getText().toString();
                                final String password =  mPassword.getText().toString();
                                final String email = mEmail.getText().toString();
                                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterStep3.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(RegisterStep3.this, CardPage.class);
                                            UserAccount user = new UserAccount();
                                            user.setfName(fName);
                                            user.setmName(mName);
                                            user.setlName(lName);
                                            user.setLocation(location);
                                            user.setEmail(email);
                                            user.setUsername(username);
                                            user.setPassword(password);
                                            user.setFirebaseId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            userRef.child(username).setValue(user);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        }
                    });


                }


            }
        });
    }

    public void initializeElements(){
        mUsername = findViewById(R.id.usernameInput);
        mPassword = findViewById(R.id.passwordInput);
        mEmail = findViewById(R.id.emailInput);
        mSubmitButton = (Button) findViewById(R.id.submitButton);
    }

    public static boolean checkValidEmail(String email){
        String symbols = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(symbols, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
