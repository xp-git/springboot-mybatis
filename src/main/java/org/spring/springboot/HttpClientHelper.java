package org.spring.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class HttpClientHelper {
	private static Logger logger = LoggerFactory
			.getLogger(HttpClientHelper.class);
	
	 private RequestConfig config = RequestConfig.custom()  
	            .setSocketTimeout(30000)  
	            .setConnectTimeout(30000)  
	            .setConnectionRequestTimeout(30000)  
	            .build();  
	      
    private static HttpClientHelper instance = null;  
    
    private HttpClientHelper(){}  
   
    public static HttpClientHelper getInstance(){  
        if (instance == null) {  
            instance = new HttpClientHelper();  
        }  
        return instance;  
    } 
	public  <T> T doPost(Map<String, Object> data, String url,
			Class<T> clazz) {
		Gson gson = new Gson();
		String resStr = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			JSONObject fromObject = JSONObject.fromObject(data);
			StringEntity entity2 = new StringEntity(fromObject.toString(),"UTF-8");

			entity2.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(entity2);

			  httppost.setConfig(config);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString(instreams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doPost error=" + e.getMessage().toString());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		T fromJson = gson.fromJson(resStr, clazz);
		return fromJson;
	}

	public  <T> T doGet(String url, Class<T> clazz) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Gson gson = new Gson();
		String resStr = "";
		try {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(config);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString(instreams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doGet error=" + e.getMessage().toString());
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		T fromJson = gson.fromJson(resStr, clazz);
		return fromJson;
	}
	public  String doPost(Map<String, Object> data, String url) {
		String resStr = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			JSONObject fromObject = JSONObject.fromObject(data);
			StringEntity entity2 = new StringEntity(fromObject.toString(),"UTF-8");

			entity2.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(entity2);

		
			  httppost.setConfig(config);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString3(instreams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doPost error=" + e.getMessage().toString());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resStr;
	}
	public  String doPostByJson(String json, String url) {
		String resStr = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			StringEntity entity2 = new StringEntity(json,"UTF-8");
			
			entity2.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(entity2);
			
	
			httppost.setConfig(config);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString3(instreams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doPost error=" + e.getMessage().toString());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resStr;
	}
	public  String doPostByParameters(String url,Map<String,Object> map ){  
		String resStr = "";
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
        try{ 
        	HttpPost httppost = new HttpPost(url);
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,Object> elem = (Entry<String, Object>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue().toString()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");  
                httppost.setEntity(entity);  
            }  
            httppost.setConfig(config);
            HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString3(instreams);
        }catch(Exception ex){  
            ex.printStackTrace();  
        } finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
        return resStr;  
    }  
	public  String  doGet(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resStr = "";
		try {
			HttpGet httpGet = new HttpGet(url);
		
			httpGet.setConfig(config);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString3(instreams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doGet error=" + e.getMessage().toString());
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resStr;
	}
	
	public  <T> T doGetXml(String url, Class<T> clazz) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resStr = "";
		T data = null;
		try {
			HttpGet httpGet = new HttpGet(url);
		
			httpGet.setConfig(config);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
//			resStr = convertStreamToString(instreams);
			data = getObjectFromXML(instreams, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doGet error=" + e.getMessage().toString());
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static <T> T getObjectFromXML(InputStream xml, Class<T> clazz) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object obj = unmarshaller.unmarshal(xml);
        xml.close();
        return (T)obj;
    }
	
	public  <T> T doGetByProxy(String action, HttpHost host,
			Map<String, Object> urlData, Class<T> clazz) {
		Gson gson = new Gson();
		String resStr = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpHost target = new HttpHost(host);
			// HttpHost target = new
			// HttpHost((String)urlData.get("widebandHost"),
			// (Integer)urlData.get("widebandPort"),
			// (String)urlData.get("fiberPathSchema"));
			HttpHost proxy = new HttpHost((String) urlData.get("proxyHost"),
					(Integer) urlData.get("proxyPort"),
					(String) urlData.get("proxySchema"));
			HttpPost httppost = new HttpPost(action);
			httppost.setConfig(config);
		
			HttpResponse response = httpClient.execute(target, httppost);
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString(instreams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doGetByProxy error=" + e.getMessage().toString());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		T fromJson = gson.fromJson(resStr, clazz);
		return fromJson;
	}

	public  <T> T doPostByProxy(Map<String, Object> data, String action,
			HttpHost host, Map<String, Object> urlData, Class<T> clazz) {
		Gson gson = new Gson();
		String resStr = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpHost target = new HttpHost(host);
			// HttpHost target = new
			// HttpHost((String)urlData.get("widebandHost"),
			// (Integer)urlData.get("widebandPort"),
			// (String)urlData.get("fiberPathSchema"));
			HttpHost proxy = new HttpHost((String) urlData.get("proxyHost"),
					(Integer) urlData.get("proxyPort"),
					(String) urlData.get("proxySchema"));
		
			HttpPost httppost = new HttpPost(action);
			httppost.setConfig(config);

			JSONObject fromObject = JSONObject.fromObject(data);
			StringEntity entity2 = new StringEntity(fromObject.toString(),"UTF-8");

			entity2.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(entity2);
			HttpResponse response = httpClient.execute(target, httppost);

			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			resStr = convertStreamToString(instreams);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("doPostByProxy error=" + e.getMessage().toString());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		T fromJson = gson.fromJson(resStr, clazz);
		return fromJson;
	}

	public  String convertStreamToString(InputStream is) {
		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = sb.toString().replace("\\n", "").replace("\\", "");
		// return res.substring(1, res.length()-1);
		return res;
	}

	public  String convertStreamToString2(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = sb.toString().replace("\\n", "").replace(" ", "")
				.replace("\\", "").replace("\"\"", "null");
		// return res.substring(1, res.length()-1);
		return res;
	}
	public  String convertStreamToString3(InputStream is) {
		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
