package stud.mandatory.tictactoe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import stud.mandatory.tictactoe.model.Account;
import stud.mandatory.tictactoe.model.AccountV;
import stud.mandatory.tictactoe.repositories.AccountDbRepository;
import stud.mandatory.tictactoe.repositories.iAccountRepository;

import java.util.ArrayList;

@Controller
public class TicTacToeController {

    private iAccountRepository accountRepository;

    public TicTacToeController() {
        accountRepository = new AccountDbRepository();
    }

    private Account currentAccount = null;
    private GameController gameCtrl = new GameController();

    /*
    NO_GAME - initial state after logging in
    GAME_IN_PROGRESS - a game is currently in progress
    GAME_ENDED - game ended
    -A cheap equivalent to a finite state machine
     */
    private String gameState = "NO_GAME";

    //the main page containing the game itself
    @GetMapping("/")
    public String index(Model model) {
        if (currentAccount == null)
            //no account is logged in, so redirect to the login page
            return "redirect:/login";
        else if (gameState.equals("NO_GAME")) {
            //user just logged in, so start a new game using their details
            gameCtrl.newGame(currentAccount);
            //set the current state of the game
            gameState = "GAME_IN_PROGRESS";
        }

        //adds the board to the view model
        model.addAttribute("squares", gameCtrl.getBoard());
        //adds the account itself to the view model
        model.addAttribute("account", currentAccount);
        model.addAttribute("gameState", gameState);
        return "index";
    }

    //called when the user clicks a square
    @GetMapping("/place")
    public String place(@RequestParam("id") int id){
        //initial value
        int result = -2;
        if (gameState.equals("GAME_IN_PROGRESS")) {
            //there's a game in progress, so check and place the tag on the board
            result = gameCtrl.place(id);
        }
        if (result != -2) {
            //the result was changed by the game controller, so the game has ended
            gameState = "GAME_ENDED";
            if (result == -1) {
                //the game ended in a tie
                currentAccount.incrementTies();
            }

            else if (result == 0) {
                //the player won
                currentAccount.incrementWins();
            }
            else { // result == 1
                //the computer won
                currentAccount.incrementLosses();
            }
            //update the user's wins, losses and ties in the database
            accountRepository.update(currentAccount);
        }
        //redirects to the main game page
        return "redirect:/";
    }

    //called by the "play again" button
    @GetMapping("/restart")
    public String restart(){
        //stars a new game
        gameState = "GAME_IN_PROGRESS";
        gameCtrl.newGame(currentAccount);
        return "redirect:/";
    }

    //a message showed by the /create page when something goes wrong
    //while creating an account
    private String createMessage = "";
    @GetMapping("/create")
    public String create(Model model) {
        //adds the message to the view model and then resets it
        model.addAttribute("message", createMessage);
        createMessage = "";
        return "create";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute AccountV account) {
        //checks if the account is already in the database
        //'true' = it's not
        boolean dbCheck = accountRepository.create(account);
        //checks if the user checked the "I'm human" checkbox when creating an account
        boolean accVer = account.isVerified();
        if (account.isVerified() && dbCheck) {
            //the account is not already in the database and the user is human,
            //so log in with that account
            currentAccount = accountRepository.read(account);
            //redirects to the main game page
            return "redirect:/";
        }
        else {
            if (!accVer) {
                //the used did not check the "I'm human" checkbox
                createMessage = "You need to verify you're human";
            }
            else if (!dbCheck) {
                //the account is already in the database
                createMessage = "Account already exists";
            }
            //redirect to the create page, to try again
            return "redirect:/create";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute Account userAccount){

        //temporary workaround for the database inactivity issue
        if(userAccount.getNickname().equals("getConnection")){
            accountRepository = new AccountDbRepository();
            System.out.println("ACCOUNT REPOSITORY REINITIALIZED");
        }
        else {
            //returns null if the account is not in the database
            currentAccount = accountRepository.read(userAccount);
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(){
        currentAccount = null;
        gameState = "NO_GAME";
        return "redirect:/";
    }

    //shows a table of all the players, sorted by their winning percentage
    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        //reads the players in a list
        ArrayList<Account> accounts = accountRepository.readAll();
        //sorts the list of players in descending order
        accounts.sort(Account::compareTo);
        //adds the list to the view model
        model.addAttribute("players", accounts);
        model.addAttribute("userState", currentAccount == null ? "NO_USER" : "USER");
        return "leaderboard";
    }
}
