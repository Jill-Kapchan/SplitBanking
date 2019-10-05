package splitbanking.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Card implements Parcelable {

    private String fName;
    private String mName;
    private String lName;
    private String cardName;

    private int oneUse;
    private int isShared;
    private long cardNumber;
    private int expDate;
    private int cvvNumber;
    private double limit;

    private String token;

    private ArrayList<SharedUser> sharedUsers;

    public Card(UserAccount user, String cardName, double limit, int oneUse, int isShared) {
        this.fName = user.getfName();
        this.mName = user.getmName();
        this.lName = user.getlName();
        this.cardName = cardName;
        cardNumber = 0;
        expDate = 1022;
        cvvNumber = 0;
        token = "";
        this.limit = limit;
        this.oneUse = oneUse;
        this.isShared = isShared;

        sharedUsers = new ArrayList<SharedUser>();
        this.sharedUsers.add(new SharedUser(user, 1)); // by default host pays 100% of charges
        this.setToken();
    }

    protected Card(Parcel in){
        fName = in.readString();
        mName = in.readString();
        lName = in.readString();
        cardName = in.readString();
        oneUse = in.readInt();
        isShared = in.readInt();
        cardNumber = in.readLong();
        expDate = in.readInt();
        cvvNumber = in.readInt();
        limit = in.readDouble();
        token = in.readString();
        sharedUsers = in.readArrayList(SharedUser.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(fName);
        parcel.writeString(mName);
        parcel.writeString(lName);
        parcel.writeString(cardName);
        parcel.writeLong(cardNumber);
        parcel.writeInt(expDate);
        parcel.writeInt(cvvNumber);
        parcel.writeString(token);
        parcel.writeDouble(limit);
        parcel.writeInt(oneUse);
        parcel.writeInt(isShared);
        parcel.writeList(sharedUsers);

    }

    public static final Creator<Card> CREATOR = new Creator<Card>(){
        @Override
        public Card createFromParcel(Parcel in){ return new Card(in); }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    //----------------------------
    //Accessor methods
    //----------------------------
    public String getFName() {
        return fName;
    }

    public String getMName() {
        return mName;
    }

    public String getlName() {
        return lName;
    }

    public String getCardName() {
        return this.cardName;
    }

    public int isOneUse() {
        return oneUse;
    }

    public int isShared() {
        return isShared;
    }

    public long getCardNum() {
        return cardNumber;
    }

    public int getExpDate() {
        return expDate;
    }

    public int getCvvNum() {
        return cvvNumber;
    }

    public double getLimit() {
        return limit;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<SharedUser> getSharedUsers() {
        return this.sharedUsers;
    }

    //----------------------------
    //Mutator methods
    //----------------------------
    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardNum(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpDate(int expDate) {
        this.expDate = expDate;
    }

    public void setCvvNum(int cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public void setToken() {
        this.token  = Long.toString(this.getCardNum()) + "/" +
                Integer.toString(this.getExpDate()) + "/" +
                Integer.toString(this.getCvvNum());
    }

    public void setToken(String token) {
        this.token  = token;
    }

    //---------------------
    // sharing functions
    //---------------------
    void charge(double amt) {
        // round down on uneven charges, host will pay the rest
    }

    // TODO: later
    void changeSplit(UserAccount user, double percent) {
        SharedUser shared = BankUtils.findSharedUser(this, user);

        shared.setPayPercent(percent);
    }

    void addSharedUser(UserAccount user, double payPercent) {
        if (isShared == 0) return;
        SharedUser shared = new SharedUser(user, payPercent);

        this.sharedUsers.add(shared);
    }

    void removeSharedUser(UserAccount user) {
        SharedUser shared = BankUtils.findSharedUser(this, user);

        this.sharedUsers.remove(shared);
    }

    @Override
    public String toString() {
        String cardName = "Card Name: " + this.cardName + "\n";
        String owner = "Owner: " + this.fName + " " + this.mName + " " + this.lName + "\n";
        String cardnum = "Card Number: " + this.cardNumber + "\n";
        String expdate = "Expiration Date: " + this.expDate + "\n";
        String cvvnumber = "CVV: " + this.cvvNumber + "\n";
        String limit = "Limit: " + (this.limit == -1 ? "Unlimited" : this.limit + "") + "\n";
        String singleuse = "Single Use: " + this.oneUse + "\n";
        String shared = "Shard Users: " + this.sharedUsers.toString() + "\n";

        return cardName + owner + cardnum + expdate + cvvnumber + limit + singleuse + shared;
    }

}
