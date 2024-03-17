package com.example.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;

// import java.io.FileInputStream;
// import java.io.IOException;




// /**
//  * Database
//  */
public final class Database {

    private Database() throws FileNotFoundException{
        FileInputStream serviceAccount =
            new FileInputStream("src/main/resources/com/example/asteroidsgame-e3baf-firebase-adminsdk-v5845-4cbf992265.json");

    }

    public static void main(String[] args) throws FileNotFoundException {
        new Database();
    }
}