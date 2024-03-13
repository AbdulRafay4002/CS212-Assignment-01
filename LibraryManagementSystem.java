import java.util.Scanner;
import java.util.ArrayList;

class Book{
    private int bookID;
    private String title;
    private String author;
    private String genre;
    private boolean availability;

    public Book(int bookID, String title, String author, String genre){
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = true; 
    }

    // Getters and setters
    public int getBookID(){
        return bookID;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getGenre(){
        return genre;
    }

    public boolean isAvailable(){
        return availability;
    }

    public void setAvailability(boolean availability){
        this.availability = availability;
    }
}

class User{
    private int userID;
    private String name;
    private String contactInfo;
    private ArrayList<Integer> borrowedBooks;

    public User(int userID, String name, String contactInfo){
        this.userID = userID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and setters
    public int getUserID(){
        return userID;
    }

    public String getName(){
        return name;
    }

    public String getContactInfo(){
        return contactInfo;
    }

    public ArrayList<Integer> getBorrowedBooks(){
        return borrowedBooks;
    }

    public void addBorrowedBook(int bookID){
        borrowedBooks.add(bookID);
    }

    public void returnBook(int bookID){
        borrowedBooks.remove(Integer.valueOf(bookID));
    }
}

class Library{
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Library(){
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Methods to add books and users
    public void addBook(Book book){
        books.add(book);
    }

    public void addUser(User user){
        users.add(user);
    }

    // Method for checking out books
    public void checkOutBook(int bookID, int userID){
        Book book = findBookByID(bookID);
        if(book != null && book.isAvailable()){
            User user = findUserByID(userID);
            if(user != null){
                book.setAvailability(false);
                user.addBorrowedBook(bookID);
                System.out.println("Book checked out successfully.");
            } 
            else{
                System.out.println("User not found.");
            }
        } 
        else{
            System.out.println("Book not available.");
        }
    }

    // Method for returning books
    public void returnBook(int bookID, int userID){
        Book book = findBookByID(bookID);
        if(book != null && !book.isAvailable()){
            User user = findUserByID(userID);
            if(user != null && user.getBorrowedBooks().contains(bookID)){
                book.setAvailability(true);
                user.returnBook(bookID);
                System.out.println("Book returned successfully.");
            } 
            else{
                System.out.println("User hasn't borrowed this book.");
            }
        } 
        else{
            System.out.println("Book not found or already available.");
        }
    }

    // Method for searching books by title or author
    public void searchBook(String keyword){
        ArrayList<Book> searchResults = new ArrayList<>();
        for(Book book : books){
            if(book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(book);
            }
        }
        if(!searchResults.isEmpty()){
            System.out.println("Search Results:");
            for(Book book : searchResults){
                System.out.println(book.getBookID() + " - " + book.getTitle() + " by " + book.getAuthor());
            }
        } 
        else{
            System.out.println("No matching books found.");
        }
    }

    // Helper method to find book by ID
    private Book findBookByID(int bookID){
        for(Book book : books){
            if(book.getBookID() == bookID){
                return book;
            }
        }
        return null;
    }

    // Helper method to find user by ID
    private User findUserByID(int userID){
        for(User user : users){
            if(user.getUserID() == userID){
                return user;
            }
        }
        return null;
    }

    // Method to display all books
    public void displayBooks(){
        System.out.println("Books in Library:");
        for(Book book : books){
            System.out.println(book.getBookID() + " - " + book.getTitle() + " by " + book.getAuthor() +
                    " (" + (book.isAvailable() ? "Available" : "Not Available") + ")");
        }
    }
}

public class LibraryManagementSystem{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Sample data for testing
        library.addBook(new Book(1, "Treasure Island", "Robert Lewis Stevenson", "Adventure"));
        library.addBook(new Book(2, "Prince and the pauper", "Mark Twain", "Classic"));
        library.addUser(new User(45, "Ali", "ali@example.com"));
        library.addUser(new User(77, "Bilal", "bilal@example.com"));

        int choice;
        do{
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Display Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Search Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch(choice){
                case 1:
                    // Add Book
                    System.out.print("Enter book ID: ");
                    int bookID = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    library.addBook(new Book(bookID, title, author, genre));
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    // Add User
                    System.out.print("Enter user ID: ");
                    int userID = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter contact information: ");
                    String contactInfo = scanner.nextLine();
                    library.addUser(new User(userID, name, contactInfo));
                    System.out.println("User added successfully.");
                    break;
                case 3:
                    // Display Books
                    library.displayBooks();
                    break;
                case 4:
                    // Borrow Book
                    System.out.print("Enter book ID to borrow: ");
                    int borrowBookID = scanner.nextInt();
                    System.out.print("Enter user ID: ");
                    int borrowUserID = scanner.nextInt();
                    library.checkOutBook(borrowBookID, borrowUserID);
                    break;
                case 5:
                    // Return Book
                    System.out.print("Enter book ID to return: ");
                    int returnBookID = scanner.nextInt();
                    System.out.print("Enter user ID: ");
                    int returnUserID = scanner.nextInt();
                    library.returnBook(returnBookID, returnUserID);
                    break;
                case 6:
                    // Search Books
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    library.searchBook(keyword);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select one of the above choices.");
            }
        } while(choice != 7);

        scanner.close();
    }
}
