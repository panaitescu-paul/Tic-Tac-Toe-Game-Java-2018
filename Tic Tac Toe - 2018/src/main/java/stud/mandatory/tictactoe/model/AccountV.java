package stud.mandatory.tictactoe.model;

//class model used exclusively by the html form to
//include the checkbox boolean, used to verify if the user is human
public class AccountV extends Account {
    private boolean verified;

    public AccountV(){}

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
