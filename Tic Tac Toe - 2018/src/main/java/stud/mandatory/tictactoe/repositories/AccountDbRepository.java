package stud.mandatory.tictactoe.repositories;

import stud.mandatory.tictactoe.model.Account;
import stud.mandatory.tictactoe.repositories.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//the main database for the accounts
public class AccountDbRepository implements iAccountRepository {

	private Connection connection = DBConnection.getConnection();

	//returns a list of all the accounts in the database
	@Override
	public ArrayList<Account> readAll() {
		ArrayList<Account> accounts = new ArrayList<>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				accounts.add(new Account(
						resultSet.getInt("accountID"),
						resultSet.getString("nickname"),
						resultSet.getString("password"),
						resultSet.getInt("wins"),
						resultSet.getInt("losses"),
						resultSet.getInt("ties")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	//using an ID, gets the corresponding account from the database
	@Override
	public Account read(int accountID) {
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE accountID=?");
			preparedStatement.setInt(1, accountID);
			ResultSet resultSet = preparedStatement.executeQuery();
			//if the account has been found, return an object of it
			if(resultSet.next())
				return new Account(
						resultSet.getInt("id"),
						resultSet.getString("nickname"),
						resultSet.getString("password"),
						resultSet.getInt("wins"),
						resultSet.getInt("losses"),
						resultSet.getInt("ties"));
			else
				//if the account was not found, return null
				return null;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	//using an username and a password, returns the full account from the database
	@Override
	public Account read(Account loginAccount){
		try{
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE nickname = ? AND password=?");
			preparedStatement.setString(1, loginAccount.getNickname());
			preparedStatement.setString(2, loginAccount.getPassword());
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();


			Account a = new Account(
					resultSet.getInt("accountID"),
					resultSet.getString("nickname"),
					resultSet.getString("password"),
					resultSet.getInt("wins"),
					resultSet.getInt("losses"),
					resultSet.getInt("ties"));
			return a;

		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	//creates a new account in the database
	@Override
	public boolean create(Account account) {
		//checks if the nickname and the password fields are blank
		//and if the account is not already in the database
		if (account.getNickname().equals("") ||
			account.getPassword().equals("") ||
			read(account) != null)
			return false;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accounts(nickname, password)  VALUES (?,?)");
			preparedStatement.setString(1, account.getNickname());
			preparedStatement.setString(2, account.getPassword());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//updates the provided account, typically after a game has ended
	@Override
	public void update(Account account) {
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE accounts SET wins=?, losses=?, ties=? WHERE accountID=?");
			preparedStatement.setInt(1, account.getWins());
			preparedStatement.setInt(2, account.getLosses());
			preparedStatement.setInt(3, account.getTies());
			preparedStatement.setInt(4, account.getId());
			preparedStatement.execute();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
}