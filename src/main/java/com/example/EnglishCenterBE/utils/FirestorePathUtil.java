package com.example.EnglishCenterBE.utils;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

public class FirestorePathUtil {

    public static CollectionReference getPathAccountCollection(Firestore db) {
        return db.collection("TAIKHOAN");
    }

    public static CollectionReference getPathScore(Firestore db) {
        return db.collection("DIEMTHI");
    }
    public static DocumentReference getPathAccount(Firestore db, String id) {
        return db.collection("TAIKHOAN").document(id);
    }
    public static CollectionReference getPathClass(Firestore db) {
        return db.collection("LOP");
    }
    public static DocumentReference getPathClassDetail(Firestore db, String classId) {
        return db.collection("LOP").document(classId);
    }
}
