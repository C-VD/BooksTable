package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private TableView table;
    @FXML
    private TextField txtFileName;
    ArrayList<String> changedBooksIsbns = new ArrayList<>();

    ObservableList<ObservableBook> books = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        readBooksFromDb();
        initTable();
        startTracking();
    }

    public void initTable()
    {
        table.getColumns().clear();
        TableColumn<ObservableBook, Integer> columnIsbn = new TableColumn<>("ISBN");
        columnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<ObservableBook, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnTitle.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<ObservableBook, Integer> columnYear = new TableColumn<>("Year");
        columnYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        columnYear.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));



        table.getColumns().add(columnIsbn);
        table.getColumns().add(columnTitle);
        table.getColumns().add(columnYear);
        table.setItems(books);
        table.setEditable(true);
    }
    private static Connection connectToDB() {
        String url = "jdbc:postgresql://10.10.104.166:5432/Lib?user=postgres&password=123";//&ssl=true
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("подключено");
            return conn;
        } catch (Exception e) {
            System.out.println("не удалось подключиться к базе. "+e.getMessage());
            return null;
        }
    }

    private void readBooksFromDb() throws SQLException {
        books = FXCollections.observableArrayList();
        Connection conn =  connectToDB();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT isbn, title, year FROM book ORDER BY title");
        while (rs.next()) {
            String i = rs.getString(1);
            String t = rs.getString(2);
            String y = rs.getString(3);
            ObservableBook book = new ObservableBook(i, t, y);
            books.add(book);
        }
        rs.close();
        st.close();
    }
    @FXML
    public void onSaveBtnAction() throws IOException {
        FileWriter fw = new FileWriter(new File(txtFileName.getText()));
        for (String isbn :changedBooksIsbns) {
            fw.write(isbn + "\n");
        }
        fw.close();
    }

    void startTracking(){
        for (ObservableBook b: books) {
            b.year.addListener((v,o,n) -> changedBooksIsbns.add(String.valueOf(b.getIsbn())));
            b.title.addListener((v,o,n) -> changedBooksIsbns.add(String.valueOf(b.getIsbn())));

        }

    }

}