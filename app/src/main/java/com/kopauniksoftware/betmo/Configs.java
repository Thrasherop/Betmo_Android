package com.kopauniksoftware.betmo;

public class Configs {

    // API Configs
    public static String baseAPIAddr = "https://thrasherop.pythonanywhere.com/betmo";

    public static String transferAddr = baseAPIAddr + "/transfer_balance";
    public static String getBalanceAddr = baseAPIAddr + "/get_balances";
    public static String submitGuessAddr = baseAPIAddr + "/submit_guess";
    public static String submitFinalScoreAddr = baseAPIAddr + "/submit_final_score";
    public static String getCurrentGuessAddr = baseAPIAddr + "/get_current_guesses";
    public static String getTotalWinsAddr = baseAPIAddr + "/get_total_wins";


    // Config related config
    public static String currentUserSaveFile = "currentUser.txt";

    // Enums
    public static String JoshName = "Josh";
    public static String MeganName = "Megan";

    // Account data holders
    public static int meganBalance = -69;
    public static int joshBalance = -69;

    public static int meganCurGuess = -1;
    public static int joshCurGuess = -1;
    public static String meganLastGuessTime = "";
    public static String joshLastGuessTime = "";

    public static int meganTotalWins = -1;
    public static int joshTotalWins = -1;

    public static String currentUser = JoshName;


    // Transfer screen configs
    public static String meganBalanceMessage = "Megan's balance: ";
    public static String joshBalanceMessage = "Josh's balance: ";

    public static String updateTransferComplete = "Transfer Complete";
    public static String updateTransferInProgress = "Transfering...";
    public static String updateTransferFailed = "Transfer failed: ";
    public static String updateInsufficientFunds = "Insufficient Funds";


    // Submit screen configs
    public static String updateGuessSubmitComplete = "Guess submission complete";
    public static String updateGuessSubmitFailed = "Guess submission failed: ";

    public static String updateFinalSubmitComplete = "Final score submitted";
    public static String updateFinalFailed = "Failed to submit final score: ";

    public static String updateSubmitInProgress = "Submitting...";
    public static String updateSubmitFailedGeneral = "Submission Failed: ";





}
