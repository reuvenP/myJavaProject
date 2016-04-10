package com.example;


import java.awt.print.Book;

import model.backend.Backend;
import model.backend.BackendFactory;


public class Program {
    public static void main(String[] args){
        Backend backend = BackendFactory.getInstance();

        try {
            backend.createLists();
            System.out.println("Create lists");
            backend.printList(backend.getBookList());
            backend.printList(backend.getCustomerList());
            backend.printList(backend.getOrderList());
            backend.printList(backend.getSupplierList());
            backend.printList(backend.getBooksInStoreList());
            backend.printList(backend.getBooksForOrderArrayList());

            backend.updateLists();
            System.out.println("Update lists");
            backend.printList(backend.getBookList());
            backend.printList(backend.getCustomerList());
            backend.printList(backend.getOrderList());
            backend.printList(backend.getSupplierList());
            backend.printList(backend.getBooksInStoreList());
            backend.printList(backend.getBooksForOrderArrayList());

            backend.deleteLists();
            System.out.println("Delete lists");
            backend.printList(backend.getBookList());
            backend.printList(backend.getCustomerList());
            backend.printList(backend.getOrderList());
            backend.printList(backend.getSupplierList());
            backend.printList(backend.getBooksInStoreList());
            backend.printList(backend.getBooksForOrderArrayList());

            System.out.println("Finish");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
