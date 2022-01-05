package com.example.EnglishCenterBE.data;

import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class StudentService {
    public static StudentService instance;
    public Firestore db;
    public static StudentService getInstance() {
        if (instance == null) instance = new StudentService();
        return instance;
    }

    private StudentService() {
        db = FirestoreClient.getFirestore();
    }

//    public Student getStudent(String id) {
//        if (id != null) {
//            DocumentReference documentRef = FirestorePathUtil.getPathAccount(db, id);
//            try {
//                ApiFuture<DocumentSnapshot> snapshot = documentRef.get();
//                Student student = Student.JSONParser(snapshot.get().getData());
//                return student;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}
