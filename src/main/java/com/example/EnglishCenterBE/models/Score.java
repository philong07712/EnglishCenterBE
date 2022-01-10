package com.example.EnglishCenterBE.models;

import java.util.HashMap;
import java.util.Map;

public class Score {
    private String id;
    private String studentId;
    private String classId;
    private String className;
    private double btScore = -1;
    private double ckScore = -1;
    private double gkScore = -1;
    private double tbScore = -1;

    public Score() {

    }

    public static Score JSONParser(Map<String, Object> jo) {
        try {
            Score score = new Score();
            if (jo.get("ID") != null) {
                score.id = (String) jo.get("ID");
            }
            if (jo.get("MaHocVien") != null) {
                score.studentId = (String) jo.get("MaHocVien");
            }
            if (jo.get("MaLop") != null) {
                score.classId = (String) jo.get("MaLop");
            }
            if (jo.get("TenLop") != null) {
                score.className = (String) jo.get("TenLop");
            }
            if (jo.get("DiemBT") != null) {
                score.btScore = Double.parseDouble((String) jo.get("DiemBT"));
            }
            if (jo.get("DiemCK") != null) {
                score.ckScore = Double.parseDouble((String) jo.get("DiemCK"));
            }
            if (jo.get("DiemGK") != null) {
                score.gkScore = Double.parseDouble((String) jo.get("DiemGK"));
            }
            if (jo.get("DiemTB") != null) {
                score.tbScore = Double.parseDouble((String) jo.get("DiemTB"));
            }
            return score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> toJSONObject() throws Exception {
        Map<String, Object> jo = new HashMap<>();
        jo.put("ID", id);
        jo.put("MaHocVien", studentId);
        jo.put("MaLop", classId);
        jo.put("TenLop", className);
        if (btScore != -1) {
            jo.put("DiemBT", btScore + "");
        }
        if (ckScore != -1) {
            jo.put("DiemCK", ckScore + "");
        }
        if (gkScore != -1) {
            jo.put("DiemGK", gkScore + "");
        }
        if (tbScore != -1) {
            jo.put("DiemTB", tbScore + "");
        }
        return jo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getBtScore() {
        return btScore;
    }

    public void setBtScore(double btScore) {
        this.btScore = btScore;
    }

    public double getCkScore() {
        return ckScore;
    }

    public void setCkScore(double ckScore) {
        this.ckScore = ckScore;
    }

    public double getGkScore() {
        return gkScore;
    }

    public void setGkScore(double gkScore) {
        this.gkScore = gkScore;
    }

    public double getTbScore() {
        return tbScore;
    }

    public void setTbScore(double tbScore) {
        this.tbScore = tbScore;
    }
}
