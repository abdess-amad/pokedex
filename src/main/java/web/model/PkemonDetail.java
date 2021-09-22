package web.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PkemonDetail {
	private int id;
	private String name;
	private int base_experience;
	private int height;
	private Sprites sprites;
	private int weight;
	// 1:Hit Points ,2:attack,3:defense,4:special-attack,5:special-defense,6:speed
	private List<PokemonStats> stats;
	private List<Types>types;
}
