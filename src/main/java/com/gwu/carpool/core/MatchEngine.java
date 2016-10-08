package com.gwu.carpool.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class MatchEngine{
	public static void main(String[] args) {
		System.out.println(compare("washington","chicago"));
	}
	
	
	public static float compare(String str1,String str2){
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json";
		String param = "origins="+str1.replaceAll(" ", "")+"&destinations="+str2.replaceAll(" ", "");
		String result = sendGet(url,param);
		System.out.println(result);
		String r[] = result.split("\"");
		for(String str : r){
			if(str.contains("km")){
				String sa[] = str.split(" ");
				String a = sa[0].replaceAll(",", "");
				System.out.println(a);
				return Float.parseFloat(a);
			}
		}
		return -1;
	}
	 public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            URLConnection connection = realUrl.openConnection();
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.connect();
	            Map<String, List<String>> map = connection.getHeaderFields();
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	            	System.err.println(line);
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("fucked" + e);
	            e.printStackTrace();
	        }
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
}