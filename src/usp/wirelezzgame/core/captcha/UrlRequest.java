package usp.wirelezzgame.core.captcha;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlRequest {

	  public static String getContents(String s_url) throws IOException{

		URL url=new URL(s_url);
		StringBuffer buffer;
		String line;
		int responseCode;
		HttpURLConnection connection;
		InputStream input;
		BufferedReader dataInput;
		connection = (HttpURLConnection) url.openConnection();
		responseCode = connection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
		  throw new IOException("HTTP response code: " +
		                      String.valueOf(responseCode));
		}
		try {
		  buffer = new StringBuffer();
		  input = connection.getInputStream();
		  dataInput = new BufferedReader(new InputStreamReader(input));
		  while ( (line = dataInput.readLine()) != null) {
		    buffer.append(line);
		    buffer.append("\r\n");
		  }
		  input.close();
		}
		catch (Exception ex) {
		  ex.printStackTrace(System.err);
		  return null;
		}
		return buffer.toString();
	}
}
