package com.example.EnglishCenterBE.data;

import java.util.ArrayList;
import java.util.List;

import com.example.EnglishCenterBE.models.Calendar;
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
	
	public static void main(String[] args) {
		//test
		weekdays d1 = new weekdays().StringPasre("T3,7-8");
		weekdays d2 = new weekdays().StringPasre("T3,10-13");
		weekdays d3 = new weekdays().StringPasre("T4,9-11");
		weekdays d4 = new weekdays().StringPasre("T5,9-9");
		Calendar ca= new Calendar();
		ca.add(d1);
		ca.add(d2);
		ca.add(d3);
		List<weekdays> k = new ArrayList<weekdays>();
		k.add(d4);
		System.out.println(new calendarService().CheckList(k, ca));
	}
	
	private boolean CheckList(List<weekdays> input, Calendar ca) {
		if (ca.getListDay().size()==0) return true;
		for (weekdays a : input) {
			if (ca.getListDay().get(a.getThu())!=null) {
				List<Integer> k = ca.getListDay().get(a.getThu());
				for (Integer i: a.getTiet())
					if (k.contains(i)) return false;
			}
		}
		return true;
	}
	
	private List<weekdays> ToListDay(List<String> input) {
		List<weekdays> in = new ArrayList<weekdays>();
		for (String inDay: input) {
			weekdays day = new weekdays().StringPasre(inDay);
			if(day != null) in.add(day);
			else return null;
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
	
	public boolean CheckCalendar(String input,String teacherId,List<String> students,String idClass) {
		if (input.trim().isEmpty()) return true;
		
		List<weekdays> inputListDay = ToListDay(ToListString(input));
		if(inputListDay==null) return false;
		
		Calendar ca = createCalendar(teacherId, students, idClass);
		
		System.out.println("ca:");
		if (ca.getListDay().size()!=0)
		{
			System.out.println(ca.getListDay());
		}
		System.out.println("end");
		
		return CheckList(inputListDay, ca);
	}
	
	public Calendar createCalendar(String teacherId,List<String> students,String idClass) {
		Calendar ca = new Calendar();
		if (!teacherId.isEmpty()) {
			List<Class> classes = ClassService.getClassListWithTeacher(teacherId);
			
			if(classes!=null)
			for (Class lop:classes) {
				if(lop.isActive() && lop.getTime()!=null && !lop.getTime().isEmpty() && !lop.getId().equals(idClass) && ToListDay(ToListString(lop.getTime()))!=null) {
					List<weekdays> k = ToListDay(ToListString(lop.getTime()));
					if (k.size()!=0) ca.addAll(k);
				}
			}
		}
		if (students!=null && students.size()!=0)
		for(String student: students) {
			List<Class> lops = ClassService.getClassList(student);
			for (Class lop:lops) {
				if(lop.getTime()!=null && !lop.getTime().isEmpty() && !lop.getId().equals(idClass)) {
					List<weekdays> k = ToListDay(ToListString(lop.getTime()));
					ca.addAll(k);
				}
			}
		}
		return ca;
	}
	
}
