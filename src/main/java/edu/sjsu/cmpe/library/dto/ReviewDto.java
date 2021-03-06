package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"review", "links"})
public class ReviewDto extends LinksDto {
	private Review review;
	
	public ReviewDto (Review review) {
		super();
		this.review = review;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
	
	

}
