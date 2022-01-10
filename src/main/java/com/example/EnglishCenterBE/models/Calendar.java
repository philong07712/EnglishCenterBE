package com.example.EnglishCenterBE.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Calendar {
	private Map<Integer, List<Integer>> ListDay = new HashMap<Integer, List<Integer>>();
	public void add(weekdays day){
		if (day==null) {}
		else if (ListDay.get(day.getThu())==null) {
				ListDay.put(day.getThu(), day.getTiet());
			}
		else {
			Set<Integer> newSet = new HashSet<Integer>(ListDay.get(day.getThu()));
			newSet.addAll(day.getTiet());
			
			List<Integer> newList = new ArrayList<Integer>(newSet);
			Collections.sort(newList);
			ListDay.put(day.getThu(), newList);
		}
	}
	public void addAll(List<weekdays> list){
		for(weekdays d1: list) {
			add(d1);
		}
	}
	public Map<Integer, List<Integer>> getListDay() {
		return ListDay;
	}
	public void setListDay(Map<Integer, List<Integer>> listDay) {
		ListDay = listDay;
	}
	
}