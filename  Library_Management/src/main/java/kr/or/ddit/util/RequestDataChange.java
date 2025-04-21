package kr.or.ddit.util;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

public class RequestDataChange {

	
	public static String dataChange(HttpServletRequest request) {
		
		
		String line =null;
		StringBuffer strbuf = new StringBuffer();
		BufferedReader reader;
		try {
			reader = request.getReader();
			while(true) {
				line =reader.readLine();
				if(line == null) break;
				
				strbuf.append(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String reqData = strbuf.toString(); 
		return reqData;
	}
}
