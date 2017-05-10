package com.david;

/**
 * Created by cw8454tn on 5/10/2017.
 */
public class BorrowedBooks {
    String name;
    String author;
    String date;
    String borrower;

    BorrowedBooks(String n, String a,String d,String b){
        name = n;
        author = a;
        date = d;
        borrower = b;
    }

    @Override
    public String toString(){
        return "Book name " + name + ", from author " + author + " borrowed by "  + borrower + " borrowed on " + date ;
    }
}
