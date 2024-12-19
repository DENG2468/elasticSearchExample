import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchExample {

    private static final String HOST = "localhost";
    private static final int PORT = 9200;

    public static void main(String[] args) {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(HOST, PORT, "http")))) {

            String indexName = "my_index";
            String documentId = "1";
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("field1", "value1");
            jsonMap.put("field2", 2);

            // 创建IndexRequest并设置source和content type
            IndexRequest indexRequest = new IndexRequest(indexName, documentId);
            indexRequest.source(jsonMap, XContentType.JSON); // 设置source和content type为JSON
            indexRequest.setRefreshPolicy("true");
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("Index response: " + indexResponse.toString());

            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("field1", "value1"));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("Search response: " + searchResponse.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}