package com.david;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by David on 4/30/2017.
 */
public class BookManager {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/library?useSSL=false";

    static final String user = "user1";
    static final String password = "password";
    private static final String TABLE_NAME = "library_books";
    private static final String Name_COL = "book_name";
    private static final String Author_COL = "author";
    private static final String BORROWED = "borrowed_books";





    BookManager()  {
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException cnfe){
            System.out.println("Something went wrong with connection");
            cnfe.printStackTrace();
            System.exit(-1);
        }

    }

    public void createTable(){
        try (Connection connection= DriverManager.getConnection(DB_CONNECTION_URL,user,password);
             Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS library_books(book_name VARCHAR(50),author VARCHAR(50))");
            statement.execute("CREATE TABLE IF NOT EXISTS borrowed_books(book_name VARCHAR(50),author VARCHAR(50))");
            String defaultInsert =  "INSERT INTO library_books VALUES (? , ?)";
            PreparedStatement psInsert = connection.prepareStatement(defaultInsert);
            psInsert.setString(1,"Java 101");
            psInsert.setString(2,"Kerry Fish");
            System.out.println("Created table with default values.");
            psInsert.executeUpdate();
            psInsert.close();
            statement.close();
            connection.close();
        }
        catch (SQLException se){
            se.printStackTrace();
        }

    }

    void addData(Books books){
        try(Connection connection= DriverManager.getConnection(DB_CONNECTION_URL,user,password)) {
            String addBooks = "INSERT INTO " + TABLE_NAME + " VALUES (?,?)";
            PreparedStatement addBooksPS = connection.prepareStatement(addBooks);
            addBooksPS.setString(1,books.name);
            addBooksPS.setString(2,books.author);
            addBooksPS.execute();

            System.out.println("Added information for " + books);
            addBooksPS.close();
            connection.close();

        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    ArrayList<Books> getAllRecord(){
        ArrayList<Books> allRecords = new ArrayList<>();
        try (Connection connection= DriverManager.getConnection(DB_CONNECTION_URL,user,password);
             Statement statement = connection.createStatement()){

            String getAll ="SELECT * FROM " + TABLE_NAME;
            ResultSet rs = statement.executeQuery(getAll);
            while (rs.next()){
                String name = rs.getString(Name_COL);
                String author = rs.getString(Author_COL);
                Books newBook = new Books(name,author);
                allRecords.add(newBook);
            }
            rs.close();
            connection.close();
            statement.close();
            return allRecords;

        }
        catch (SQLException se){
            se.printStackTrace();
            return null;
        }
    }

    ArrayList<Books> getAllBorrowedRecord(){
        ArrayList<Books> allBorrowedRecords = new ArrayList<>();
        try (Connection conn= DriverManager.getConnection(DB_CONNECTION_URL,user,password);
             Statement statement = conn.createStatement()){

            String getAll ="SELECT * FROM " + BORROWED;
            ResultSet rsBorrowed = statement.executeQuery(getAll);
            while (rsBorrowed.next()){
                String name = rsBorrowed.getString(Name_COL);
                String author = rsBorrowed.getString(Author_COL);
                Books borrowedBook = new Books(name,author);
                allBorrowedRecords.add(borrowedBook);
            }
            rsBorrowed.close();
            conn.close();
            statement.close();
            return allBorrowedRecords;

        }
        catch (SQLException se){
            se.printStackTrace();
            return null;
        }
    }
    void delete(Books book){
        try(Connection connection= DriverManager.getConnection(DB_CONNECTION_URL,user,password)) {
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate,TABLE_NAME,Name_COL,Author_COL);
            PreparedStatement deletePS = connection.prepareStatement(deleteSQL);
            deletePS.setString(1,book.name);
            deletePS.setString(2,book.author);
            System.out.println(deletePS.toString());
            deletePS.execute();
            deletePS.close();
            connection.close();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    void borrow(Books book){
        try(Connection connection= DriverManager.getConnection(DB_CONNECTION_URL,user,password)) {
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate,TABLE_NAME,Name_COL,Author_COL);
            PreparedStatement deletePS = connection.prepareStatement(deleteSQL);
            deletePS.setString(1,book.name);
            deletePS.setString(2,book.author);
            System.out.println(deletePS.toString());
            deletePS.execute();
            deletePS.close();


            String addBooks = "INSERT INTO " + BORROWED + " VALUES (?,?)";
            PreparedStatement addBooksPS = connection.prepareStatement(addBooks);
            addBooksPS.setString(1,book.name);
            addBooksPS.setString(2,book.author);
            System.out.println(addBooksPS.toString());
            addBooksPS.execute();


            addBooksPS.close();
            connection.close();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    void returnBook (Books book){
        try(Connection connection= DriverManager.getConnection(DB_CONNECTION_URL,user,password)) {
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate,BORROWED,Name_COL,Author_COL);
            PreparedStatement deletePS = connection.prepareStatement(deleteSQL);
            deletePS.setString(1,book.name);
            deletePS.setString(2,book.author);
            System.out.println(deletePS.toString());
            deletePS.execute();
            deletePS.close();


            String addBooks = "INSERT INTO " + TABLE_NAME + " VALUES (?,?)";
            PreparedStatement addBooksPS = connection.prepareStatement(addBooks);
            addBooksPS.setString(1,book.name);
            addBooksPS.setString(2,book.author);
            System.out.println(addBooksPS.toString());
            addBooksPS.execute();


            addBooksPS.close();
            connection.close();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

}



