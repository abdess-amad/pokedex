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
		get("/category", (req, res) -> category(req, res), templateEngine);
		get("/category/:list", (req, res) -> pokiList(req, res), templateEngine);
		get("/category/:list/:pokiName", (req, res) -> pokidetail(req, res), templateEngine);
		get("/categories/:fr", (req, res) -> categoryFr(req, res), templateEngine);
		get("/categories/:fr/:list", (req, res) -> pokiFrList(req, res), templateEngine);
		get("/categories/:fr/:list/:pokiFrDetail", (req, res) -> pokiFrDetail(req, res), templateEngine);
	}

	private ModelAndView category(Request req, Response res) throws IOException, InterruptedException {
		Map<String, Object> map = new HashMap<>();
		ResultDTO resultListDto = pokeapp.getCategoriesPokemens();
		map.put("categories", resultListDto);
		return new ModelAndView(map, "index.hbs");
	}

	private ModelAndView pokiList(Request req, Response res) throws IOException, InterruptedException {
		String nomGrop = req.params(":list");
		String pagination = req.queryParamOrDefault("page", "default");
		ResultDTO resultListDto = pokeapp.getListPokemons(pagination,null, nomGrop);
		Map<String, Object> map = new HashMap<>();
		map.put("dataPokemons", resultListDto);
		return new ModelAndView(map, "listPokemon.hbs");
	}

	private ModelAndView pokidetail(Request req, Response res) throws IOException, InterruptedException {
		String nomGrop = req.params(":list");
		String pokiName = req.params(":pokiName");
		ResultDTO resultDto = pokeapp.getDetailPokemon(pokiName, nomGrop);
		Map<String, Object> map = new HashMap<>();
		map.put("dataPokemon", resultDto);
		return new ModelAndView(map, "DetailPokemon.hbs");
	}

	private ModelAndView categoryFr(Request req, Response res) throws IOException, InterruptedException {
		ResultDTO resultDto = pokeapp.getCategoriesFrenchPokemens();
		Map<String, Object> map = new HashMap<>();
		map.put("categoriesFrench", resultDto);
		return new ModelAndView(map, "index.hbs");
	}

	private ModelAndView pokiFrList(Request req, Response res) throws IOException, InterruptedException {
		String group = req.queryParams("group");
		String numGrop = req.params(":list");
		String pagination = req.queryParamOrDefault("page", "default");
		ResultDTO resultListDto = pokeapp.getListPokemons(pagination, numGrop, group);
		Map<String, Object> map = new HashMap<>();
		map.put("listFrenchPokemons", resultListDto);
		return new ModelAndView(map, "listPokemon.hbs");
	}

	private ModelAndView pokiFrDetail(Request req, Response res) throws IOException, InterruptedException {
		String numGrop = req.params(":list");
		String nampoki = req.queryParams("nampoki");
		String group = req.queryParams("group");
		String pokiNume = req.params(":pokiFrDetail");
		ResultDTO resultFrDetail = pokeapp.getDetailFrenchPokemon(pokiNume, group, numGrop, nampoki);
		Map<String, Object> map = new HashMap<>();
		map.put("resultFrDetail", resultFrDetail);
		return new ModelAndView(map, "DetailPokemon.hbs");
	}
}
