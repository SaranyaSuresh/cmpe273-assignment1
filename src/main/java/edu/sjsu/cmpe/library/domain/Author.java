package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;

import edu.sjsu.cmpe.library.dto.LinkDto;

public class Author {

	private long id;
	@NotBlank(message="Author name needs to be specified.")
    private String name;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}
