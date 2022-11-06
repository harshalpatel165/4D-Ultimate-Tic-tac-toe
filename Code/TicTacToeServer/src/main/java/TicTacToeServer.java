import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class TicTacToeServer extends Application{
    HashMap<String, Scene> sceneMap; //tracks where all the scenes are for easy swapping
    BorderPane opening, serverPane;
    Scene openingScene, serverScene;
    VBox port;
    Text portInfo,p1,p2,p3,p4;
    TextField portText;
    Button startGame;
    ListView<String> serverInfo;
    Image bgImage;
    BackgroundImage bg;
    Background bGround;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("4 Player Tic Tac Toe");
        sceneMap = new HashMap<>();
        sceneMap.put("opening",createOpeningGui());
        sceneMap.put("server",createServerGui());
        final Server[] serverConnection = new Server[1];

        primaryStage.setScene(sceneMap.get("opening"));
        primaryStage.show();

        startGame.setOnMouseClicked(e->{
            if(!portText.getText().isEmpty()){
                int portNum = Integer.parseInt(portText.getText());
                if(portNum>=1024 && portNum <= 65535)
                    primaryStage.setScene(sceneMap.get("server"));
                serverConnection[0] = new Server(data -> {
                    Platform.runLater(()->{
                        if(data instanceof String) {
                            serverInfo.getItems().add(data.toString());
                        }
                    });
                },portNum);

            }
        });

    }



    public Scene createOpeningGui(){
        Insets textSpacing = new Insets(20,20,20,20);
        //Text to inform user
        portInfo = new Text("Enter Port Number(1024 to 65535): ");
        portInfo.setStyle("-fx-font: 30 arial;");

        //Text field to enter port
        portText = new TextField();
        portText.setStyle("-fx-font: 30 arial;");
        portText.setText("5555");

        //Button that sets the game
        startGame = new Button("Start Server");
        startGame.setStyle("-fx-font: 38 arial;");

        //Organize texts
        port = new VBox(5,portInfo,portText);

        bgImage = new Image("Tic_tac_toe.png",600,600,false,false);
        bg = new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);


        bGround = new Background(bg);

        //The panel to be displayed
        opening = new BorderPane();
        opening.setTop(port);
        opening.setCenter(startGame);
        opening.setBackground(bGround);
        BorderPane.setMargin(port, textSpacing);

        openingScene = new Scene(opening,600,600);

        return openingScene;
    }

    public Scene createServerGui(){
        Insets textSpacing = new Insets(20,20,20,20);
        //Text for players connected
        p1 = new Text("Waiting for Player 1 ");
        p1.setStyle("-fx-font: 30 arial;");


        p2 = new Text("Waiting for Player 2 ");
        p2.setStyle("-fx-font: 30 arial;");

        p3 = new Text("Waiting for Player 3 ");
        p3.setStyle("-fx-font: 30 arial;");

        p4 = new Text("Waiting for Player 4 ");
        p4.setStyle("-fx-font: 30 arial;");

        VBox players = new VBox(100,p1,p2,p3,p4);

        //List view that displays server info
        serverInfo = new ListView<>();
        serverInfo.getItems().add("Waiting for server updates...");
        //Organizing the items to display
        HBox displayBox = new HBox(50, serverInfo);

        //Set the background
        serverPane = new BorderPane();
        serverPane.setCenter(displayBox);
        serverPane.setBackground(bGround);

        serverScene = new Scene(serverPane,600,600);


        BorderPane.setMargin(displayBox, textSpacing);

        return serverScene;
    }

}
