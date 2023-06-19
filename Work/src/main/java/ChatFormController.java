import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ChatFormController extends Thread implements Initializable {
    public AnchorPane dashboardPane;
    public TextField txtMessage;
   
    public TextArea txtArea;
    public Button btnSend;
    public ImageView imgImoj;
    public ImageView imgCamera;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    BufferedReader reader;
    PrintWriter writer;
    private  String message="";
    private  String reply = "";

    public void connectSocket(){
        try{
            socket=new Socket("localhost",3030);
            System.out.println("socket is connected with the server");
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try{
            while (true){
                String msg=
            }
        }
    }


    public void sendBtnOnAction(ActionEvent event) throws IOException {
        outputStream.writeUTF(txtMessage.getText().trim());
        outputStream.flush();
    }

    public void imgCamaraOnAction(MouseEvent mouseEvent) {
    }

    public void emojiBtnOnAction(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectSocket();
    }
}
