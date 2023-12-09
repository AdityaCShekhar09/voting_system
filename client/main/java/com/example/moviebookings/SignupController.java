package com.example.moviebookings;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    Client client;

    public Label backtologin, mes1;

    public RadioButton m, f;

    public ToggleGroup grp;
    public TextField rname, rvoter, remail;
    public PasswordField rpass, repass;

    public void setbacktologin(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grp = new ToggleGroup();
        m.setToggleGroup(grp);
        f.setToggleGroup(grp);
    }

    public void register(ActionEvent actionEvent) {
        if (rname.getText().equals("") && rvoter.getText().equals("") && rpass.getText().equals("") && repass.getText().equals("") && (m.isSelected() || f.isSelected())) {
            mes1.setText("Enter your details!");
        }
        else if(!rpass.getText().equals(repass.getText())){
            mes1.setText("Re-enter the same password");
        }
        else {
            String name = rname.getText();
            String email = remail.getText();
            String pass = rpass.getText();
            String id = rvoter.getText();
            String gender = null;
            if (m.isSelected())
                gender = m.getUserData().toString();
            else
                gender = f.getUserData().toString();
            client = new Client("127.0.0.1", 5000);
            String details="2.!"+id+".!"+name+".!"+email+".!"+gender+".!"+pass;
            if(client.sendData(details)){
                mes1.setText("User registered successfully");
            }
            else {
                mes1.setText("User already exists");
            }
        }
    }
}
