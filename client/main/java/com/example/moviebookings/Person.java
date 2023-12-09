package com.example.moviebookings;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ToggleGroup;

import java.time.LocalDate;

public class Person {
    private SimpleStringProperty voterName;
    private String voterDateOfBirth;
    private SimpleStringProperty voterSex;
    private SimpleStringProperty voterPhoneNumber;
    private SimpleStringProperty voterVoterID;
    private SimpleStringProperty Votes;

    public Person(String voterName, String voterDateOfBirth, String voterSex, String voterPhoneNumber, String voterVoterID) {
        this.voterName = new SimpleStringProperty(voterName);
        this.voterDateOfBirth = voterDateOfBirth;
        this.voterSex = new SimpleStringProperty(voterSex);
        this.voterPhoneNumber = new SimpleStringProperty(voterPhoneNumber);
        this.voterVoterID = new SimpleStringProperty(voterVoterID);
    }

    public Person(String voterName, String voterVoterID) {
        this.voterName = new SimpleStringProperty(voterName);
        this.voterVoterID = new SimpleStringProperty(voterVoterID);
    }

    public Person(String voterName, int votes) {
        this.voterName = new SimpleStringProperty(voterName);
        this.Votes = new SimpleStringProperty(votes+"");
    }


    public String getVoterName() {
        return voterName.get();
    }

    public SimpleStringProperty voterNameProperty() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName.set(voterName);
    }


    public String getVoterDateOfBirth() {
        return voterDateOfBirth;
    }

    public void setVoterDateOfBirth(String voterDateOfBirth) {
        this.voterDateOfBirth = voterDateOfBirth;
    }

    public String getVoterSex() {
        return voterSex.get();
    }

    public void setVoterSex(String voterSex) {
        this.voterSex.set(voterSex);
    }

    public SimpleStringProperty getVoterSexProperty() {
        return voterSex;
    }

    public String getVoterPhoneNumber() {
        return voterPhoneNumber.get();
    }

    public SimpleStringProperty voterPhoneNumberProperty() {
        return voterPhoneNumber;
    }

    public void setVoterPhoneNumber(String voterPhoneNumber) {
        this.voterPhoneNumber.set(voterPhoneNumber);
    }

    public String getVoterVoterID() {
        return voterVoterID.get();
    }

    public SimpleStringProperty voterVoterIDProperty() {
        return voterVoterID;
    }

    public void setVoterVoterID(String voterVoterID) {
        this.voterVoterID.set(voterVoterID);
    }

    public void setVotes(String votes){
        this.Votes.set(votes);
    }

    public SimpleStringProperty getNumberOfVotes(){return Votes;}
}