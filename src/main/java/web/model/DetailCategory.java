package web.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailCategory {
	private int id;
	private String name;
	private ArrayList<NamesCategoryLang> names ;
	private ArrayList<Pokemon> pokemon_species;

}
