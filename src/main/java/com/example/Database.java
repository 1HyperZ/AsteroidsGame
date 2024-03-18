package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.example.Utils.LeaderboardEntry;

// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;

// import java.io.FileInputStream;
// import java.io.IOException;




// /**
//  * Database
//  */
public final class Database {

    private Database(){
    }

    public static int getUserBestScore(String userName) {
        return 0;
    }

    public static void setUserBestScore(String userName, int score) {
    }

    public static boolean isNewUser(String userName) {
        return false;
    }

    public static List<LeaderboardEntry> getUserScoreList() {
        return null;
    }
}