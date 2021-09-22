package web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {
	
	private String idPoki;
	private String name;
	private String url;
	private String imageUrl;

	public String returnImage() {
		
	  	  String[] strs = url.split("[https://pokeapi.co/api/v /pokemon-species/ /]");
	  	String idPoki=null;
	  	for (int i1 = 0; i1<strs.length ; i1++ ){
			if(!strs[i1].isEmpty()) {
				 idPoki=strs[i1];
			}
			} 
	  	this.idPoki=idPoki;
	  	imageUrl="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/"+idPoki+".svg";
		return imageUrl;
	}
	
}
