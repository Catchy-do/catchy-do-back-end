package com.main.catchy.utils.http;
//package com.Catchy.Utils.http;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
////import org.apache.http.HttpEntity;
////import org.apache.http.client.methods.CloseableHttpResponse;
////import org.apache.http.client.methods.HttpGet;
////import org.apache.http.impl.client.CloseableHttpClient;
////import org.apache.http.impl.client.HttpClients;
////import org.apache.http.util.EntityUtils;
//
//import io.micrometer.core.instrument.util.IOUtils;
//
//public class HttpClientApi {
//	public String sendD17GetStatus(String link) throws IOException {
//		URL url = new URL(link);
//		String result=null;
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestProperty("accept", "application/json");
//		connection.setRequestProperty("Authorization", "Basic 066Ei2DZ220@+791");
//		//connection.setRequestProperty("cookie", "_ga=GA1.2.86800630.1606832836; __auc=9989580d1761eb34f473362417b; _fbp=fb.1.1606832836477.1950894693; medreview=1; _gid=GA1.2.1588380524.1608502605; PHPSESSID=093a27bbf8517d31de00935ed5fe0187; __asc=17b514a71768b6705d27789a74c; ubid=66bf3352252dd27c1cc1f17109a65fc3; _gat=1; _gat_myTracker=1");
//		connection.setDoOutput(true);
//		//connection.getOutputStream().write(params.getBytes("UTF-8"));
//		connection.setRequestMethod("GET");
//
//
//		InputStream responseStream = connection.getInputStream();
//		result = IOUtils.toString(responseStream, StandardCharsets.UTF_8);
//		//System.out.println("Restult ="+result);
//		connection.disconnect();
//		return result;
//	}
//
//}
