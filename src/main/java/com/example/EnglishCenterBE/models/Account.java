package com.example.EnglishCenterBE.models;

import com.google.cloud.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private String username;
    private String password;
    private String role;
    private boolean isActive;
    String name;
    String identityId;
    Date bornDate;
    boolean sex;
    String address;
    String phoneNumber;

    public Account() {

    }

    public static Account JSONParser(Map<String, Object> jo) {
        try {
            Account account = new Account();
            account.username = (String) jo.get("TK");
            account.password = (String) jo.get("MK");
            account.role = (String) jo.get("Role");
            account.isActive = (Boolean) jo.get("isActive");
            if (jo.get("HoTen") != null) {
                account.name = (String) jo.get("HoTen");
            }
            if (jo.get("MaCanCuoc") != null) {
                account.identityId = (String) jo.get("MaCanCuoc");
            }
            if (jo.get("NgaySinh") != null) {
                account.bornDate = ((Timestamp) jo.get("NgaySinh")).toDate();
            }
            if (jo.get("GioiTinh") != null) {
                account.sex = (Boolean) jo.get("GioiTinh");
            }
            if (jo.get("DiaChi") != null) {
                account.address = (String) jo.get("DiaChi");
            }
            if (jo.get("SDT") != null) {
                account.phoneNumber = (String) jo.get("SDT");
            }
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
        jo.put("NgaySinh", bornDate);
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
}
