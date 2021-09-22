package pokeService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class pokeServiceImp implements IpokeService {
	private static pokeServiceImp instance = null;

	public static pokeServiceImp getInstance() {
		if (instance == null) {
			instance = new pokeServiceImp();
		}
		return instance;
	}

	// build url and send request GET
	public HttpResponse<String> getResponce(String urlAttachment) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://pokeapi.co/api/v2/").append(urlAttachment);
		String url = stringBuilder.toString();
		System.out.println(url);
		HttpResponse<String> response = null;
		try {
			response = sendreq(url);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<String> sendreq(String url) throws IOException, InterruptedException {
		// request http with http client for java 11
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.GET().build();
		// send request and get response
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
}
