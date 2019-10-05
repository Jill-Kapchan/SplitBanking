package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddCardRoot extends AppCompatActivity {

    BottomNavigationView navBar;

    Button mPersonalBtn, mSharedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcardroot_activity);

        initializeElements();

        mPersonalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCardRoot.this, CreatePersonalCard.class);
                startActivity(intent);
            }
        });

        mSharedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCardRoot.this, CreateSharedCard.class);
                startActivity(intent);
            }
        });


    }

    private void initializeElements(){
        mPersonalBtn = findViewById(R.id.personalBtn);
        mSharedBtn = findViewById(R.id.sharedBtn);
        initializeNavBar();
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
                        startActivity(new Intent(AddCardRoot.this, CardPage.class));
                        item.setIcon(R.drawable.ic_card_membership_black_24dp);
                        break;
                    case R.id.navigation_balance:
                        startActivity(new Intent(AddCardRoot.this, BalancePage.class));
                        item.setIcon(R.drawable.ic_money);
                        break;
                    case R.id.navigation_notification:
                        startActivity(new Intent(AddCardRoot.this, NotificationPage.class));
                        item.setIcon(R.drawable.ic_notifications_active_black_24dp);
                        break;
                    case R.id.navigation_friends:
                        startActivity(new Intent(AddCardRoot.this, FriendsPage.class));
                        item.setIcon(R.drawable.ic_recent_actors_black_24dp);
                        break;

                }
                return false;
            }
        });
    }
}
