package org.spring.springboot;

import java.io.IOException;




import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Test {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "http://localhost:9000/api/city?cityName=广州市";
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			String resString = EntityUtils.toString(entity);
		
			System.out.println("返回的结果："+resString);
			JSONObject fromObject = JSONObject.fromObject(resString);
	        System.out.println(fromObject); 
	        
	        HttpClientHelper http = HttpClientHelper.getInstance();
	        String str = http.doGet(url);
	        System.out.println("doget :"+str);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
