package com.example.EnglishCenterBE.models;

import java.util.HashMap;
import java.util.Map;

public class Score {
    private String id;
    private String studentId;
    private String classId;
    private String className;
    private String studentName;
    private double docScore = -1;
    private double noiScore = -1;
    private double vietScore = -1;
    private double ngheScore = -1;
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
            if (jo.get("TenHocVien") != null) {
                score.studentName = (String) jo.get("TenHocVien");
            }
            if (jo.get("DiemDoc") != null) {
                score.docScore = Double.parseDouble((String) jo.get("DiemDoc"));
            }
            if (jo.get("DiemNoi") != null) {
                score.noiScore = Double.parseDouble((String) jo.get("DiemNoi"));
            }
            if (jo.get("DiemViet") != null) {
                score.vietScore = Double.parseDouble((String) jo.get("DiemViet"));
            }
            if (jo.get("DiemNghe") != null) {
                score.ngheScore = Double.parseDouble((String) jo.get("DiemNghe"));
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
        jo.put("TenHocVien", studentName);
        if (docScore != -1) {
            jo.put("DiemDoc", docScore + "");
        }
        if (noiScore != -1) {
            jo.put("DiemNoi", noiScore + "");
        }
        if (vietScore != -1) {
            jo.put("DiemViet", vietScore + "");
        }
        if (ngheScore != -1) {
            jo.put("DiemNghe", ngheScore + "");
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

    public double getDocScore() {
        return docScore;
    }

    public void setDocScore(double docScore) {
        this.docScore = docScore;
    }

    public double getNoiScore() {
        return noiScore;
    }

    public void setNoiScore(double noiScore) {
        this.noiScore = noiScore;
    }

    public double getVietScore() {
        return vietScore;
    }

    public void setVietScore(double vietScore) {
        this.vietScore = vietScore;
    }

    public double getNgheScore() {
        return ngheScore;
    }

    public void setNgheScore(double ngheScore) {
        this.ngheScore = ngheScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
