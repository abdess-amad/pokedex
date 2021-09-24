package web.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.model.CategoryPokemons;
import web.model.PkemonDetail;
import web.model.Pokemon;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {
	private ArrayList<CategoryPokemons> categoriesPokemons;
	private List<Pokemon> pokemons;
	private String nomeOrnumGroup;
	private int page;
	private int ifHasNext;
	private int count;
	private PkemonDetail pokemonsDetails;
	private String nomGroup;
	private String typeName;
	private List<String> stringList;
	private String nomPokemon;
	private boolean french = false;
	private boolean englich = false;
}
