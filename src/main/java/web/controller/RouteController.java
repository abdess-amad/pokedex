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
		get("/categories/:paramLang", (req, res) -> categories(req, res), templateEngine);
		get("/categories/:paramLang/:list", (req, res) -> pokiList(req, res), templateEngine);
		get("/categories/:paramLang/:list/:pokiFrDetail", (req, res) -> pokiFrDetail(req, res), templateEngine);
	}

	private ModelAndView categories(Request req, Response res) throws IOException, InterruptedException {
		String paramLang = req.params(":paramLang");
		ResultDTO resultDto = pokeapp.getCategoriesPokemens(paramLang);
		Map<String, Object> map = new HashMap<>();
		map.put("categories", resultDto);
		return new ModelAndView(map, "index.hbs");
	}

	private ModelAndView pokiList(Request req, Response res) throws IOException, InterruptedException {
		String group = req.queryParams("group");
		String nomOrNumberGrop = req.params(":list");
		String paramLang = req.params(":paramLang");
		String pagination = req.queryParamOrDefault("page", "default");
		ResultDTO resultListDto = pokeapp.getListPokemons(pagination, nomOrNumberGrop, group, paramLang);
		Map<String, Object> map = new HashMap<>();
		map.put("listPokemons", resultListDto);
		return new ModelAndView(map, "listPokemon.hbs");
	}

	private ModelAndView pokiFrDetail(Request req, Response res) throws IOException, InterruptedException {
		String paramLang = req.params(":paramLang");
		String numOrNomGrop = req.params(":list");
		String nampoki = req.queryParams("nampoki");
		String group = req.queryParams("group");
		String pokiNume = req.params(":pokiFrDetail");
		ResultDTO resultFrDetail = pokeapp.getDetailPokemon(pokiNume, group, numOrNomGrop, nampoki, paramLang);
		Map<String, Object> map = new HashMap<>();
		map.put("detailPokemon", resultFrDetail);
		return new ModelAndView(map, "DetailPokemon.hbs");
	}
}
