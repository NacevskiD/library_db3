package com.david;

import java.awt.print.Book;

/**
 * Created by David on 5/3/2017.
 */
public class Books {
    String name;
    String author;

    Books(String n, String a){
        name = n;
        author = a;
    }

    @Override
    public String toString(){
        return "Book name " + name + ", from author " + author;
    }
}
