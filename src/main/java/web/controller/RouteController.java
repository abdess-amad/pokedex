package web.controller;

import static spark.Spark.get;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import pokeApp.PokeApp;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;
import web.model.CategoryPokemons;
import web.model.DetailCategory;
import web.model.ListCatpoki;
import web.model.NamesCategoryLang;
import web.model.PkemonDetail;
import web.model.Pokemon;
import web.model.PokemonSpacies;
import web.model.Type;
import web.model.dto.ResultDTO;

import static spark.Spark.*;

public class RouteController {
	public static TemplateEngine templateEngine = new HandlebarsTemplateEngine();
	PokeApp pokeapp = PokeApp.getInstance();

	public void init() {
		port(8080);
		staticFileLocation("templates");
		get("/", (req, res) -> index(req, res), templateEngine);
		get("/categories/:sitelangue", (req, res) -> categoriesPokemons(req, res), templateEngine);
		get("/categories/:sitelangue/:list", (req, res) -> listPokemons(req, res), templateEngine);
		get("/categories/:sitelangue/:list/:detailPokemons", (req, res) -> detailPokemons(req, res), templateEngine);
	}

	private ModelAndView categoriesPokemons(Request req, Response res) throws IOException, InterruptedException {
		String sitelangue = req.params(":sitelangue");
		ResultDTO resultDto = pokeapp.getCategoriesPokemens(sitelangue);
		Map<String, Object> map = new HashMap<>();
		map.put("categories", resultDto);
		map.put("sitelangue", sitelangue);
		return new ModelAndView(map, "index.hbs");
	}

	private ModelAndView listPokemons(Request req, Response res) throws IOException, InterruptedException {
		String group = req.queryParams("group");
		String nomOrNumberGrop = req.params(":list");
		String sitelangue = req.params(":sitelangue");
		String pagination = req.queryParamOrDefault("page", "default");
		ResultDTO resultListDto = pokeapp.getListPokemons(pagination, nomOrNumberGrop, group, sitelangue);
		Map<String, Object> map = new HashMap<>();
		map.put("listPokemons", resultListDto);
		map.put("sitelangue", sitelangue);

		return new ModelAndView(map, "listPokemon.hbs");
	}

	private ModelAndView detailPokemons(Request req, Response res) throws IOException, InterruptedException {
		String sitelangue = req.params(":sitelangue");
		String numOrNomGrop = req.params(":list");
		String nampoki = req.queryParams("nampoki");
		String group = req.queryParams("group");
		String pokiNume = req.params(":detailPokemons");
		ResultDTO resultFrDetail = pokeapp.getDetailPokemon(pokiNume, group, numOrNomGrop, nampoki, sitelangue);
		Map<String, Object> map = new HashMap<>();
		map.put("detailPokemon", resultFrDetail);
		map.put("sitelangue", sitelangue);
		return new ModelAndView(map, "DetailPokemon.hbs");
	}

	private ModelAndView index(Request req, Response res) throws IOException, InterruptedException {
		Map<String, Object> map = new HashMap<>();
		String sitelangue = req.queryParams("locale");
		ResultDTO resulLangue = pokeapp.getIndex(sitelangue);
		if (sitelangue == null) {
			sitelangue = "en";
		}
		map.put("language", resulLangue);
		map.put("sitelangue", sitelangue);
		return new ModelAndView(map, "home.hbs");
	}
}
