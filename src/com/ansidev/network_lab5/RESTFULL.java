package com.ansidev.network_lab5;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class RESTFULL {
	public static int flag;
	public static HttpEntity doGet(final String url) {
		HttpEntity httpEntity = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 200) {
				httpEntity = httpResponse.getEntity();
				org.apache.http.Header mHeader = httpEntity.getContentType();
				if(mHeader.getValue().contains("image/")) {
					flag = 1;
				}
				else if(mHeader.getValue().contains("text/")){
					flag = 0;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpEntity;
	}
}
