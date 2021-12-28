package com.example.EnglishCenterBE.models;

import com.google.cloud.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Student {
    String id;
    String name;
    String identityId;
    Date bornDate;
    boolean sex;
    String address;
    String phoneNumber;

    public Student() {

    }

    public static Student JSONParser(Map<String, Object> jo) {
        try {
            Student student = new Student();
            student.id = (String) jo.get("MaHocVien");
            student.name = (String) jo.get("HoTen");
            student.identityId = (String) jo.get("MaCanCuoc");
            student.bornDate = ((Timestamp) jo.get("NgaySinh")).toDate();
            student.sex = (Boolean) jo.get("GioiTinh");
            student.address = (String) jo.get("DiaChi");
            student.phoneNumber = (String) jo.get("SDT");
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> toJSONObject() throws Exception {
        Map<String, Object> jo = new HashMap<>();
        jo.put("MaHocVien", id);
        jo.put("HoTen", name);
        jo.put("MaCanCuoc", identityId);
        jo.put("NgaySinh", bornDate);
        jo.put("GioiTinh", sex);
        jo.put("DiaChi", address);
        jo.put("SDT", phoneNumber);
        return jo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
