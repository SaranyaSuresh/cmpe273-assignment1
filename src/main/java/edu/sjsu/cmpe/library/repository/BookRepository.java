package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Author;

public class BookRepository implements BookRepositoryInterface {
    /** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
    private final ConcurrentHashMap<Long, Book> bookInMemoryMap;
    /** In-memory map to store authors. (Key, Value) -> (ID, Author) */
    
    
    /** Never access this key directly; instead use generateISBNKey() */
    private long isbnKey;
    private int author_id = 0;
    private int review_id = 0;
    
    

    public BookRepository(ConcurrentHashMap<Long, Book> bookMap) {
	checkNotNull(bookMap, "bookMap must not be null for BookRepository");
	bookInMemoryMap = bookMap;
	isbnKey = 0;
	}

    
    
    /**
     * This should be called if and only if you are adding new books to the
     * repository.
     * 
     * @return a new incremental ISBN number
     */
    private final Long generateISBNKey() {
	// increment existing isbnKey and return the new value
	return Long.valueOf(++isbnKey);
    }

    /**
     * This will auto-generate unique ISBN for new books.
     */
    @Override
    public Book saveBook(Book newBook) {
	checkNotNull(newBook, "newBook instance must not be null");
	// Generate new ISBN
	Long isbn = generateISBNKey();
	newBook.setIsbn(isbn);
	        
    for (int i = 0; i<newBook.getAuthors().size(); i++)
    {
    	newBook.getAuthors().get(i).setId(++author_id);
    }
    
    /*for (int j = 0; j<newBook.getReviews().size(); j++)
    {
    	newBook.getReviews().get(j).setId(++review_id);
    }*/
	
    
	bookInMemoryMap.putIfAbsent(isbn, newBook);
	author_id = 0;

	return newBook;
    }
    
       

    /**
     * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
     */
    @Override
    public Book getBookByISBN(Long isbn) {
	checkArgument(isbn > 0,
		"ISBN was %s but expected greater than zero value", isbn);
	return bookInMemoryMap.get(isbn);
	
    }
    
    public int deleteBook(Long isbn) {
    	bookInMemoryMap.remove(isbn);
    	return 0;
    	
    }
    
    public int updateBook(Long isbn, String status_update) {
    	bookInMemoryMap.get(isbn).setStatus(status_update);
    	return 0;
    	
    	
    }

}
