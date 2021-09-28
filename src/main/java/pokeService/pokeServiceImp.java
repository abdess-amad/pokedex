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

	// enumeration class contains after endpoint constant
	public enum InformationPokemon {

		GROUPS("egg-group/"), LIST("pokemon/"), TYPE("type/");

		private String info;

		private InformationPokemon(String infopoke) {
			info = infopoke;
		}

		public String getInfo() {
			return info;
		}
	}

	// build url and send request GET
	public HttpResponse<String> getResponce(String urlAttachment, String param) {
		StringBuilder stringBuilder = new StringBuilder();
		// param can be any parameter on api pokeapi, if param is nul is not necesserie
		// to append in url
		if (param == null) {
			stringBuilder.append("https://pokeapi.co/api/v2/").append(urlAttachment);
		} else {
			stringBuilder.append("https://pokeapi.co/api/v2/").append(urlAttachment).append(param);
		}
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
