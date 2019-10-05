package splitbanking.project;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FirebaseCallback {

    private static DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("UserAccounts");

    private static UserAccount userToBeCalled;


    public FirebaseCallback(){

    }

    public static void isEmailDuplicate(final String email, final BooleanCallback booleanCallback){
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag = false;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    UserAccount user = ds.getValue(UserAccount.class);
                    if(user.getEmail().equals(email)){
                        flag = true;
                        break;
                    }
                }
                booleanCallback.callback(flag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void callCurrentUser(@NonNull final CurrentUserCallback finishedCallback){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    final String currentUserfID = firebaseUser.getUid();
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                UserAccount user = ds.getValue(UserAccount.class);
                                if (user.getFirebaseId().equals(currentUserfID)) {
                                    finishedCallback.callback(user);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    Log.d("user logged in", "logged in");
                }
            }
        });
    }

    public static void updateUser(UserAccount userToBeUpdated){
        String username = userToBeUpdated.getUsername();
        userRef.child(username).setValue(userToBeUpdated);
    }

    public static void addUserToCard(UserAccount userSource, final UserAccount userToAdd, Card cardToAddUser, final double split){
        final String usernameSource = userSource.getUsername();
        final String usernameToAdd = userToAdd.getUsername();
        final String cardName = cardToAddUser.getCardName();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserAccount user = ds.getValue(UserAccount.class);
                    if(user.getUsername().equals(usernameSource)){
                        ArrayList<Card> cl = user.getCardList(); // search through their cards
                        for(int i = 0; i < cl.size(); i++){
                            if(cl.get(i).getCardName() == cardName){
                                cl.get(i).addSharedUser(userToAdd, split);
                                userRef.child(usernameSource).child("cardList").setValue(cl);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
