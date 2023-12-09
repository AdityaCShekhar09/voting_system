package com.example.moviebookings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
//import sample.Main;
//import sample.Person;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class ManageVoter implements Initializable {
    public Label adminmes;
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
    private TextField dateOfBirth;
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
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<Person, String>("voterDateOfBirth"));
        colSex.setCellValueFactory(new PropertyValueFactory<Person, String>("voterSex"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<Person, String>("voterPhoneNumber"));
        colVoterID.setCellValueFactory(new PropertyValueFactory<Person, String>("voterVoterID"));

        //loads dummy data
        Client client = new Client("127.0.0.1", 5000);
        if(client.sendData("14")) {
            adminmes.setText("Session is running");
        }

        try {
            tableView.setItems(getPeople());
        }catch (Exception e){
            ObservableList<Person> people= FXCollections.observableArrayList();
            people.add(new Person("null","null","null","null","null"));
        }


    }

    private ObservableList<Person> getPeople() {
        ObservableList<Person> people= FXCollections.observableArrayList();
        Client client = new Client("127.0.0.1", 5000);
        String data = client.getData("15");
        System.out.println(data);
        String [] rows = data.split("./e");
        for (int i =0; i<rows.length; i++){
            System.out.println(rows[i]);
            String[] values = rows[i].split("./d");
            people.add(new Person(values[1],values[2],values[3],values[4],values[0]));
        }
        return people;
    }

    //this Method will help admin to double click and edit
    public void changeNameEvent(TableColumn.CellEditEvent edittedCell){
        Person personSelected= tableView.getSelectionModel().getSelectedItem();
        personSelected.setVoterName(edittedCell.getNewValue().toString());
    }
    public void changeDateOfBirthEvent(TableColumn.CellEditEvent edittedCell){
        Person personSelected= tableView.getSelectionModel().getSelectedItem();
        personSelected.setVoterName(edittedCell.getNewValue().toString());
    }
    public void changeSexEvent(TableColumn.CellEditEvent edittedCell){
        Person personSelected= tableView.getSelectionModel().getSelectedItem();
        personSelected.setVoterName(edittedCell.getNewValue().toString());
    }
    public void changePhoneNumberEvent(TableColumn.CellEditEvent edittedCell){
        Person personSelected= tableView.getSelectionModel().getSelectedItem();
        personSelected.setVoterName(edittedCell.getNewValue().toString());
    }
    public void changeVoterIDEvent(TableColumn.CellEditEvent edittedCell) {
        Person personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setVoterName(edittedCell.getNewValue().toString());
    }


    //Method That will add new voter
    @FXML
    public void addVoter(ActionEvent event){
        Client client = new Client("127.0.0.1", 5000);
        if(!client.sendData("14.!")){
            adminmes.setText("Session has ended");
        }
        else {
            Person newPerson = new Person(name.getText(), dateOfBirth.getText(), sex.getText(), phoneNumber.getText(), voterID.getText());
            Client client1 = new Client("127.0.0.1", 5000);
            String id = voterID.getText();
            String name1 = name.getText();
            String email = dateOfBirth.getText();
            String gender = sex.getText();
            String pass = phoneNumber.getText();
            String data = "16.!" + id + ".!" + name1 + ".!" + email + ".!" + gender + ".!" + pass;
            if (client1.sendData(data))
                //get all items from the table as a list and add the new person to the list
                tableView.getItems().add(newPerson);
        }
    }

    //Removes selected rows
    @FXML
    public void removeVoter(ActionEvent event){
        Client client = new Client("127.0.0.1", 5000);
        if(!client.sendData("14.!")){
            adminmes.setText("Session has ended");
        }else {
            ObservableList<Person> selectedRows, allPeople;
            allPeople = tableView.getItems();

            //Gives rows that were selected
            selectedRows = tableView.getSelectionModel().getSelectedItems();
            Client client1 = new Client("127.0.0.1", 5000);
            //Loop over the selected rows and removes the person object from the table
            for (Person person : selectedRows) {
                String id = person.getVoterVoterID();
                String data = "17.!" + id;
                if (client1.sendData(data)) {
                    allPeople.remove(person);
                }
            }
        }
    }
    public void adminLogOut(ActionEvent event) throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void startSession(ActionEvent actionEvent) throws IOException {
        Client client = new Client("127.0.0.1", 5000);
        if(client.sendData("14")) {
            adminmes.setText("Session is already running");
        }
        else{
            Client client1 = new Client("127.0.0.1", 5000);
            if(client1.sendData("12")) {
                Parent root = FXMLLoader.load(getClass().getResource("votermanage.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void stopSession(ActionEvent actionEvent) {
        Client client = new Client("127.0.0.1", 5000);
        if(client.sendData("13"))
            adminmes.setText("Session has stopped");
    }

    public void setbacktologin(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
