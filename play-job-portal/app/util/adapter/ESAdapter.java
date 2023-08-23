package util.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.typesafe.config.Config;
import jakarta.inject.Inject;
import play.libs.Json;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ESAdapter {
    private final String url;

    @Inject
    public ESAdapter(Config config) {
        url = config.getString("elasticsearch.url");
    }

    public JsonNode get(String index, JsonNode query) {
        String uri = String.format("%s/%s/_search", url, index);

        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(
                        HttpRequest.newBuilder()
                            .uri(new URI(uri))
                            .header("Content-Type", "application/json")
                            .method("GET", HttpRequest.BodyPublishers.ofString(query.toString()))
                            .build(),
                        HttpResponse.BodyHandlers.ofString()
                    );

            ArrayNode hits = (ArrayNode) Json.parse(response.body()).get("hits").get("hits");

            if (hits.isEmpty()) return hits;

            List<JsonNode> listNode = new ArrayList<>();
            for (JsonNode hit : hits) listNode.add(hit.get("_source"));

            return Json.toJson(listNode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
