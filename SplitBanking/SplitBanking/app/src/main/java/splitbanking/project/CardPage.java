package splitbanking.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CardPage extends AppCompatActivity {

    BottomNavigationView navBar;
    Button mAddCardBtn;
    ListView mCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardpage);

        initializeElements();

        mAddCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardPage.this, AddCardRoot.class);
                startActivity(intent);
            }
        });

        final List<Card> cards = new ArrayList<Card>();
        FirebaseCallback.callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(UserAccount currentUser) {
                for(int i = 0; i < currentUser.getCardList().size(); i++){
                    cards.add(currentUser.getCardList().get(i));
                }
            }
        });

        final CardListAdapter cla = new CardListAdapter(CardPage.this, R.layout.item_card, cards);
        mCardList.setAdapter(cla);

        mCardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Card card = (Card) adapterView.getAdapter().getItem(position);

                Bundle b = new Bundle();
//                Intent intent = new Intent(CardPage.this, IndivCard.class);
                b.putParcelable("CARD", card);
//                intent.putExtras(b);
//                startActivity(intent);
            }
        });
    }

    private void initializeElements(){
        initializeNavBar();
        mAddCardBtn = (Button) findViewById(R.id.addCardBtn);
        mCardList = (ListView) findViewById(R.id.cardList);

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
                        startActivity(new Intent(CardPage.this, CardPage.class));
                        item.setIcon(R.drawable.ic_card_membership_black_24dp);
                        break;
                    case R.id.navigation_balance:
                        startActivity(new Intent(CardPage.this, BalancePage.class));
                        item.setIcon(R.drawable.ic_money);
                        break;
                    case R.id.navigation_notification:
                        startActivity(new Intent(CardPage.this, NotificationPage.class));
                        item.setIcon(R.drawable.ic_notifications_active_black_24dp);
                        break;
                    case R.id.navigation_friends:
                        startActivity(new Intent(CardPage.this, FriendsPage.class));
                        item.setIcon(R.drawable.ic_recent_actors_black_24dp);
                        break;

                }
                return false;
            }
        });
    }

}
