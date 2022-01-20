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
            if (jo.get("MK") != null) {
                account.password = (String) jo.get("MK");
            }
            if (jo.get("Role") != null) {
                account.role = (String) jo.get("Role");
            }
            if (jo.get("isActive") != null) {
                account.isActive = (Boolean) jo.get("isActive");
            }
            if (jo.get("HoTen") != null) {
                account.name = (String) jo.get("HoTen");
            }
            if (jo.get("MaCanCuoc") != null) {
                account.identityId = (String) jo.get("MaCanCuoc");
            }
            if (jo.get("NgaySinh") != null) {
                if (jo.get("NgaySinh") instanceof String) {
                    String dateString = (String) jo.get("NgaySinh");
                    account.bornDate = Timestamp.parseTimestamp(dateString).toDate();
                } else {
                    account.bornDate = ((Timestamp) jo.get("NgaySinh")).toDate();
                }
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

    public Map<String, Object> toJSONObject(boolean isFullData) throws Exception {
        Map<String, Object> jo = new HashMap<>();
        jo.put("TK", username);
        // hide private information
        if (isFullData) {
            jo.put("MK", password);
            jo.put("MaCanCuoc", identityId);
            jo.put("Role", role);
            jo.put("isActive", isActive);
        }
        jo.put("HoTen", name);
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
