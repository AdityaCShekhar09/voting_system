package com.example.moviebookings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VoterPanel implements Initializable {
    @FXML
    private Button addVoter;
    @FXML
    private Button removeVoter;

    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person,String> colName;
    @FXML
    private TableColumn<Person, String>colDateOfBirth;
    @FXML
    private TableColumn<Person, String>colSex;
    @FXML
    private TableColumn<Person, String>colPhoneNumber;
    @FXML
    private TableColumn<Person, String>colVoterID;
    @FXML
    private TableColumn<Person, String>colPassword;

    @FXML
    private TextField name;
   // @FXML
  //  private LocalDate dateOfBirth;
    @FXML
    private TextField sex;
   /* @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private RadioButton otherRadioButton;*/
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField voterID;
    @FXML
    private TextField password;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private Button adminLogOut;


    @FXML
    public void initialize(URL location, ResourceBundle resources){
       /* //Toggle Group for gender
        sex=new ToggleGroup();
        this.maleRadioButton.setToggleGroup(sex);
        this.femaleRadioButton.setToggleGroup(sex);
        this.otherRadioButton.setToggleGroup(sex);*/

        //Sets Up Column to the table
        colName.setCellValueFactory(new PropertyValueFactory<Person, String>("voterName"));
        colVoterID.setCellValueFactory(new PropertyValueFactory<Person, String>("voterVoterID"));

        //loads data
        tableView.setItems(getPeople());

        //Update the table to allow modification
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colVoterID.setCellFactory(TextFieldTableCell.forTableColumn());

        //This will allow select multiple row at once
    }

    private ObservableList<Person> getPeople() {
        ObservableList<Person> people= FXCollections.observableArrayList();
        Client client = new Client("127.0.0.1", 5000);
        String data = client.getData("6");
        System.out.println(data);
        String [] rows = data.split("./e");
        for (int i =0; i<rows.length; i++){
            System.out.println(rows[i]);
            String[] values = rows[i].split("./d");
            people.add(new Person(values[1],values[0]));
        }
        return people;
    }

    //Method That will add new voter
   @FXML
    public void vote(ActionEvent event) throws IOException {
       ObservableList<Person> selectedRows, allPeople;
       allPeople= tableView.getItems();

       //Gives rows that were selected
       selectedRows=tableView.getSelectionModel().getSelectedItems();
       Client client = new Client("127.0.0.1", 5000);
       //Loop over the selected rows and removes the person object from the table
       for (Person person: selectedRows) {
           String id = person.getVoterVoterID();
           String data = "8.!" + User.id + ".!" + id;
           if (client.sendData(data)) {
               Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               Scene scene = new Scene(root);
               stage.setScene(scene);
               stage.show();
           }
       }
       //get all items from the table as a list and add the new person to the list
    }

    public void voterLogOut(ActionEvent event) throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
