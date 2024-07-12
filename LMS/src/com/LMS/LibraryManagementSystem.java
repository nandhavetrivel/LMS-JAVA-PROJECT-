package com.LMS;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class LibraryManagementSystem implements Serializable {
    private static ArrayList<Book> bookCollection = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<IssueBook> issuedBooks = new ArrayList<>();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println();
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Display Books");
            System.out.println("6. Save and Load Data from Files");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    issueBook();
                    break;
                case 5:
                    displayBooks();
                    break;
                case 6:
                    saveDataToFile();
                    loadDataFromFile();
                    break;
                case 7:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private static void addBook() {
        System.out.print("Enter Book Name: ");
        String bookName = scanner.nextLine();
        System.out.print("Enter Writer Name: ");
        String writerName = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        Book newBook = new Book(bookName, writerName, price, quantity);
        bookCollection.add(newBook);

        System.out.println("Book added successfully!");
    }

    private static void deleteBook() {
        System.out.print("Enter BookID to delete: ");
        int bookId = scanner.nextInt();

        Iterator<Book> iterator = bookCollection.iterator();
        boolean bookFound = false;

        while (iterator.hasNext()) {
            Book book = iterator.next();

            if (book.getBookId() == bookId) {
                iterator.remove();
                bookFound = true;
                break;
            }
        }

        if (bookFound) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }


    private static void displayBooks() {
        if (bookCollection.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("List of Books:");
            for (Book book : bookCollection) {
                System.out.println(book);
            }
        }
    }

    private static void searchBook() {
        System.out.print("Enter Book Name or Writer Name: ");
        String query = scanner.next().toLowerCase();
        scanner.nextLine();

        boolean found = false;

        for (Book book : bookCollection) {
            if (book.getBookName().toLowerCase().contains(query) || book.getWriterName().toLowerCase().contains(query)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    private static void issueBook() {
        System.out.print("Old User? Enter Yes or No: ");
        String userCheck = scanner.nextLine();
        String userPassword;
        if (userCheck.equalsIgnoreCase("yes")) {
            System.out.print("Enter Admin Password: ");
            userPassword = scanner.next();
        }
        else {
            userPassword = registerUser();
        }

        if (isUser(userPassword)) {
            System.out.print("Enter BookID to issue: ");
            int bookId = scanner.nextInt();

            // Check if the book is available
            Book selectedBook = findBookById(bookId);
            if (selectedBook != null && selectedBook.getQuantity() > 0) {
                System.out.print("Enter User ID: ");
                int userId = scanner.nextInt();

                // Check if the book is not already issued to the user
                if (!isBookAlreadyIssued(bookId, userId)) {
                    issuedBooks.add(new IssueBook(bookId, userId));
                    selectedBook.setQuantity(selectedBook.getQuantity() - 1);
                    System.out.println("Book issued successfully!");
                } else {
                    System.out.println("Book already issued to the user.");
                }
            } else {
                System.out.println("Book not available.");
            }
        } else {
            System.out.println("Invalid admin password. Access denied!");
        }
    }

    private static String registerUser() {
        System.out.print("Enter a password for the new user: ");
        String password = scanner.next();

        User newUser = new User(password, "User");
        users.add(newUser);

        System.out.println("User registered successfully!");
        return password;
    }

    private static boolean isUser(String password) {
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase("user") && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static Book findBookById(int bookId) {
        for (Book book : bookCollection) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    private static boolean isBookAlreadyIssued(int bookId, int userId) {
        for (IssueBook issue : issuedBooks) {
            if (issue.getBookId() == bookId && issue.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

    private static void saveDataToFile() {
        try {
            FileOutputStream fos1 = new FileOutputStream("userDetails.txt", true);
            FileOutputStream fos2 = new FileOutputStream("bookDetails.txt", true);
            FileOutputStream fos3 = new FileOutputStream("issueBook.txt", true);
            ObjectOutputStream userStream = new ObjectOutputStream(fos1);
            ObjectOutputStream bookStream = new ObjectOutputStream(fos2);
            ObjectOutputStream issueStream = new ObjectOutputStream(fos3);
            userStream.writeObject(users);
            bookStream.writeObject(bookCollection);
            issueStream.writeObject(issuedBooks);

            userStream.reset();
            bookStream.reset();
            issueStream.reset();

            userStream.close();
            bookStream.close();
            issueStream.close();
            fos1.close();
            fos2.close();
            fos3.close();

            System.out.println("Data saved to files successfully.");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadDataFromFile() {
        try  {
            FileInputStream fis1 = new FileInputStream("userDetails.txt");
            FileInputStream fis2 = new FileInputStream("bookDetails.txt");
            FileInputStream fis3 = new FileInputStream("issueBook.txt");
            ObjectInputStream userStream = new ObjectInputStream(fis1);
            ObjectInputStream bookStream = new ObjectInputStream(fis2);
            ObjectInputStream issueStream = new ObjectInputStream(fis3);
            users = (ArrayList<User>) userStream.readObject();
            bookCollection = (ArrayList<Book>) bookStream.readObject();
            issuedBooks = (ArrayList<IssueBook>) issueStream.readObject();

            userStream.close();
            bookStream.close();
            issueStream.close();
            fis1.close();
            fis2.close();
            fis3.close();

            System.out.println("Data loaded from files successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}