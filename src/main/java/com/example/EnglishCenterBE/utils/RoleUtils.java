package com.example.EnglishCenterBE.utils;

public class RoleUtils {
    public static String getRoleFromUsername(String subject) {
        String subRole = subject.substring(0, 2);
        return convertSubRoleToRole(subRole);
    }

    public static String convertSubRoleToRole(String subRole) {
        if (subRole.equals(Constants.SubRole.ADMIN)) {
            return Constants.Role.ADMIN;
        }
        if (subRole.equals(Constants.SubRole.MANAGER)) {
            return Constants.Role.MANAGER;
        }
        if (subRole.equals(Constants.SubRole.TEACHER)) {
            return Constants.Role.TEACHER;
        }
        if (subRole.equals(Constants.SubRole.STUDENT)) {
            return Constants.Role.STUDENT;
        }
        return null;
    }
}
