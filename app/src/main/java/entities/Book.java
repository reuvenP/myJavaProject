package entities;

/**
 * Created by shmuel on 08/03/2016.
 */
public class Book {

	// variables
	String title;
	int bookID;
	int year;
	String author;
	int pages;
	Category category;



	// constructor
	public Book(String title, int year, String author, int pages, Category category) {
		this.title = title;
		this.year = year;
		this.author = author;
		this.pages = pages;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", bookID=" + bookID +
				", year=" + year +
				", author='" + author + '\'' +
				", pages=" + pages +
				", category=" + category +
				'}';
	}

	// getters and setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBookID() {
		return this.bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
