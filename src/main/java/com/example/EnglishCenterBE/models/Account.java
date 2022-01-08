package com.example.EnglishCenterBE.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.Timestamp;

public class Account {
    private String username;
    private String password;
    private String role;
    private boolean isActive;
    private String name;
    private String identityId;
    private Date bornDate;
    private boolean sex;
    private String address;
    private String phoneNumber;
    
    public Account() {

    }

    public static Account JSONParser(Map<String, Object> jo) {
        try {
            Account account = new Account();
            account.username = (String) jo.get("TK");
            account.password = (String) jo.get("MK");
            account.role = (String) jo.get("Role");
            account.isActive = (Boolean) jo.get("isActive");
            account.name = (String) jo.get("HoTen");
            account.identityId = (String) jo.get("MaCanCuoc");
            if(jo.get("NgaySinh")!=null)
            	account.bornDate = ((Timestamp) jo.get("NgaySinh")).toDate();
            if(jo.get("GioiTinh")!=null)
            	account.sex = (Boolean) jo.get("GioiTinh");
            account.address = (String) jo.get("DiaChi");
            account.phoneNumber = (String) jo.get("SDT");
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> toJSONObject() throws Exception {
        Map<String, Object> jo = new HashMap<>();
        jo.put("TK", username);
        jo.put("MK", password);
        jo.put("Role", role);
        jo.put("isActive", isActive);
        jo.put("HoTen", name);
        jo.put("MaCanCuoc", identityId);
        jo.put("NgaySinh",bornDate);
        jo.put("GioiTinh", sex);
        jo.put("DiaChi", address);
        jo.put("SDT", phoneNumber);
        return jo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
