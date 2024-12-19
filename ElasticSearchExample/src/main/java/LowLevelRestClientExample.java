import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

public class LowLevelRestClientExample {

    private static final String HOST = "localhost";
    private static final int PORT = 9200;

    public static void main(String[] args) {
        // 创建 RestClient 实例
        try (RestClient restClient = RestClient.builder(
                new HttpHost(HOST, PORT, "http")).build()) {

            // 创建一个 GET 请求来获取集群健康状态
            Request healthRequest = new Request("GET", "/_cluster/health?pretty");

            // 执行请求
            Response response = restClient.performRequest(healthRequest);

            try {
                // 打印响应内容
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}