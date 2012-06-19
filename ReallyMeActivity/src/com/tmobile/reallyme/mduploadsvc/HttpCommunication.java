package com.tmobile.reallyme.mduploadsvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import android.util.Log;
import java.net.HttpURLConnection;

import com.tmobile.reallyme.ReallyMeActivity;

public class HttpCommunication {
	String TAG = "HN";
	String result = "";
	String content = "";
	boolean responsecode = false;

	public boolean sendMetaData(String data) {

		content = data;
		// Log.v(TAG, "in sendData");
		try {

			String URL = ReallyMeActivity.metaDataApiURL + ReallyMeActivity.pCid.replace("+", "");
			result = post(URL, content);
			responsecode = true;

		} catch (Exception e) {
			responsecode = false;
		}
		return responsecode;

	}

	public boolean uploadPhoneBook(String data) {

		content = data;

		try {

			result = post(ReallyMeActivity.phoneBookApiURL, content);
			responsecode = true;

		} catch (Exception e) {
			responsecode = false;
		}
		return responsecode;

	}

	private String post(String urlString, String content) throws IOException {


		
		URL url = new URL(urlString);
		
		Log.v("HN", "XML is: " + content);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		byte[] buff;
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type","text/xml");

		InputStream in = null;
		OutputStream out;

		
		String postData = content.replace("&", "");
		out = con.getOutputStream();
		buff = postData.getBytes("ASCII");
		
		out.write(buff);
		out.flush();
		out.close();
		//Log.v("HereNow", "after post");
		in = con.getInputStream();
		int ch = 0;
		StringBuffer sb = new StringBuffer();
		
		while ((ch = in.read()) != -1) {
			sb.append((char) ch);
		}

		in.close();
		con.disconnect();
		Log.v("HN", "response RM: " + sb.toString());
		return sb.toString();

	}

}
