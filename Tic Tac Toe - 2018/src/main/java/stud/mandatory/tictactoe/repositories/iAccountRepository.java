package stud.mandatory.tictactoe.repositories;

import stud.mandatory.tictactoe.model.Account;

import java.util.ArrayList;

public interface iAccountRepository {
	//returns a list of all the accounts in the repository
	ArrayList<Account> readAll();
	//using an ID, gets the corresponding account from the repository
	Account read(int id);
	//using an username and a password, returns the full account from the repository
	Account read(Account loginAccount);
	//creates a new account in the repository
	boolean create(Account account);
	//updates the provided account, typically after a game has ended
    void update(Account currentAccount);
}