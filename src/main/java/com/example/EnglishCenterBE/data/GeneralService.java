package com.example.EnglishCenterBE.data;

import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.example.EnglishCenterBE.utils.StringUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class GeneralService {
    public static Firestore db = FirestoreClient.getFirestore();

    public static String login(String username, String password) {
        if (username == null || password == null) return null;
        DocumentReference documentRef = FirestorePathUtil.getPathAccount(db, username);
        try {
            ApiFuture<DocumentSnapshot> snapshot = documentRef.get();
            System.out.println(snapshot.get().getData());
            Account account =  Account.JSONParser(snapshot.get().getData());
            if (account != null) {
                if (password.equals(account.getPassword()) && account.isActive()) {
                    String token = StringUtil.generateToken(username);
                    return token;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean changePassword(String username, String newPassword) {
        if (username == null || newPassword == null) return false;
        DocumentReference documentRef = FirestorePathUtil.getPathAccount(db, username);
        try {
            documentRef.update("MK", newPassword);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Account getInformation(String username) {
        if (username == null) return null;
        DocumentReference documentRef = FirestorePathUtil.getPathAccount(db, username);
        try {
            ApiFuture<DocumentSnapshot> snapshot = documentRef.get();
            Account account =  Account.JSONParser(snapshot.get().getData());
            if (account != null) {
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
