package com.example.EnglishCenterBE.models;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private String username;
    private String password;
    private String role;
    private boolean isActive;

    public Account() {

    }

    public static Account JSONParser(Map<String, Object> jo) {
        try {
            Account account = new Account();
            account.username = (String) jo.get("TK");
            account.password = (String) jo.get("MK");
            account.role = (String) jo.get("Role");
            account.isActive = (Boolean) jo.get("isActive");
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
