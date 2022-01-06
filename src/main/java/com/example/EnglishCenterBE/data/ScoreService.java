package com.example.EnglishCenterBE.data;

import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.models.Score;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    public static Firestore db = FirestoreClient.getFirestore();

    public static List<Score> getScoreList(String username) {
        if (username == null) return null;
        List<Score> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathScore(db);
        ApiFuture<QuerySnapshot> scoreQuery = collectionRef.whereEqualTo("MaHocVien", username).get();
        try {
            QuerySnapshot classSnapshot = scoreQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Score score = Score.JSONParser(snapshot.getData());
                if (score != null) {
                    list.add(score);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Score getScoreDetail(String username, String classId) {
        if (username == null || classId == null) return null;
        CollectionReference collectionRef = FirestorePathUtil.getPathScore(db);
        System.out.println("username: " + username + " classId: " + classId);
        ApiFuture<QuerySnapshot> scoreQuery = collectionRef
                .whereEqualTo("MaHocVien", username).whereEqualTo("MaLop", classId).get();
        try {
            QuerySnapshot classSnapshot = scoreQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Score score = Score.JSONParser(snapshot.getData());
                if (score != null) {
                    return score;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
