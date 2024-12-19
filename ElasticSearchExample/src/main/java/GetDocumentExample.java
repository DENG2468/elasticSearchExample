import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.apache.http.HttpHost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetDocumentExample {

    private static final String HOST = "localhost";
    private static final int PORT = 9200;

    public static void main(String[] args) {
        // 创建RestHighLevelClient实例
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(HOST, PORT, "http")))) {

            // 创建索引
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("my_index2");
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            System.out.println("Index created with status: " + createIndexResponse.isAcknowledged());

            // 索引文档
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("field1", "value1");
            jsonMap.put("field2", 2);
            IndexRequest indexRequest = new IndexRequest("my_index", "1");
            indexRequest.source(jsonMap);
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("Document indexed with status: " + indexResponse.status());

            // 获取文档
            GetRequest getRequest = new GetRequest("my_index", "1");
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            System.out.println("Document retrieved: " + getResponse.getSourceAsString());

            // 搜索文档
            SearchRequest searchRequest = new SearchRequest("my_index");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("field1", "value1"));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("Search response: " + searchResponse.toString());

            // 打印搜索结果
            for (SearchHit hit : searchResponse.getHits()) {
                System.out.println(hit.getSourceAsString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
