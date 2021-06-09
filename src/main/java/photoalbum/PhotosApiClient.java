package photoalbum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PhotosApiClient {

    private final HttpClient httpClient;
    private final String apiUrl;

    public PhotosApiClient(String apiUrl) {
        this.httpClient =  HttpClient.newBuilder().build();
        this.apiUrl = apiUrl;
    }

    public List<Photo> getAlbum(Integer albumId) throws ExecutionException, InterruptedException, JsonProcessingException {

        String json = makeHttpRequest(apiUrl + "?albumId=" + albumId);
        ObjectMapper mapper = new ObjectMapper();
        Photo[] photos = mapper.readValue(json, Photo[].class);

        return Arrays.asList(photos);
    }

    public String makeHttpRequest(String url) throws ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();
    }
}
