package com.example.EnglishCenterBE.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.Calendar;
import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

public class ManagerService {
	public static ManagerService instance;
	public static Firestore db;
	
	public ManagerService getInstance() {
        if (instance == null) instance = new ManagerService();
        return instance;
    }
	
	public ManagerService() {
		db = FirestoreClient.getFirestore();
	}
	
	public boolean addClass(String jo) {
		calendarService check = new calendarService().getInstance();
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			Class lop = (Class) Class.JSONParser(obj);
			
			if (getClass(lop.getId())!=null) {
				  	return false;
				}
			if (!check.CheckCalendar(lop.getTime(), lop.getTeacherId(),new ArrayList<String>(), lop.getId())) 
				return false;
			else {
					new FirebaseDBService().getInstance().pushClass(lop);
					return true;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static Class getClass(String id) {
		if (id != null) {
        	DocumentReference docRef = FirestorePathUtil.getPathClassDetail(db, id);
    		try {
                ApiFuture<DocumentSnapshot> snapshot = docRef.get();
                if(snapshot.get().exists()==false) return null;
                Class lop  = Class.JSONParser(snapshot.get().getData());
                return lop;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	
	public boolean updateInforClass(String id,String jo) {
		calendarService check = new calendarService().getInstance();
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			Class lop = getClass(id);
			if(lop!=null) {
				boolean kq;
				if(lop.getStudents()!=null && lop.getStudents().size()!=0)
					kq= check.CheckCalendar((String) obj.get("GioHoc"), (String) obj.get("MaGiangVien"), lop.getStudents(), id);
				else
					kq= check.CheckCalendar((String) obj.get("GioHoc"), (String) obj.get("MaGiangVien"), new ArrayList<String>(), id);
				
				if (!kq) return false;
				
				lop.setId((String) obj.get("MaLop"));
				lop.setName((String) obj.get("TenLop"));
				lop.setTeacherId((String) obj.get("MaGiangVien"));
				lop.setTeacherName((String) obj.get("TenGiangVien"));
				lop.setRoom((String) obj.get("PhongHoc"));
				lop.setTime((String) obj.get("GioHoc"));
			
				new FirebaseDBService().getInstance().pushClass(lop);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteClass(String id) {
		try {
			Class lop = getClass(id);
			if(lop!=null) {
				lop.setActive(false);
				new FirebaseDBService().getInstance().pushClass(lop);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addStudents(String id, String jo) {
		calendarService check = new calendarService().getInstance();
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			List<String> newStudents = new ArrayList<String>();
			newStudents = (List<String>) obj.get("students");
			
			for(String idHV: newStudents) {
        		if (AccountService.getInstance().getAccount(idHV)==null) return false;
        	}
			
			if(newStudents.size()==0) return false;
			Set<String> newSet = new HashSet<String>(newStudents);
			
			Class lop = getClass(id);
			if(lop==null) return false;
			
			if (lop.getStudents() != null && lop.getStudents().size()>0) {
				newSet.addAll(lop.getStudents());
			}
		    
		    List<String> newList = new ArrayList<String>(newSet);
		    Collections.sort(newList);
			lop.setStudents(newList);
			
			if(lop.getStudents()!=null && lop.getStudents().size()!=0)	
				if (!check.CheckCalendar(lop.getTime(),"",lop.getStudents(), id))
					return false;
			
			new FirebaseDBService().getInstance().pushClass(lop);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteStudents(String id, String jo) {
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			List<String> deleteListStudents = new ArrayList<String>();
			deleteListStudents = (List<String>) obj.get("deleteListStudents");
			
			Class lop = getClass(id);
			List<String> newList = lop.getStudents();
			newList.removeAll(deleteListStudents);
			lop.setStudents(newList);
			new FirebaseDBService().getInstance().pushClass(lop);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<Class> getAllClass(){
		List<Class> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathClass(db);
        ApiFuture<QuerySnapshot> classQuery = collectionRef.whereEqualTo("isActive", true).get();
        try {
            QuerySnapshot classSnapshot = classQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Class lop = Class.JSONParser(snapshot.getData());
                if (lop != null) {
                    list.add(lop);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } 
		return null;
	}
	
	public static List<Class> searchClassByName(String para){
		List<Class> list = new ArrayList<>();
        CollectionReference collectionRef = FirestorePathUtil.getPathClass(db);
        ApiFuture<QuerySnapshot> classQuery = collectionRef.whereEqualTo("isActive", true).get();
        try {
            QuerySnapshot classSnapshot = classQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Class lop = Class.JSONParser(snapshot.getData());
                if (lop != null && lop.getName().contains(para)) {
                    list.add(lop);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
	}
	public static List<Map<String, String>> getAllAccountByRole(String Role){
		List<Map<String, String>> list = new ArrayList<>();
		
		CollectionReference collectionRef = db.collection("TAIKHOAN");
        ApiFuture<QuerySnapshot> classQuery = collectionRef.whereEqualTo("isActive", true).whereEqualTo("Role", Role).get();
        try {
            QuerySnapshot classSnapshot = classQuery.get();
            for (QueryDocumentSnapshot snapshot : classSnapshot.getDocuments()) {
                Account acc = Account.JSONParser(snapshot.getData());
                if (acc != null ) {
                    Map<String, String> k = new HashMap<String, String>();
                    k.put("TK", acc.getUsername());
                    if (acc.getName()==null) acc.setName("");
                    k.put("UserName", acc.getName());
                    list.add(k);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
	}
	
	public static Calendar getCalender(String jo){
		Calendar ca = new Calendar();
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			ca = (new calendarService()).getInstance().createCalendar((String) obj.get("MaGiangVien"), (List<String>) obj.get("students"),(String) obj.get("MaLop") );
			return ca;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        return null;
	}
}
