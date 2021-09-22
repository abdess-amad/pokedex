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
public class ListCatpoki {

	private int count;
	private String next;
	private String previous;
	private ArrayList<CategoryPokemons> results;
}
