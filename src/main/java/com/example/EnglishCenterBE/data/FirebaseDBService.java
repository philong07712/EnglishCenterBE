package com.example.EnglishCenterBE.data;

import java.util.HashMap;
import java.util.Map;

import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.models.DataAccount;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseDBService {
	public static FirebaseDBService instance;
	public Firestore db;
	
	public FirebaseDBService getInstance() {
        if (instance == null) instance = new FirebaseDBService();
        return instance;
    }
	
	public FirebaseDBService() {
		 db = FirestoreClient.getFirestore();
	}
	
	public void pushAccount(Account acc) throws Exception {
		Map<String, Object> docData = new HashMap<String, Object>();
		docData = acc.toJSONObject();
		ApiFuture<WriteResult> f = db.collection("TAIKHOAN").document(acc.getUsername()).set(docData);
	}
	
	public Map<String, Object> getAllAccount() {
		CollectionReference accounts = db.collection("TAIKHOAN");
		
		Query query = accounts.whereEqualTo("isActive", true);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		Map<String, Object> json = new HashMap<>();
		try {
			for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
				json.put(document.getId(), document.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return json;
	}
	
	public DataAccount getDataAccount() {
		DataAccount data= new DataAccount();
		DocumentReference docRef = FirestorePathUtil.getPathAccount(db, "DATA");
        try {
            ApiFuture<DocumentSnapshot> snapshot = docRef.get();
            data = DataAccount.JSONParser(snapshot.get().getData());
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public void pushDataAccount(DataAccount data) throws Exception {
		Map<String, Object> docData = new HashMap<String, Object>();
		docData = data.toJSONObject();
		ApiFuture<WriteResult> f = db.collection("TAIKHOAN").document("DATA").set(docData);
	}
	
	public void pushClass(Class lop) throws Exception {
		Map<String, Object> docData = new HashMap<String, Object>();
		docData = lop.toJSONObject();
		ApiFuture<WriteResult> f = db.collection("LOP").document(lop.getId()).set(docData);
	}
	
}
