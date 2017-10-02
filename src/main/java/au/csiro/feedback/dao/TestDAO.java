package au.csiro.feedback.dao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class TestDAO {
	private static String metadataUrl = "http://localhost:8080/csiro-feedback-service/list/";
	public static void main(String[] args) throws UnsupportedEncodingException {
		String doi = "10.4225/08/50863D7E652C5";
		String getURL = metadataUrl + doi ;

		try {
			HttpParams params = new BasicHttpParams();
		    HttpProtocolParams.setContentCharset(params, "UTF-8");
		    DefaultHttpClient httpclient = new DefaultHttpClient(params);
		    //String url=getURL.replace(".", "%2E");
		    //System.out.println(url);
			HttpGet getRequest = new HttpGet(getURL);
	
			System.out.println("Executing request " + getRequest.getRequestLine());
			HttpResponse response = httpclient.execute(getRequest);
			int code = response.getStatusLine().getStatusCode();
			System.out.println("Response:" +code);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpclient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}


	}

}
