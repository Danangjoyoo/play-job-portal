package util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import play.libs.Json;
import play.mvc.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RequestMapper {
    private final Http.Request request;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final JsonNode json;
    private final Map<String, String> forms;
    private final Http.MultipartFormData<Object> files;

    public RequestMapper(Http.Request request) {
        this.request = request;
        this.headers = this.extractHeader(request.getHeaders());
        this.queryParams = this.convertToSingleValueMap(request.queryString());
        this.forms = this.convertToSingleValueMap(request.body().asFormUrlEncoded());
        this.json = this.extractJson();
        this.files = request.body().asMultipartFormData();
    }

    private Map<String, String> convertToSingleValueMap(Map<String, String[]> inputMap) {
        Map<String, String> result = new HashMap<>();

        if (inputMap == null) return result;

        for (Map.Entry<String, String[]> entry : inputMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

    private Map<String, String> extractHeader(Http.Headers headers) {
        Map<String, List<String>> inputMap = headers.asMap();
        Map<String, String> result = new HashMap<>();

        if (inputMap == null) return result;

        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().get(0));
        }
        return result;
    }

    private JsonNode extractJson() {
        JsonNode json = request.body().asJson();
        json = json != null?json:Json.toJson(new HashMap<String, Object>());

        return json;
    }
}
