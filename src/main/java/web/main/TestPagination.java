package web.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPagination {
public static void main(String[] args) {
	
	int total=1000;
	
	List<String> elements =new ArrayList<>();
	
	for(int i=0;i<total;i++) {
		elements.add("element :"+i);	
	}
	int page=1;
	int offset=(page*10)-10;
	int limit=offset+10;
	
	
	
	List<String> elementage5=new ArrayList<String>();	
	for(int i=offset;i<limit;i++) {
		elementage5.add(elements.get(i));	
	}
	System.out.println(elementage5);
	

	
	String url = "https://pokeapi.co/api/v2/pokemon-species/25/";
	String[] strs = url.split("[https://pokeapi.co/api/v  /pokemon-species/ /]");

	
	String idPoki=null;
	for (int i = 0; i<strs.length ; i++ ){
		if(!strs[i].isEmpty()) {
			 idPoki=strs[i];
			 if(idPoki.equals("-")) {
				 idPoki="2";
			 }
		}
		} 
	System.out.println(idPoki);
}
}
