package com.example.EnglishCenterBE.data;

import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassService {
    public static Firestore db = FirestoreClient.getFirestore();

    public static List<Class> getClassList(String username) {
        if (username == null) return null;
        List<Class> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathClass(db);
        ApiFuture<QuerySnapshot> classQuery = collectionRef.whereArrayContains("HocVien", username).get();
        try {
            QuerySnapshot classSnapshot = classQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Class studentClass = Class.JSONParser(snapshot.getData());
                if (studentClass != null) {
                    list.add(studentClass);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Class> getClassListWithTeacher(String teacherId) {
        if (teacherId == null) return null;
        List<Class> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathClass(db);
        ApiFuture<QuerySnapshot> classQuery = collectionRef.whereEqualTo("MaGiangVien", teacherId).get();
        try {
            QuerySnapshot classSnapshot = classQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Class studentClass = Class.JSONParser(snapshot.getData());
                if (studentClass != null) {
                    list.add(studentClass);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


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
                        Account student = GeneralService.getInformation(studentId);
                        if (student != null) {
                            studentList.add(student.toJSONObject(false));
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
}
