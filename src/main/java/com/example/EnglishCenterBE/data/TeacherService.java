package com.example.EnglishCenterBE.data;

import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.models.Score;
import com.example.EnglishCenterBE.utils.Constants;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherService {
    public static Firestore db = FirestoreClient.getFirestore();

    public static Map<String, Object> getClassDetail(String classId) {
        if (classId == null) return null;
        Map<String, Object> map = new HashMap<>();
        DocumentReference documentRef = FirestorePathUtil.getPathClassDetail(db, classId);
        try {
            ApiFuture<DocumentSnapshot> snapshot = documentRef.get();
            Class detailClass =  Class.JSONParser(snapshot.get().getData());
            if (detailClass != null) {
                map.put("LopHoc", detailClass.toJSONObject());
                // Lay list student ve
                if (detailClass.getStudents() != null) {
                    List<Map<String, Object>> studentList = new ArrayList<>();
                    for (String studentId : detailClass.getStudents()) {
                        Score score = ScoreService.getScoreDetail(studentId, classId);
                        if (score != null) {
                            studentList.add(score.toJSONObject());
                        }
                    }
                    map.put("HocVien", studentList);
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addScores(String classId, String className, List<Score> scores) {
        try {
            if (classId == null || className == null || scores == null) return false;
            for (Score score : scores) {
                score.setClassId(classId);
                score.setClassName(className);
                Account account = AccountService.getInstance().getAccount(score.getStudentId());
                score.setStudentName(account.getName());
                ScoreService.addScore(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Class> searchByClassId(String classId) {
        List<Class> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathClass(db);
        ApiFuture<QuerySnapshot> snapshots = collectionRef.whereGreaterThanOrEqualTo("MaLop", classId)
                .whereLessThanOrEqualTo("MaLop", classId + "\uf8ff").get();
        try {
            QuerySnapshot classSnapshot = snapshots.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Class studentClass = Class.JSONParser(snapshot.getData());
                if (studentClass != null) {
                    list.add(studentClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Account> searchByStudentId(String studentId) {
        List<Account> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathAccountCollection(db);
        ApiFuture<QuerySnapshot> snapshots = collectionRef.whereEqualTo("Role", Constants.Role.STUDENT)
                .whereGreaterThanOrEqualTo("TK", studentId)
                .whereLessThanOrEqualTo("TK", studentId + "\uf8ff")
                .get();
        try {
            QuerySnapshot studentSnapshot = snapshots.get();
            for (QueryDocumentSnapshot snapshot : studentSnapshot.getDocuments()) {
                Account student = Account.JSONParser(snapshot.getData());
                if (student != null) {
                    list.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Account> searchByStudentName(String studentName) {
        List<Account> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathAccountCollection(db);
        ApiFuture<QuerySnapshot> snapshots = collectionRef.whereEqualTo("Role", Constants.Role.STUDENT)
                .whereGreaterThanOrEqualTo("HoTen", studentName)
                .whereLessThanOrEqualTo("HoTen", studentName + "\uf8ff")
                .get();
        try {
            QuerySnapshot studentSnapshot = snapshots.get();
            for (QueryDocumentSnapshot snapshot : studentSnapshot.getDocuments()) {
                Account student = Account.JSONParser(snapshot.getData());
                if (student != null) {
                    list.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
