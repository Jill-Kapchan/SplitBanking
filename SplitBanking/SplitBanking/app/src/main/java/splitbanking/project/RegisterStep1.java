package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterStep1 extends AppCompatActivity {
    EditText mFNameInput;
    EditText mMNameInput;
    EditText mLNameInput;
    Button mNextButton;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        initializeElements();
        b = new Bundle();


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fName =  mFNameInput.getText().toString();
                final String mName =  mMNameInput.getText().toString();
                final String lName =  mLNameInput.getText().toString();
                Intent intent = new Intent(RegisterStep1.this,RegisterStep2.class);
                intent.putExtra("fName", fName);
                intent.putExtra("mName", mName);
                intent.putExtra("lName", lName);
                startActivity(intent);
            }
        });
    }

    public void initializeElements(){
        mFNameInput = findViewById(R.id.fNameInput);
        mMNameInput = findViewById(R.id.mNameInput);
        mLNameInput = findViewById(R.id.fNameInput);
        mNextButton = findViewById(R.id.nextButton);
    }
}
