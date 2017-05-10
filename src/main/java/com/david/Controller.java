package com.david;

import java.util.ArrayList;

public class Controller {

    static LibraryGUI gui;
    static BookManager db;
    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.startApp();
    }

    private void startApp(){
        db = new BookManager();
        db.createTable();
        ArrayList<Books> allData = db.getAllRecord();
        ArrayList<Books> borrowedData = db.getAllBorrowedRecord();
        gui = new LibraryGUI(this);
        gui.setListData(allData);
        gui.setBorrowedListData(borrowedData);
    }
    ArrayList<Books> getAllData(){
        return db.getAllRecord();
    }
    ArrayList<Books> getAllBorrowedInfo(){
        return db.getAllBorrowedRecord();
    }
    void addRecordsToDatabase(Books books){
        db.addData(books);
    }
    void delete (Books book){
        db.delete(book);
    }
    void borrow (Books book){
        db.borrow(book);
    }
    void returnBook (Books book){
        db.returnBook(book);
    }
}
