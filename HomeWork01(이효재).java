package kr.or.ddit.homework01;

import java.util.Date;

public class HomeWorkPr01 {
	public static void main(String[] args) {
		
		Date date = new Date();
		
		long time = date.getTime();
		
		System.out.println("Time = " + time);
		// x년 x일 x시 x분 x초 지났습니다.
		
		
		long times = time / 1000;
		
		long years = times / (60 * 60 * 24 * 365); //몫
			System.out.println(years);
			
			times = times - (60 * 60 * 24 * 365) * years; //나머지
		
		long days = times / (60 * 60 * 24); //몫
			System.out.println(days);
			
			times = times - (60 * 60 * 24) * days; //나머지
			
		long hours = times / (60 * 60); //몫
			System.out.println(hours);
			
			times = times - (60 * 60) * hours; //나머지
			 
		long mins = times / 60; //몫
			System.out.println(mins);
			
			times = times - 60 * mins;
				System.out.println(times); //나머지 초
				
		System.out.println(years + "년 " + days + "일 " + hours + "일 " + mins +"분 " + times + "초 지났습니다.");
			
			
			
			
			
	}
}
