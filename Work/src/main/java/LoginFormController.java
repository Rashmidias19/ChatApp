import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXButton btnLogin;
    public TextField txtUserName;
    public AnchorPane dashboardPane;

    public void loginBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChatFormForm.fxml"))));
        stage.setTitle("VETCLOUD");
        stage.centerOnScreen();
        stage.show();
    }
}
