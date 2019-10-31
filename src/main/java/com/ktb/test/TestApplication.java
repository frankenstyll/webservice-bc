package com.ktb.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestApplication {
	
	public static void main(String[] args) {

		try {

			//url for call webservice 
			URL url = new URL("https://glacial-peak-48383.herokuapp.com/bcbot/pushMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			String input = "{\"userId\":\"Ue0f8a270ffea40064588037b51272d28\","
					+ "\"messages\": ["
					+ "{\"type\": \"text\", 	\"text\": \"เรียน BC XXX XXXX\"} ,"
					+ "{\"type\": \"text\", \"text\": \"ลูกค้าของท่านเลยกำหนดระยะเวลา\"}, "
					+ "{\"type\": \"text\", \"text\": \"รบกวนติดต่อสอบถาม\"} ]"
					+ "}";

			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {
			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }

	}
}
