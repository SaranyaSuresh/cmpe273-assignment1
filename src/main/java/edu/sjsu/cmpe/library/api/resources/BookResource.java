package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;
import javax.validation.Valid;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.domain.Author;


import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;
    

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }
    
    /** Create Book API **/
    @POST
    @Timed(name = "create-book")
    public Response createBook(@Valid Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);
	String location =  "/books/" + savedBook.getIsbn();
	LinksDto bookResponse = new LinksDto();
	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", location, "POST"));
	return Response.status(201).entity(bookResponse).build();
	
    }

    /** View Book API **/
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	BookDto bookResponse = new BookDto(book);
	String location = "/books/" + book.getIsbn();
	bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	bookResponse.addLink(new LinkDto("update-book", location, "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", location, "POST"));
	if (book.getReviews().size() > 0) {
		//bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() +"/reviews/", "GET"));
		bookResponse.addLink(new LinkDto("view-all-reviews", location +"/reviews", "GET"));
	}
	return bookResponse;
	
    }

    /** Delete Book API **/
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBook(@PathParam("isbn") LongParam isbn) {
    	if(bookRepository.deleteBook(isbn.get()) != 0) {
    		return Response.status(404).entity("Book could not be deleted. Please check the isbn number.").build();
    		
    	}
    	
    	LinksDto response = new LinksDto();
    	response.addLink(new LinkDto("create-book", "/books", "POST"));
    	return Response.status(200).entity(response).build();
    }
    
    /** Update Book API **/
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBook(@PathParam("isbn") LongParam isbn, @QueryParam("status") String status) {
    	if(bookRepository.updateBook(isbn.get(), status) != 0) {
    		return Response.status(404).entity("Book could not be updated. Please check the isbn number.").build();
    	}
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	LinksDto updateResponse = new LinksDto();
    	updateResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
    	updateResponse.addLink(new LinkDto("update-book", "/books/" + book.getIsbn(), "PUT"));
    	updateResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
    	updateResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn(), "POST"));
    	if (book.getReviews().size() > 0) {
    		updateResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() +"/reviews", "GET"));
    	}
    	return Response.status(200).entity(updateResponse).build();
    	
    }
    
    
    /** Create Book Review API **/
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createReview(@PathParam("isbn") LongParam isbn, @Valid Review request) {
       	Book book = bookRepository.getBookByISBN(isbn.get());
    	book.getReviews().add(request);
    	String location = "/books/" + book.getIsbn() + "/reviews/" + request.getId() ;
    	LinksDto reviewResponse = new LinksDto();
    	reviewResponse.addLink(new LinkDto("view-review", location, "GET"));
       	return Response.status(201).entity(reviewResponse).build();
        	
    }
    
    /** View Book Review API **/
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-review")
    public Response viewReview(@PathParam("isbn") LongParam isbn, @PathParam("id") int rev_id) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	ReviewDto reviewResponse = new ReviewDto(book.getReviews().get(rev_id-1));
    	String location = "/books" + book.getIsbn() + "/reviews/" + rev_id;
       	reviewResponse.addLink(new LinkDto("view-review", location, "GET"));
       	return Response.status(200).entity(reviewResponse).build();
       		
    }
        
    /** View All Reviews API **/
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-review")
    public Response viewReview(@PathParam("isbn") LongParam isbn) {
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	ReviewsDto reviewResponse = new ReviewsDto(book.getReviews());
       	return Response.status(200).entity(reviewResponse).build();
       		
    }	
    
    /** View Book Author API **/
    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name = "view-author")
    public Response viewAuthor(@PathParam("isbn") LongParam isbn, @PathParam("id") int author_id) {
    Book book = bookRepository.getBookByISBN(isbn.get());
    AuthorDto authorResponse = new AuthorDto(book.getAuthors().get(author_id-1));
    String location = "/books/" + book.getIsbn() + "/authors/" + author_id;    
    authorResponse.addLink(new LinkDto("view-author", location, "GET"));
    return Response.status(200).entity(authorResponse).build(); 
    }
    
    /** View All Authors of the Book API **/
    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-author")
    public Response viewAllAuthor(@PathParam("isbn") LongParam isbn) {
    Book book = bookRepository.getBookByISBN(isbn.get());
    AuthorsDto authorResponse = new AuthorsDto(book.getAuthors());
    return Response.status(200).entity(authorResponse).build(); 
    }
        
}

