package httpclient;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by hll on 2016/3/13.
 */
public class HttpClientTest {

  @Test
  public void testStatusCode() throws IOException {
    CloseableHttpClient client = HttpClientBuilder.create().build();
    CloseableHttpResponse response = client.execute(new HttpGet("http://ifeve.com/"));
    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println(statusCode);

    System.out.println(EntityUtils.toString(response.getEntity()));

    assert statusCode==HttpStatus.SC_OK;
  }
}
