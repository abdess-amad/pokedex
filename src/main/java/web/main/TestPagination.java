package web.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jknack.handlebars.internal.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import web.model.Pokemon;

public class TestPagination {
	public static void main(String[] args) {

		/*
		 * int total=1000;
		 * 
		 * List<String> elements =new ArrayList<>();
		 * 
		 * for(int i=0;i<total;i++) { elements.add("element :"+i); } int page=1; int
		 * offset=(page*10)-10; int limit=offset+10;
		 * 
		 * 
		 * 
		 * List<String> elementage5=new ArrayList<String>(); for(int
		 * i=offset;i<limit;i++) { elementage5.add(elements.get(i)); }
		 * System.out.println(elementage5);
		 * 
		 * 
		 * 
		 * String url = "https://pokeapi.co/api/v2/pokemon-species/25/"; String[] strs =
		 * url.split("[https://pokeapi.co/api/v  /pokemon-species/ /]");
		 * 
		 * 
		 * String idPoki=null; for (int i = 0; i<strs.length ; i++ ){
		 * if(!strs[i].isEmpty()) { idPoki=strs[i]; if(idPoki.equals("-")) { idPoki="2";
		 * } } } System.out.println(idPoki);
		 */

		try {
			// create Gson instance
			Gson gsons = new Gson();

			// create a reader
			// Reader readers =
			// java.nio.file.Files.newBufferedReader(Paths.get("src/main/resources/templates/monstre.json"),
			// StandardCharsets.UTF_8);

			String fileName="Inconnu";
			FileInputStream filePath = new FileInputStream("src/main/resources/templates/utileFile/"+fileName+".json");
			InputStreamReader readFile = new InputStreamReader(filePath,Charset.forName ("ISO-8859-1"));
			 BufferedReader reader = new BufferedReader(readFile);
			 

			Type listType = new TypeToken<ArrayList<Pokemon>>() {}.getType();
			List<Pokemon> poki = gsons.fromJson(reader, listType);
			for (int i = 0; i < poki.size(); i++) {
				System.out.println(poki.get(i).getName());
			}
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
