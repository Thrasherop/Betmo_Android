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

    // Acccount data holders
    public static int meganBalance = -69;
    public static int joshBalance = -69;

    public static int meganCurGuess = -1;
    public static int joshCurGuess = -1;
    public static String meganLastGuessTime = "";
    public static String joshLastGuessTime = "";

    public static int meganTotalWins = -1;
    public static int joshTotalWins = -1;

    public static String currentUser = JoshName;






}
