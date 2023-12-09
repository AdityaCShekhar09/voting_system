package com.example.moviebookings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Client client;
    @FXML
    public PasswordField pass;
    @FXML
    public TextField email;
    @FXML
    private Button loginbtn, signupbtn;

    @FXML
    private Label message;

    public void onlogin(ActionEvent actionEvent) throws IOException {

        if(email.getText().isEmpty() && pass.getText().isEmpty())
            message.setText("Enter your details!");
        else if(email.getText().equals("admin") && pass.getText().equals("admin")){
            Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
            Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            client = new Client("127.0.0.1", 5000);
            String e = email.getText();
            String p = pass.getText();
            String userDetails ="1.!"+ e + ".!" + p;
            if (client.sendData(userDetails)) {
                client = new Client("127.0.0.1", 5000);
                if(client.sendData("14.!")){
                    client = new Client("127.0.0.1", 5000);
                    String id= client.getData("7.!"+email.getText());
                    User.id=id;
                    client = new Client("127.0.0.1", 5000);
                    if (client.sendData("10.!"+User.id)){
                        message.setText("You have already voted");
                    }
                    else{
                        Parent root = FXMLLoader.load(getClass().getResource("voter.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
                else {
                    Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else
                message.setText("Invalid username or password!");
        }

    }

    public void onsignup(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup-view.fxml"));
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
