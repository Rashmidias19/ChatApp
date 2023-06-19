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
        String name=txtUserName.getText();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/ChatForm.fxml"))));
        stage.setTitle(name+" Room");
        stage.centerOnScreen();
        stage.show();
    }
}
