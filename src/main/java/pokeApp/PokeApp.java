package pokeApp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import pokeService.IpokeService;
import pokeService.pokeServiceImp;
import web.model.CategoryPokemons;
import web.model.DetailCategory;
import web.model.ListCatpoki;
import web.model.PkemonDetail;
import web.model.Pokemon;
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

	public ResultDTO getIndex(String paramLang) throws IOException, InterruptedException {
		
		ResultDTO resultDto = new ResultDTO();
		
		if ( paramLang == null  || paramLang.equals("en") ) {
			resultDto.setEnglich(true);
		}

		else if (paramLang.equals("fr")) {
			resultDto.setFrench(true);
		}
			
		return resultDto;
	}
	public ResultDTO getCategoriesPokemens(String paramLang) throws IOException, InterruptedException {
		count = 0;
		pokiFr = null;
		ResultDTO resultDto = new ResultDTO();
		String urlAttachment = "egg-group/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment);
		// string json to java object
		ListCatpoki catepoki = new Gson().fromJson(response.body(), ListCatpoki.class);
		// arraylist of objects Category
		ArrayList<CategoryPokemons> categories = new ArrayList<CategoryPokemons>();
		for (CategoryPokemons poki : catepoki.getResults()) {
			categories.add(poki);
		}
		if (paramLang.equals("en")) {
			resultDto.setCategoriesPokemons(categories);
			resultDto.setEnglich(true);
		}

		if (paramLang.equals("fr")) {
			ArrayList<String> categoriesfr = null;
			try {
			Gson gsons = new Gson();
			
			String fileName="eggs-groups";
			FileInputStream filePath = new FileInputStream("src/main/resources/templates/utileFile/"+fileName+".json");
			InputStreamReader readFile = new InputStreamReader(filePath,Charset.forName ("ISO-8859-1"));
			 BufferedReader reader = new BufferedReader(readFile);
			 

			 java.lang.reflect.Type listType = new TypeToken<ArrayList<CategoryPokemons>>() {}.getType();
			List<CategoryPokemons> poki = gsons.fromJson(reader, listType);
			categoriesfr = new ArrayList<String>();
			for (int i = 0; i < poki.size(); i++) {
				categoriesfr.add(poki.get(i).getName());
			}
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

			/*
			 * for (int i = 1; i <= categories.size(); i++) { String urlAttachment2 =
			 * "egg-group/" + i; HttpResponse<String> responsefr =
			 * pokemonService.getResponce(urlAttachment2);
			 * 
			 * DetailCategory detailCatepoki = new Gson().fromJson(responsefr.body(),
			 * DetailCategory.class); String categorynamefr =
			 * detailCatepoki.getNames().get(2).getName(); categoriesfr.add(categorynamefr);
			 * }
			 */

			resultDto.setStringList(categoriesfr);
			resultDto.setFrench(true);
			resultDto.setEnglich(false);
		}
		return resultDto;
	}

	/*
	 * ArrayList<String> nampokemoneggs1=new ArrayList<String>(); ArrayList<String>
	 * nampokemoneggs2=new ArrayList<String>();
	 */
	public ResultDTO getListPokemons(String pagination, String nomOrNumberGrop, String nomGrop, String paramLang)
			throws IOException, InterruptedException {
		ResultDTO resultDto = new ResultDTO();
		int verifNext = 0;
		ArrayList<Pokemon> listPokimones;
		if (count == 0) {
			if (paramLang.equals("fr")) {
				listPokimones = listpokimones(nomOrNumberGrop);
				/*
				 * for(int i=0;i<listPokimones.size();i++) {
				 * nampokemoneggs1.add(listPokimones.get(i).getName());
				 * 
				 * }
				 */
				// get translation pokemons name
				translateToFr(0, listPokimones.size(), listPokimones, nomGrop);
				/*
				 * executor.execute(new Runnable() {
				 * 
				 * @Override public void run() { try { translateToFr(30, listPokimones.size(),
				 * listPokimones); } catch (IOException | InterruptedException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); } } });
				 */
				count = count + 1;
			} else if (paramLang.equals("en")) {
				String urlAttachment = "egg-group/" + nomOrNumberGrop + "/";
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
		ArrayList<Pokemon> listePokemons = null;
		if (paramLang.equals("fr")) {
			listePokemons = pokiFr;
			resultDto.setFrench(true);
			/*
			 * for(int i=0;i<pokiFr.size();i++) {
			 * nampokemoneggs2.add(pokiFr.get(i).getName()); } for(int
			 * i=0;i<nampokemoneggs1.size();i++) {
			 * System.out.println("{"+'"'+"name"+'"'+":"+'"'+nampokemoneggs2.get(i)+'"'+"},"
			 * ); }
			 */

		} else if (paramLang.equals("en")) {
			listePokemons = listpoki;
			resultDto.setEnglich(true);
		}
		List<Pokemon> elementPage = paginationListe(count, limit, offset, skip, listePokemons);
		verifNext = skip - listePokemons.size();
		resultDto.setNomGroup(nomGrop);
		resultDto.setPage(page);
		resultDto.setPokemons(elementPage);
		resultDto.setCount(count);
		resultDto.setNomeOrnumGroup(nomOrNumberGrop);
		resultDto.setIfHasNext(verifNext);
		return resultDto;
	}

	public ResultDTO getDetailPokemon(String pokiNume, String nomGrop, String numOrNomGrop, String nampoki,
			String paramLang) throws IOException, InterruptedException {

		ResultDTO resultDto = new ResultDTO();
		String urlAttachment1 = "pokemon/" + pokiNume + "/";
		HttpResponse<String> response = pokemonService.getResponce(urlAttachment1);
		PkemonDetail detailpoki = new Gson().fromJson(response.body(), PkemonDetail.class);
		if (paramLang.equals("en")) {
			resultDto.setNomGroup(numOrNomGrop);
			resultDto.setPokemonsDetails(detailpoki);
			resultDto.setEnglich(true);
		} else if (paramLang.equals("fr")) {
			String type = detailpoki.getTypes().get(0).getType().getName();
			String urlAttachment2 = "type/" + type + "/";
			HttpResponse<String> responsef = pokemonService.getResponce(urlAttachment2);
			Type typNam = new Gson().fromJson(responsef.body(), Type.class);
			String typeName = typNam.getNames().get(2).getName();
			resultDto.setNomGroup(nomGrop);
			resultDto.setPokemonsDetails(detailpoki);
			resultDto.setNomeOrnumGroup(numOrNomGrop);
			resultDto.setNomPokemon(nampoki);
			resultDto.setTypeName(typeName);
			resultDto.setFrench(true);
		}
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

	public ArrayList<Pokemon> translateToFr(int i, int limit, ArrayList<Pokemon> listpoki, String nomGrop)
			throws IOException, InterruptedException {
		pokiFr = new ArrayList<Pokemon>();
		Gson gsons = new Gson();
		String fileName = nomGrop;
		FileInputStream filePath = new FileInputStream("src/main/resources/templates/utileFile/" + fileName + ".json");
		InputStreamReader readFile = new InputStreamReader(filePath, Charset.forName("ISO-8859-1"));
		BufferedReader reader = new BufferedReader(readFile);
		java.lang.reflect.Type listType = new TypeToken<ArrayList<Pokemon>>() {
		}.getType();
		List<Pokemon> pokemonsName = gsons.fromJson(reader, listType);
		for (i = 0; i < limit; i++) {
			/*
			 * String urlAttachment = "pokemon-species/" + listpoki.get(i).getName();
			 * HttpResponse<String> responsefr = pokemonService.getResponce(urlAttachment);
			 * PokemonSpacies pokiNames = new Gson().fromJson(responsefr.body(),
			 * PokemonSpacies.class); Pokemon pokinamefr = new Pokemon();
			 * pokinamefr.setName(pokiNames.getNames().get(4).getName());
			 */
			Pokemon pokinamefr = new Pokemon();
			pokinamefr.setName(pokemonsName.get(i).getName());
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
