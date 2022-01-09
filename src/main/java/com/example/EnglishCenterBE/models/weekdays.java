package com.example.EnglishCenterBE.models;

public class weekdays {
	private int Thu;
	private int Start;
	private int End;
	public weekdays StringPasre(String s) {
		weekdays day = new weekdays();
		String[] d = s.trim().split(",") ;
		
		try {
			if (d[0].equals("CN")) Thu =8;
			else if  (d[0].substring(0,1).equals("T"))
				day.Thu = Integer.parseInt(d[0].substring(1));
			else return null;
			
			String[] h = d[1].trim().split("-");
			day.Start = Integer.parseInt(h[0]);
			day.End = Integer.parseInt(h[1]);
			if (day.Start >22 || day.End >22 || day.Start<7 || day.End<7 || day.Start>day.End) return null;
			return day;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Boolean isInvalid(weekdays b) {
		if(b==null) return false;
		if(this.Thu==b.Thu) {
			if(b.Start <= this.Start && this.Start < b.End ) return false;
			if(this.Start <= b.Start && b.Start < this.End ) return false;
		}
		return true;
	}
	public int getThu() {
		return Thu;
	}
	public void setThu(int thu) {
		Thu = thu;
	}
	public int getStart() {
		return Start;
	}
	public void setStart(int start) {
		Start = start;
	}
	public int getEnd() {
		return End;
	}
	public void setEnd(int end) {
		End = end;
	}
	
}
