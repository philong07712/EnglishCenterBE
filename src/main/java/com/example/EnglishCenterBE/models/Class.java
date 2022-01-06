package com.example.EnglishCenterBE.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class {
    private String id;
    private String teacherId;
    private List<String> students;
    private String name;
    private String room;
    private String time;
    private String teacherName;
    public Class() {

    }

    public static Class JSONParser(Map<String, Object> jo) {
        try {
            Class studentClass = new Class();
            studentClass.id = (String) jo.get("MaLop");
            studentClass.teacherId = (String) jo.get("MaGiangVien");
            studentClass.name = (String) jo.get("TenLop");
            studentClass.room = (String) jo.get("PhongHoc");
            studentClass.time = (String) jo.get("GioHoc");
            studentClass.teacherName = (String) jo.get("TenGiangVien");
            if (jo.get("HocVien") != null) {
                studentClass.students = (List<String>) jo.get("HocVien");
            }
            System.out.println("Error here");
            return studentClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> toJSONObject() throws Exception {
        Map<String, Object> jo = new HashMap<>();
        jo.put("MaLop", id);
        jo.put("MaGiangVien", teacherId);
        jo.put("TenLop", name);
        jo.put("PhongHoc", room);
        jo.put("GioHoc", time);
        jo.put("TenGiangVien", teacherName);
        jo.put("HocVien", students);

        return jo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
