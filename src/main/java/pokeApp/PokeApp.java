package pokeApp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.http.MetaData.Request;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pokeService.IpokeService;
import pokeService.pokeServiceImp;
import web.model.CategoryPokemons;
import web.model.DetailCategory;
import web.model.ListCatpoki;
import web.model.NamesCategoryLang;
import web.model.PkemonDetail;
import web.model.Pokemon;
import web.model.PokemonSpacies;
import web.model.Type;
import web.model.dto.ResultDTO;

@Getter
@Setter

public class PokeApp {
	private static PokeApp instance = null;

	public static PokeApp getInstance() {
		if (instance == null) {
			instance = new PokeApp();
		}
		return instance;
	}

	int count;
	ArrayList<Pokemon> listpoki;
	ArrayList<Pokemon> pokiFr;
	IpokeService pokemonService = pokeServiceImp.getInstance();
	ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	public ResultDTO getCategoriesPokemens() throws IOException, InterruptedException {
		count = 0;
		ResultDTO resultListDto = new ResultDTO();
		String urlAttachment = "egg-group/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment);
		// string json to java object
		ListCatpoki catepoki = new Gson().fromJson(response.body(), ListCatpoki.class);
		// arraylist of objects Category
		ArrayList<CategoryPokemons> categories = new ArrayList<CategoryPokemons>();
		for (CategoryPokemons poki : catepoki.getResults()) {
			categories.add(poki);
		}
		resultListDto.setCategoriesPokemons(categories);
		return resultListDto;
	}
	
	public ResultDTO getDetailPokemon(String pokiName, String nomGrop) throws IOException, InterruptedException {
		ResultDTO resultDto = new ResultDTO();
		String urlAttachment = "pokemon/" + pokiName + "/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment);
		PkemonDetail detailpoki = new Gson().fromJson(response.body(), PkemonDetail.class);
		resultDto.setNomeGroup(nomGrop);
		resultDto.setPokemonsDetails(detailpoki);
		return resultDto;
	}

	public ResultDTO getCategoriesFrenchPokemens() throws IOException, InterruptedException {
		count = 0;
		pokiFr = null;
		ResultDTO resultDto = new ResultDTO();
		String urlAttachment1 = "egg-group/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment1);
		ListCatpoki catepoki = new Gson().fromJson(response.body(), ListCatpoki.class);
		ArrayList<CategoryPokemons> categoryfr = new ArrayList<CategoryPokemons>();
		for (CategoryPokemons poki : catepoki.getResults()) {
			categoryfr.add(poki);
		}
		ArrayList<String> categories = new ArrayList<String>();
		for (int i = 1; i <= categoryfr.size(); i++) {
			String urlAttachment2 = "egg-group/" + i;
			HttpResponse<String> responsefr = pokemonService.getResponce(urlAttachment2);

			DetailCategory detailCatepoki = new Gson().fromJson(responsefr.body(), DetailCategory.class);
			String categorynamefr = detailCatepoki.getNames().get(2).getName();
			categories.add(categorynamefr);
		}
		resultDto.setStringList(categories);

		return resultDto;
	}

	public ResultDTO getListPokemons(String pagination, String numGrop, String nomGrop)
			throws IOException, InterruptedException {
		ResultDTO resultDto = new ResultDTO();
		int verifNext = 0;
		ArrayList<Pokemon> listPokimones;
		if (count == 0) {
		if(numGrop !=null) {
			listPokimones = listpokimones(numGrop);
			// get translation pokemons name
			translateToFr(0, 30, listPokimones);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						translateToFr(30, listPokimones.size(), listPokimones);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			count = count + 1;
		}else {		
			String urlAttachment = "egg-group/" + nomGrop + "/";
			HttpResponse<String> response = pokemonService.getResponce(urlAttachment);
			DetailCategory detailCatepoki = new Gson().fromJson(response.body(), DetailCategory.class);
			listpoki = new ArrayList<Pokemon>();
			for (Pokemon Pokemons : detailCatepoki.getPokemon_species()) {
				listpoki.add(Pokemons);
				// for activate method returned url image
				Pokemons.returnImage();
			}
			count = count + 1;
		}
			
		}
		// pagination
		if (pagination.equals("next")) {
			count = count + 1;
		}
		if (pagination.equals("prev")) {
			count = count - 1;
		}
		int page = count;
		int limit = 12;
		int offset = (page * limit) - limit;
		int skip = offset + limit;
		ArrayList<Pokemon> listePokemons;
		if(numGrop !=null) {
			listePokemons=pokiFr;
		}else {
			listePokemons=listpoki;
		}
		List<Pokemon> elementPage = paginationListe(count, limit, offset, skip, listePokemons);	
		verifNext = skip - listePokemons.size();
		resultDto.setNumGroup(numGrop);
		resultDto.setPage(page);
		resultDto.setPokemons(elementPage);
		resultDto.setCount(count);
		resultDto.setNomeGroup(nomGrop);
		resultDto.setIfHasNext(verifNext);
		return resultDto;
	}

	public ResultDTO getDetailFrenchPokemon(String pokiNume, String nomGrop, String numGrop, String nampoki)
			throws IOException, InterruptedException {
		ResultDTO resultDto = new ResultDTO();
		String urlAttachment1 = "pokemon/" + pokiNume + "/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment1);
		PkemonDetail detailFrpoki = new Gson().fromJson(response.body(), PkemonDetail.class);
		String type = detailFrpoki.getTypes().get(0).getType().getName();
		String urlAttachment2 = "type/" + type + "/";
		HttpResponse<String> responsef = pokemonService.getResponce(urlAttachment2);
		Type typNam = new Gson().fromJson(responsef.body(), Type.class);
		String typeName = typNam.getNames().get(2).getName();
		resultDto.setNomeGroup(nomGrop);
		resultDto.setPokemonsDetails(detailFrpoki);
		resultDto.setNumGroup(numGrop);
		resultDto.setNomPokemon(nampoki);
		resultDto.setTypeName(typeName);
		return resultDto;
	}

	public List<Pokemon> paginationListe(int count, int limit, int offset, int skip, List<Pokemon> listpoki) {

		List<Pokemon> elementage5 = new ArrayList<Pokemon>();
		if (skip > listpoki.size()) {
			for (int i = offset; i < listpoki.size(); i++) {
				elementage5.add(listpoki.get(i));
			}
		} else if (offset < 0) {
			for (int i = 0; i < limit; i++) {
				elementage5.add(listpoki.get(i));
			}
		}

		else {

			for (int i = offset; i < skip; i++) {
				elementage5.add(listpoki.get(i));
			}
		}
		return elementage5;
	}

	public ArrayList<Pokemon> listpokimones(String numgrop) throws IOException, InterruptedException {
		String urlAttachment = "egg-group/" + numgrop + "/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment);
		DetailCategory detailCatepoki = new Gson().fromJson(response.body(), DetailCategory.class);
		// filtre pokemons name
		ArrayList<Pokemon> listpoki = new ArrayList<Pokemon>();
		for (Pokemon Pokemons : detailCatepoki.getPokemon_species()) {
			listpoki.add(Pokemons);
		}
		return listpoki;
	}

	public ArrayList<Pokemon> translateToFr(int i, int limit, ArrayList<Pokemon> listpoki)
			throws IOException, InterruptedException {
		pokiFr = new ArrayList<Pokemon>();
		for (i = 0; i < limit; i++) {
			String urlAttachment = "pokemon-species/" + listpoki.get(i).getName();
			HttpResponse<String> responsefr = pokemonService.getResponce(urlAttachment);
			PokemonSpacies pokiNames = new Gson().fromJson(responsefr.body(), PokemonSpacies.class);
			Pokemon pokinamefr = new Pokemon();
			pokinamefr.setName(pokiNames.getNames().get(4).getName());
			String url = listpoki.get(i).getUrl();
			String[] strs = url.split("[https://pokeapi.co/api/v /pokemon-species/ /]");
			String idPoki = null;
			for (int i1 = 0; i1 < strs.length; i1++) {
				if (!strs[i1].isEmpty()) {
					idPoki = strs[i1];
				}
			}
			pokinamefr.setUrl(idPoki);
			pokinamefr.setImageUrl(
					"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/"
							+ idPoki + ".svg");

			pokiFr.add(pokinamefr);
		}
		return pokiFr;
	}

}
