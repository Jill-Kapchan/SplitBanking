package splitbanking.project;

import java.util.ArrayList;

public class BankUtils {

    public static final int ROUTING_NUMBER = 1212806749;

    // method name may change
    public static SharedUser findSharedUser(Card card, UserAccount user) {

        ArrayList<SharedUser> sharedUsers = card.getSharedUsers();

        for (SharedUser check : sharedUsers) {
            if (check.getUserAccount().equals(user)) return check;
        }

        return null;
    }

}
