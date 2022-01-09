package com.example.EnglishCenterBE.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.EnglishCenterBE.models.Class;
import com.example.EnglishCenterBE.models.weekdays;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class calendarService {
	public static calendarService instance;
	public Firestore db = FirestoreClient.getFirestore();
	public calendarService getInstance() {
		 if (instance == null) instance = new calendarService();
	        return instance;
	}
	
	private boolean CheckList(List<weekdays> input, List<weekdays> data) {
		if (data.size()==0) return true;
		for (weekdays a : input) {
			for(weekdays b: data) {
				if (!a.isInvalid(b)) return false;
			}
		}
		return true;
	}
	
	private List<weekdays> ToListDay(List<String> input) {
		List<weekdays> in = new ArrayList<weekdays>();
		for (String inDay: input) {
			weekdays day = new weekdays().StringPasre(inDay);
			if(day != null) in.add(day);
		}
		return in;
	}
	
	private List<String> ToListString(String input) {
		String[] inDays = input.trim().split(" ");
		List<String> kq = new ArrayList<String>();
		for (String inDay : inDays) {
			kq.add(inDay);
		}
		return kq;
	}
	
	public boolean CheckCalendarTeacher(String input,String teacherId,String idClass) {
		List<Class> classes = ClassService.getClassListWithTeacher(teacherId);
		Set<String> newSet = new HashSet<String>();
		for (Class lop:classes) {
			if(lop.getTime()!=null && !lop.getTime().isEmpty() && !lop.getId().equals(idClass)) {
				List<String> day = ToListString(lop.getTime());
				newSet.addAll(day);
			}
		}
		List<String> newList = new ArrayList<String>(newSet);
		List<weekdays> teacherListTime = ToListDay(newList);  
		List<weekdays> in = ToListDay(ToListString(input));
		return CheckList(in,teacherListTime);
	}
	
	public boolean CheckCalendarStudents(String input, List<String> students,String idClass) {
		Set<String> newSet = new HashSet<String>();
		for(String student: students) {
			List<Class> classes = ClassService.getClassList(student);
			for (Class lop:classes) {
				if(lop.getTime()!=null && !lop.getTime().isEmpty()) {
					List<String> day = ToListString(lop.getTime());
					newSet.addAll(day);
				}
			}
		}
		List<String> newList = new ArrayList<String>(newSet);
		List<weekdays> teacherListTime = ToListDay(newList);  
		List<weekdays> in = ToListDay(ToListString(input));
		return CheckList(in,teacherListTime);
	}
	
	
}
