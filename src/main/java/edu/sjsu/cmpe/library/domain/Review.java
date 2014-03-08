package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

public class Review {
	private int id;
	@NotNull(message="Rating needs to be specified.")
	@Range(min=1, max=5)
	private int rating;
	@NotBlank(message="Comments need to be provided.")
	private String comment;
	private static int review_id = 0;
	
	
	public Review() {
		this.id = ++review_id;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
