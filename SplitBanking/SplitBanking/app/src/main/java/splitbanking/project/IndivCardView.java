package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class IndivCardView extends AppCompatActivity {


    BottomNavigationView navBar;
    Bundle b;

    TextView mCardName, mCardNum, mCVV, mExpDate, mLimitAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indiv_card_view);

        b = getIntent().getExtras();
        String cardName = b.getString("cardname");
        String cardNum = b.getString("cardnum");
        String cvvnum = b.getString("cvvnum");
        String expdate = b.getString("expdate");
         String fName = b.getString("fname");
         String cName = b.getString("mname");
         String lName = b.getString("lname");

        initializeElements();
        Log.d(cardName, cardName);
        Log.d(cardNum, cardNum);

        mCardName.setText(cardName);
        mCardNum.setText(cardNum);
        mCVV.setText(cvvnum);
        mExpDate.setText(expdate);

    }

    private void initializeElements(){
        initializeNavBar();
        mCardName = findViewById(R.id.tvCardName);
        mCardNum = findViewById(R.id.tvCardNumber);
        mCVV = findViewById(R.id.tvCVV);
        mExpDate = findViewById(R.id.tvExpDate);
        mLimitAmount = findViewById(R.id.tvLimitAmount);
    }

    private void initializeNavBar(){
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
                        startActivity(new Intent(IndivCardView.this, CardPage.class));
                        item.setIcon(R.drawable.ic_card_membership_black_24dp);
                        break;
                    case R.id.navigation_balance:
                        startActivity(new Intent(IndivCardView.this, BalancePage.class));
                        item.setIcon(R.drawable.ic_money);
                        break;
                    case R.id.navigation_notification:
                        startActivity(new Intent(IndivCardView.this, NotificationPage.class));
                        item.setIcon(R.drawable.ic_notifications_active_black_24dp);
                        break;
                    case R.id.navigation_friends:
                        startActivity(new Intent(IndivCardView.this, FriendsPage.class));
                        item.setIcon(R.drawable.ic_recent_actors_black_24dp);
                        break;

                }
                return false;
            }
        });
    }
}
