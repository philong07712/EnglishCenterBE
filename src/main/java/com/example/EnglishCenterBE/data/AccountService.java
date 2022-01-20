package com.example.EnglishCenterBE.data;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.EnglishCenterBE.models.Account;
import com.example.EnglishCenterBE.models.DataAccount;
import com.example.EnglishCenterBE.utils.FirestorePathUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class AccountService {
	public static AccountService instance;
	public Firestore db;
	
	public static AccountService getInstance() {
        if (instance == null) instance = new AccountService();
        return instance;
    }
	
	private AccountService() {
		db = FirestoreClient.getFirestore();
	}
	
	public String AddAccountService(String json) {
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(json);
			String Role =(String) obj.get("Role");
			long Num = (long) obj.get("Num");
			FirebaseDBService fBDService = new FirebaseDBService().getInstance();
			DataAccount data =	fBDService.getDataAccount();
			Map<String, Object> jo = new HashMap<>();
			long lastId =0;
			String header="";
			switch (Role) {
			case "Admin":
				header = "AD";
				lastId = data.getAdmin();
				data.setAdmin(lastId+Num);
				break;
			case "Manager":
				header = "QL";
				lastId = data.getManager();
				data.setManager(lastId+Num);
				break;
			case "Teacher":
				header = "GV";
				lastId = data.getTeacher();
				data.setTeacher(lastId+Num);
				break;
			default:
				header = "HV";
				lastId = data.getStudent();
				data.setStudent(lastId+Num);
				break;
			}
			Account acc = new Account();
			acc.setRole(Role);
			acc.setActive(true);
			
			for (int i=1;i<=Num;i++) {
				String newTK= header + Long.toString(1000000+lastId+i);
				acc.setUsername(newTK);
				acc.setPassword(newTK);
				fBDService.pushAccount(acc);
			}
			fBDService.pushDataAccount(data);
			return header + Long.toString(1000000+lastId+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	public Account getAccount(String UserName) {
        if (UserName != null) {
        	DocumentReference docRef = FirestorePathUtil.getPathAccount(db, UserName);
    		try {
                ApiFuture<DocumentSnapshot> snapshot = docRef.get();
                if(snapshot.get().exists()==false) return null;
                Account acc  = Account.JSONParser(snapshot.get().getData());
                return acc;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
	
	public Boolean updateAccount(String jo) {
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			Account acc = getAccount((String) obj.get("TK"));
			if (obj.get("MK")!=null)
				acc.setPassword((String) obj.get("MK"));
			if (obj.get("HoTen")!=null)
				acc.setName((String) obj.get("HoTen"));
			if (obj.get("MaCanCuoc")!=null)
				acc.setIdentityId((String) obj.get("MaCanCuoc"));
			if (obj.get("NgaySinh")!=null) {
				if (obj.get("NgaySinh") instanceof String) {
					String dateString = (String) obj.get("NgaySinh");
					acc.setBornDate(Timestamp.parseTimestamp(dateString).toDate());
				} else {
					acc.setBornDate(((Timestamp) obj.get("NgaySinh")).toDate());
				}
			}
			if (obj.get("GioiTinh")!=null)
				acc.setSex((Boolean) obj.get("GioiTinh"));
			if (obj.get("DiaChi")!=null)
				acc.setAddress((String) obj.get("DiaChi"));
			if (obj.get("SDT")!=null)
				acc.setPhoneNumber((String) obj.get("SDT"));
			FirebaseDBService fBDService = new FirebaseDBService().getInstance();
			fBDService.pushAccount(acc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateAccountGeneral(String requestUsername, String jo) {
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(jo);
			String username = (String) obj.get("TK");
			if (requestUsername.equals(username)) {
				return updateAccount(jo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean deleteAccount(String UserName) {
		try {
			
			if(UserName.equals("Admin")) return false;
			Account acc = getAccount(UserName);
			acc.setActive(false);
			FirebaseDBService fBDService = new FirebaseDBService().getInstance();
			fBDService.pushAccount(acc);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
