package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterStep2 extends AppCompatActivity {
    EditText mLocationInput;
    Button mNextButton;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initializeElements();
        b = new Bundle();
        b = getIntent().getExtras();
        final String fName = b.getString("fName");
        final String mName = b.getString("mName");
        final String lName = b.getString("lName");


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String location =  mLocationInput.getText().toString();
                Intent intent = new Intent(RegisterStep2.this,RegisterStep3.class);
                intent.putExtra("fName", fName);
                intent.putExtra("mName", mName);
                intent.putExtra("lName", lName);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }

    public void initializeElements(){
        mLocationInput = findViewById(R.id.locationInput);
        mNextButton = findViewById(R.id.nextButton);
    }
}
