package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

    public class ObservableBook {
        ObservableList<ObservableBook> triangles = FXCollections.observableArrayList();

        SimpleIntegerProperty isbn = new SimpleIntegerProperty();
        SimpleStringProperty title = new SimpleStringProperty();
        SimpleIntegerProperty year = new SimpleIntegerProperty();



        public int getIsbn() {
            return isbn.get();
        }

        public SimpleIntegerProperty isbnProperty() {
            return isbn;
        }

        public void setIsbn(int isbn) {
            this.isbn.set(isbn);
            // area.set(area());
        }

        public String
        getTitle() {
            return title.get();
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public int getYear() {
            return year.get();
        }

        public SimpleIntegerProperty yearProperty() {
            return year;
        }

        public void setYear(int year) {
            this.year.set(year);
        }


        public ObservableBook(SimpleIntegerProperty isbn, SimpleStringProperty title, SimpleIntegerProperty year) {
            this.isbn = isbn;
            this.title = title;
            this.year = year;
        }

        public ObservableBook(String isbn, String title, String year) {
            this.isbn.set(Integer.parseInt(isbn));
            this.title.set(title);
            this.year.set(Integer.parseInt(year));
        }

        public ObservableBook(int i, String t, int y) {
            setIsbn(i);
            setTitle(t);
            setYear(y);
        }

        public void loadBooks(List<String> lines){
            for (String str: lines) {
                ObservableBook oBk = new ObservableBook(str.split(" ")[0], str.split(" ")[1], str.split(" ")[2]);
                triangles.add(oBk);
            }
        }
}
