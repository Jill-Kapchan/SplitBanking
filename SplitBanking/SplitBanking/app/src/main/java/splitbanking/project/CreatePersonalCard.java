package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePersonalCard extends AppCompatActivity {

    BottomNavigationView navBar;

    FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private DatabaseReference userRef;

    Button mCreateBtn;
    EditText mCardNameInput, mAmountInput;
    RadioButton mLimit, mNoLimit, mOneTime;
    RadioGroup mLimitGroup;

    String cardName;
    double amountLimit = 0.0;
    int limit = 0;
    int noLimit = 0;
    int oneTime = 0;

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpersonalcard_activity);

        initializeElements();
        userRef = FirebaseDatabase.getInstance().getReference().child("UserAccounts");

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardName = mCardNameInput.getText().toString();
                if(!mAmountInput.getText().toString().equals("")){
                    amountLimit = Double.parseDouble(mAmountInput.getText().toString());
                }else{
                    amountLimit = 0.0;
                }

                if (mLimit.isChecked()) {
                    limit = 1;
                } else if (mNoLimit.isChecked()) {
                    noLimit = 1;
                } else if (mOneTime.isChecked()) {
                    oneTime = 1;
                }

                FirebaseCallback.callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(UserAccount currentUser) {
                        Card tokenizedCard = currentUser.generate(cardName, amountLimit, oneTime, 0);
                        Log.d(tokenizedCard.getCardName(),"cardname");
                        Log.d(String.valueOf(tokenizedCard.getCardNum()), "card num");

                        Intent intent = new Intent(CreatePersonalCard.this, IndivCardView.class);
                        intent.putExtra("cardname", tokenizedCard.getCardName());
                        intent.putExtra("cardnum", String.valueOf(tokenizedCard.getCardNum()));
                        intent.putExtra("cvvnum", String.valueOf(tokenizedCard.getCvvNum()));
                        intent.putExtra("expdate", String.valueOf(tokenizedCard.getExpDate()));


                        startActivity(intent);
                        //FirebaseCallback.updateUser(currentUser);

                    }

                });
            }

        });

    }

    private void initializeElements() {
        initializeNavBar();
        mCreateBtn = findViewById(R.id.confirmCreate);
        mCardNameInput = findViewById(R.id.cardNameInput);
        mAmountInput = findViewById(R.id.limitInput);
        mLimit = findViewById(R.id.limitRadio);
        mNoLimit = findViewById(R.id.noLimitRadio);
        mOneTime = findViewById(R.id.oneTimeRadio);

    }

    private void initializeNavBar() {
        // Bottom toolbar
        navBar = (BottomNavigationView) findViewById(R.id.navigationBar);
        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_card:
                        startActivity(new Intent(CreatePersonalCard.this, CardPage.class));
                        item.setIcon(R.drawable.ic_card_membership_black_24dp);
                        break;
                    case R.id.navigation_balance:
                        startActivity(new Intent(CreatePersonalCard.this, BalancePage.class));
                        item.setIcon(R.drawable.ic_money);
                        break;
                    case R.id.navigation_notification:
                        startActivity(new Intent(CreatePersonalCard.this, NotificationPage.class));
                        item.setIcon(R.drawable.ic_notifications_active_black_24dp);
                        break;
                    case R.id.navigation_friends:
                        startActivity(new Intent(CreatePersonalCard.this, FriendsPage.class));
                        item.setIcon(R.drawable.ic_recent_actors_black_24dp);
                        break;

                }
                return false;
            }
        });
    }

}
