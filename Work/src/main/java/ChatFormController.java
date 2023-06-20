import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatFormController extends Thread implements Initializable {
    public AnchorPane dashboardPane;
    public TextField txtMessage;
    public VBox vBox;
    public TextArea txtArea;
    public Button btnSend;
    public ImageView imgImoj;
    public ImageView imgCamera;
    public Label lblName;
    public AnchorPane emojiPane;
    public VBox emoji;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader reader;
    PrintWriter writer;
    private File filePath;
    private String message = "";
    private String reply = "";

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8000);
            System.out.println("socket is connected with the server");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] token = msg.split(" ");
                String cmd = token[0];
                System.out.println(cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < token.length; i++) {
                    fullMsg.append(token[i]);
                }
                String[] massageAr = msg.split(" ");
                String string = "";
                for (int i = 0; i < massageAr.length - 1; i++) {
                    string += massageAr[i + 1] + " ";
                }

                Text text = new Text(string);
                String fChar = "";

                if (string.length() > 3) {
                    fChar = string.substring(0, 3);
                }

                if (fChar.equalsIgnoreCase("img")) {
                    string = string.substring(3, string.length() - 1);

                    File file = new File(string);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitWidth(70);
                    imageView.setFitHeight(70);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);//bottom-right
                    //vBox.setAlignment(Pos.BOTTOM_LEFT);
                    //hBox.setAlignment(Pos.CENTER_LEFT);

                 ///   hBox.getChildren().add(imageView);
                //for sending the images
                    if (!cmd.equalsIgnoreCase(lblName.getText())) {
                        vBox.setAlignment(Pos.BOTTOM_LEFT);//top left
                        hBox.setAlignment(Pos.CENTER_LEFT);//center right

                       Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);
                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);//bottom right
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);
                    }

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));

                }else if(fChar.equalsIgnoreCase("emj")){
                    string = string.substring(3, string.length() - 1);

                    File file = new File(string);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitWidth(70);
                    imageView.setFitHeight(70);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);//bottom-right
                    //vBox.setAlignment(Pos.BOTTOM_LEFT);
                    //hBox.setAlignment(Pos.CENTER_LEFT);

                    ///   hBox.getChildren().add(imageView);
                    //for sending the images
                    if (!cmd.equalsIgnoreCase(lblName.getText())) {
                        vBox.setAlignment(Pos.BOTTOM_LEFT);//top left
                        hBox.setAlignment(Pos.CENTER_LEFT);//center right

                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);
                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);//bottom right
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text("");
                        hBox.getChildren().add(text1);
                    }

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                }
                else {
                    TextFlow tempTextFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(/*LoginFormController.name*/lblName.getText() + ":")) {
                        Text name = new Text(cmd + " ");
                        name.getStyleClass().add("name");
                        tempTextFlow.getChildren().add(name);
                    }

                    tempTextFlow.getChildren().add(text);
                    tempTextFlow.setMaxWidth(120);

                    TextFlow textFlow = new TextFlow(tempTextFlow);
                    textFlow.setStyle("-fx-background-color:#ff6b81;" + "-fx-background-radius: 20px;" + "-fx-font-size: 17px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    HBox hBox = new HBox(10);
                    hBox.setPadding(new Insets(5));

                    if (!cmd.equalsIgnoreCase(/*LoginFormController.name*/lblName.getText()+ ":")) {
                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(textFlow);
                    } else {
                        Text text1 = new Text(fullMsg + ": Me");
                        TextFlow textFlow1 = new TextFlow(text1);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(textFlow1);
                        textFlow1.setStyle("-fx-background-color:#7bed9f;" + "-fx-background-radius: 20px;" + "-fx-font-size: 17px;");
                        textFlow1.setPadding(new Insets(5, 10, 5, 10));
                    }
                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                }

                System.out.println(fullMsg);
                if (cmd.equalsIgnoreCase(/*LoginFormController.name*/lblName.getText() + ":")) {
                    continue;
                } else if (fullMsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void sendBtnOnAction(ActionEvent event) throws IOException {
        String msg=txtMessage.getText();
        writer.println(lblName.getText()+" :"+msg);
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text=new Text("Me :"+msg);
        text.setStyle("-fx-font-size: 15px");
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                + "-fx-background-color: rgb(15,125,242);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0.934, 0.945, 0.996));
        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);
        writer.flush();
        txtMessage.setText("");
        if((msg.equalsIgnoreCase("BYE"))||(msg.equalsIgnoreCase("Logout"))){
            System.exit(0);
        }



    }

    public void imgCamaraOnAction(MouseEvent mouseEvent) throws MalformedURLException {

        try {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            this.filePath = fileChooser.showOpenDialog(stage);
            writer.println(lblName.getText() + " " + "img" + filePath.getPath());
            writer.flush();
        } catch (NullPointerException e) {
            System.out.println("Image is not Selected!");
        }


    }

    public void emojiBtnOnAction(MouseEvent mouseEvent) {
    }

    ArrayList labels = new ArrayList();

    public void setEmoji() {
        String[] emojis = new String[]{
                "\uD83D\uDE00", "\uD83D\uDE03", "\uD83D\uDE04", "\uD83D\uDE01", "\uD83D\uDE06", "\uD83E\uDD23",
                "\uD83D\uDE02", "\uD83D\uDE42", "\uD83D\uDE43", "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE31",
                "\uD83D\uDE07", "\uD83E\uDD70", "\uD83D\uDE0D", "\uD83E\uDD29", "\uD83D\uDE18", "\uD83D\uDE17",
                "\uD83D\uDE0F", "\uD83D\uDE12", "\uD83D\uDE1E", "\uD83D\uDE14", "\uD83D\uDE1F", "\uD83D\uDE15",
                "\uD83D\uDE41", "\uD83D\uDE23", "\uD83D\uDE16", "\uD83D\uDE2B", "\uD83D\uDE29", "\uD83D\uDE22",
                "\uD83D\uDE2D", "\uD83D\uDE2E", "\uD83D\uDE2F", "\uD83D\uDE32", "\uD83D\uDE33", "\uD83D\uDE35",
                "\uD83D\uDE28", "\uD83D\uDE30", "\uD83D\uDE25", "\uD83D\uDE34", "\uD83D\uDE37", "\uD83E\uDD12",
                "\uD83E\uDD15", "\uD83E\uDD22", "\uD83E\uDD2E", "\uD83E\uDD27", "\uD83E\uDD75", "\uD83E\uDD76",
                "\uD83E\uDD74", "\uD83E\uDD2F", "\uD83E\uDD1F", "\uD83D\uDE20", "\uD83D\uDE21", "\uD83E\uDD2C",
                "\uD83D\uDE08", "\uD83D\uDC7F", "\uD83D\uDC80", "\uD83D\uDCA9", "\uD83E\uDD21", "\uD83D\uDC79",
                "\uD83D\uDC7A"};
        Label label;
        String y;

        for (int i = 0; i < emojis.length; i++) {
            label = new Label();
            y = emojis[i];
            int fontSize = 28;
            label.setStyle("-fx-font-size: " + fontSize + "px;");
            label.setText(y);
            Label finalLabel = label;
            label.setOnMouseClicked(e -> {
                vBox.setAlignment(Pos.BOTTOM_RIGHT); // Align VBox content to the right
                Text text = new Text("Me : " + finalLabel.getText());
                Label labelSender = new Label();
                Label labelMsg = new Label();
                labelMsg.setStyle("-fx-font-size: " + fontSize + "px;");
                labelSender.setText("Me : ");
                labelMsg.setText(finalLabel.getText());
                text.setTextAlignment(TextAlignment.RIGHT); // Align text to the right
                HBox hBox = new HBox(10);
                hBox.setAlignment(Pos.BOTTOM_RIGHT);//bottom left
                HBox hBox1 = new HBox();//extrs
                hBox1.setAlignment(Pos.CENTER_RIGHT);//center right
                hBox.getChildren().add(labelSender);
                hBox.getChildren().add(labelMsg);
                Platform.runLater(() -> vBox.getChildren().addAll(hBox));
//                try {
//                    dataOutputStream.writeUTF("emj " + finalLabel.getText());
//                    dataOutputStream.flush();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
                writer.println(lblName.getText() + " " + "emj");
                writer.flush();
            });
            labels.add(label);
            addEmojis();
        }

    }
    public void addEmojis() {
        HBox box1 = new HBox();
        HBox box2 = new HBox();
        HBox box3 = new HBox();
        for (int j = 0; j <labels.size(); j++) {
            if (j<=15){
                box1.getChildren().add((Node) labels.get(j));
            }else if (j<=30){
                box2.getChildren().add((Node) labels.get(j));
            }else if (j<=45){
                box3.getChildren().add((Node) labels.get(j));
            }

        }
//            emoji.setAlignment(Pos.TOP_LEFT);
        emoji.getChildren().addAll(box1,box2,box3);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectSocket();
        setEmoji();
        lblName.setText(LoginFormController.name);

    }

    public void sendEmoji(File filePath){
        String msg = String.valueOf((filePath));
        writer.println(lblName.getText() + ": " + msg);
        Image image = new Image(filePath.toURI().toString());

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(70);
        imageView.setFitHeight(70);

        HBox hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.BOTTOM_RIGHT);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text("Me : "+msg);
        text.setStyle("-fx-font-size: 15px");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                + "-fx-background-color: rgb(15,125,242);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.945, 0.996));
        hBox.getChildren().add(textFlow);
        vBox.getChildren().add(hBox);
        writer.flush();
        txtMessage.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }


//        txtAreaMsg.appendText("Me : "+"\uD83D\uDE42");
    }

}
