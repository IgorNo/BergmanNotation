package nov.gui.controllers;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nov.math.ext.BergmanNotation;

public class Controller {

    public Button btnConvert;
    public Button btnAdd;
    public Button btnMultiply;
    public Button btnExit;

    public TextField txtBerToDec;
    public TextField txtBerRes;
    public TextField txtBerNum2;
    public TextField txtBerNum1;
    public TextField txtDecNum1;
    public TextField txtDecNum2;
    public TextField txtDecRes;


    public void convert(ActionEvent actionEvent) {
        try {
            double ndn1 = Double.parseDouble(txtDecNum1.getText());
            double ndn2 = Double.parseDouble(txtDecNum2.getText());

            BergmanNotation nbn1 = new BergmanNotation(ndn1,20);
            txtBerNum1.setText(nbn1.toString());
            BergmanNotation nbn2 = new BergmanNotation(ndn2,20);
            txtBerNum2.setText(nbn2.toString());
        } catch (NumberFormatException e){
            showErrorDialog("Error","Invalid Number Format!");
        }

}

    public void add(ActionEvent actionEvent) {

        try {
            double ndn1 = Double.parseDouble(txtDecNum1.getText());
            double ndn2 = Double.parseDouble(txtDecNum2.getText());

            BergmanNotation nbn1 = new BergmanNotation(ndn1, 20);
            BergmanNotation nbn2 = new BergmanNotation(ndn2, 20);
            BergmanNotation sumb = nbn1.add(nbn2);

            txtBerNum1.setText(nbn1.toString());
            txtBerNum2.setText(nbn2.toString());
            txtDecRes.setText(new Double(ndn1 + ndn2).toString());
            txtBerRes.setText(sumb.toString());
            txtBerToDec.setText(new Double(sumb.toDecimal()).toString());
        } catch (NumberFormatException e){
            showErrorDialog("Error","Invalid Number Format!");
        }
    }

    public void multiply(ActionEvent actionEvent) {
        try {
            double ndn1 = Double.parseDouble(txtDecNum1.getText());
            double ndn2 = Double.parseDouble(txtDecNum2.getText());

            BergmanNotation nbn1 = new BergmanNotation(ndn1,20);
            BergmanNotation nbn2 = new BergmanNotation(ndn2,20);
            BergmanNotation miltb = nbn1.multiply(nbn2);

            txtBerNum1.setText(nbn1.toString());
            txtBerNum2.setText(nbn2.toString());
            txtDecRes.setText(new Double(ndn1*ndn2).toString());
            txtBerRes.setText(miltb.toString());
            txtBerToDec.setText(new Double(miltb.toDecimal()).toString());
        } catch (NumberFormatException e){
            showErrorDialog("Error","Invalid Number Format!");
        }
    }

    public void exit(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public  void showErrorDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }

}
