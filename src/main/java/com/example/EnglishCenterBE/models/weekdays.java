package com.example.EnglishCenterBE.models;

import java.util.ArrayList;
import java.util.List;

public class weekdays {
	private int Thu;
	private List<Integer> tiet= new ArrayList<Integer>();
	
	public weekdays StringPasre(String s) {
		weekdays day = new weekdays();
		String[] d = s.trim().split(",") ;
		try {
			if (d[0].equals("CN")) day.Thu = 8;
			else if  (d[0].substring(0,1).equals("T"))
				day.Thu = Integer.parseInt(d[0].substring(1));
			else return null;
			
			String[] h = d[1].trim().split("-");
			int Start = Integer.parseInt(h[0]);
			int End = Integer.parseInt(h[1]);
			if (Start >15 || End >15 || Start<1 || End<1 || Start>End) return null;
			else {
				List<Integer> t= new ArrayList<Integer>();
				for (int i= Start;i<= End;i++) t.add(i);
				day.setTiet(t);
			}
			return day;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getThu() {
		return Thu;
	}
	public void setThu(int thu) {
		Thu = thu;
	}
	public List<Integer> getTiet() {
		return tiet;
	}
	public void setTiet(List<Integer> tiet) {
		this.tiet = tiet;
	}
	
}
