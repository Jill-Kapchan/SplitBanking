package splitbanking.project;

public class SharedUser {
    private UserAccount userAccount;
    private double payPercent;

    public SharedUser(UserAccount sharedUser, double payPercent) {
        this.userAccount = sharedUser;
        this.payPercent = payPercent;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setSharedUser(UserAccount sharedUser) {
        this.userAccount = sharedUser;
    }

    public double getPayPercent() {
        return payPercent;
    }

    public void setPayPercent(double payPercent) {
        this.payPercent = payPercent;
    }

    @Override
    public String toString() {
        String userInfo = this.userAccount.toString();
        String pay = "Pay Percent: " + this.payPercent + "\n";

        return userInfo + pay;
    }

}