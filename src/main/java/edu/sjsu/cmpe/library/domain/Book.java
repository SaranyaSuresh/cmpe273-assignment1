package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe.library.dto.LinkDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;

@JsonPropertyOrder({ "isbn", "title", "publication-date", "language", "num-pages", "status", "reviews", "authors"})
public class Book {
    private long isbn;
    
    @JsonProperty("title")
    @NotBlank(message="Title of Book cannot be blank.")
    private String title;
    @JsonProperty("publication-date")
    @NotEmpty(message="Publication date needs to be specified.")
    private String publication_date;
    private String language;
    @JsonProperty("num-pages")
    private String num_pages;
    private String status;
    private List<Author> authors = new ArrayList<Author>();
    private List<Review> reviews = new ArrayList<Review>();
      
    public Book(){
    	this.status = "available";
    }
    
   	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	/**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    public String getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(String num_pages) {
		this.num_pages = num_pages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

		/**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
}
