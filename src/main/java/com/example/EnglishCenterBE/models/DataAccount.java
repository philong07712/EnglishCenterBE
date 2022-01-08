package com.example.EnglishCenterBE.models;

import java.util.HashMap;
import java.util.Map;

public class DataAccount {
	private long Admin ;
	private long Manager ;
	private long Teacher ;
	private long Student ;
	
	public DataAccount() {
		
	}
	
	public static DataAccount JSONParser(Map<String, Object> jo) {
        try {
        	DataAccount data = new DataAccount();
        	data.Admin = (long) jo.get("Admin");
        	data.Manager = (long) jo.get("Manager");
        	data.Teacher = (long) jo.get("Teacher");
        	data.Student = (long) jo.get("Student");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> toJSONObject() throws Exception {
        Map<String, Object> jo = new HashMap<>();
        jo.put("Admin", Admin);
        jo.put("Manager", Manager);
        jo.put("Teacher", Teacher);
        jo.put("Student", Student);
        return jo;
    }

	public long getAdmin() {
		return Admin;
	}

	public void setAdmin(long admin) {
		Admin = admin;
	}

	public long getManager() {
		return Manager;
	}

	public void setManager(long manager) {
		Manager = manager;
	}

	public long getTeacher() {
		return Teacher;
	}

	public void setTeacher(long teacher) {
		Teacher = teacher;
	}

	public long getStudent() {
		return Student;
	}

	public void setStudent(long student) {
		Student = student;
	}
}
