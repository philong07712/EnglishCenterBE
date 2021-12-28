package com.example.EnglishCenterBE.utils;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

public class FirestorePathUtil {
    public static DocumentReference getPathStudent(Firestore db, String id) {
        return db.collection("HOCVIEN").document(id);
    }
}
