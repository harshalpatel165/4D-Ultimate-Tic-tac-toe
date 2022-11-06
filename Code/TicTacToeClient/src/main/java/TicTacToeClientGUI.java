
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.*;
import javafx.scene.effect.*;
import javafx.event.EventHandler;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicTacToeClientGUI extends Application {
    //Game scene variables-----------------------------------------------
    private javafx.event.EventHandler<ActionEvent> myHandler;
    private ListView<Label> view = new ListView<>();
    private MenuBar bar = new MenuBar();
    private Menu gamePlayMenu = new Menu("Game Play");
    private Menu themeMenu = new Menu("Themes");
    private Menu optionMenu = new Menu("Options");
    private GridPane grid = new GridPane();
    private BorderPane pane = new BorderPane();
    private TicTacToe[] boardArray = new TicTacToe[9];
    private Text playerMove = new Text();
    private Scene gameScene;


    //Lobby scene variables----------------------------------------------
    BorderPane playerMenu = new BorderPane();
    BorderPane introMenu = new BorderPane();
    Button continueToGame = new Button("Join a server");
    Button instructions = new Button("How to Play");
    Button addEasyAI = new Button("Add Easy AI");
    Button addMediumAI = new Button("Add Medium AI");
    Button addPlayer = new Button("Add player");
    Button StartGame = new Button("Start Game");
    Button addText = new Button("Add Text");
    Button playerData = new Button("Player Statistics");
    ListView list = new ListView();
    HBox introButtons = new HBox(instructions, playerData, continueToGame);
    HBox menu = new HBox(addEasyAI,addMediumAI,addPlayer,StartGame,addText);
    TextField chat = new TextField("Press Enter to Send Message");
    VBox listView = new VBox(50,list,chat);
    VBox players = new VBox();

    //Port scene variables----------------------------------------------
    Button connectServer = new Button("Join Server");
    BorderPane portPane = new BorderPane();
    String connected = "worked";
    Client clientConnection;
    String test="";
    ArrayList<Player> playerList;
    GameInfo gameInfo;
    boolean isFourPlayers = false;

    //sql data variables
    DBConDao dbConDao = new DBConDao();
    int playerWins = -1;
    int playerLosses = -1;
    int playerPoints = -1;


    //login screen variables---------------------------------------------
    Button registerScreenButton = new Button("Register");
    Button loginScreenButton = new Button("Login");
    Button backArrow = new Button("<-");
    Text enterUsername = new Text("Username: ");
    Text enterPassword = new Text("Password: ");
    Text enterPasswordAgain = new Text("Retype Password: ");

    TextField usernameField = new TextField();
    TextField passwordField = new TextField();
    TextField retypeField = new TextField();

    Button registerButton = new Button("Register Account");
    Button loginButton = new Button("Login Account");

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Welcome");
        setLoginWindow(primaryStage);

        registerScreenButton.setOnAction(e->{
            setRegistrationScene(primaryStage);
        });

        loginScreenButton.setOnAction(e->{
            setLoginScene(primaryStage);
        });

        loginButton.setOnAction(e->{

            try {
                login(primaryStage);
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }


        });

        registerButton.setOnAction(e->{
            try {
                register(primaryStage);
                setLoginWindow(primaryStage);
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        backArrow.setOnAction(e->{
            setLoginWindow(primaryStage);
        });



        //Code to send the chat
        chat.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent k){
                if(k.getCode().equals(KeyCode.ENTER)){
                    clientConnection.sendChat(chat.getText(), clientConnection.TID);
                    chat.setText("");
                }
            }
        });

    }

    void setJoinServerScene(Stage primaryStage){
        primaryStage.setTitle("Join Server Menu");
        HBox portRow = new HBox();

        TextField portField = new TextField();
        portField.setText("5555");
        Text port = new Text("Enter server port: ");
        portRow.getChildren().addAll(port,portField);
        portRow.setSpacing(30);
        portRow.setAlignment(Pos.CENTER);
        VBox verticleBox = new VBox(portRow, connectServer);
        verticleBox.setSpacing(30);
        verticleBox.setAlignment(Pos.CENTER);
        portPane.setCenter(verticleBox);



        portPane.setStyle("-fx-background-color: lightblue;");
        portPane.setStyle("-fx-background-image: url(TicTacToeBackPic.jfif);-fx-background-size: 900, 600;"
                + "-fx-background-repeat: no-repeat;");
        Scene scene = new Scene(portPane,940,600);
        primaryStage.setScene(scene);


        connectServer.setOnAction(e->{
            System.out.println("there is no 4 players" + isFourPlayers);

                try{
                    clientConnection = new Client(data->{
                        Platform.runLater(()->{


                            if(data instanceof MasterBoard){
                                updateTicTacToes((MasterBoard) data,primaryStage);
                            }

                            if(data instanceof chatMsg){
                                chatMsg m = (chatMsg) data;
                                list.getItems().add(m.message);
                            }

                            if(data == "point"){
                                try {
                                    dbConDao.updateStats(0,0,1);
                                } catch (SQLException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            if(data == "win"){
                                try {
                                    dbConDao.updateStats(1,0,5);
                                } catch (SQLException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            if(data instanceof GameInfo) {
                                System.out.println("data: " + ((GameInfo) data).playerList.size());
                                gameInfo = (GameInfo) data;
                                if (gameInfo.playerList.size() > 4) {
                                    System.out.println("there are no 4 player. no more people should be able to join");
                                    isFourPlayers = true;
                                }
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isFourPlayers) {
                                            updateUI();
                                        } else {
                                            gameScreen();
                                            primaryStage.setScene(gameScene);
                                            primaryStage.show();
                                        }
                                    }
                                });

                            } //END OF IF DATA INSTANCE OF GameInfo
                        });
                    },Integer.parseInt(portField.getText()));
                } catch (Exception ex) {
                    connected = "Didnt work";
                    System.out.println("exception caught");
                }
                if(!isFourPlayers) {
                    if (!connected.equals("Didnt work")) {
                        clientConnection.start();

                        int retryCounter = 0;
                        while (1 == 1) {
                            if (retryCounter > 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                                clientConnection.send("new Player");
                                retryCounter++;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            {
                                // handle exceptions
                                // possibly add a sleep period
                            }
                        }
                        setPlayerMenu(primaryStage);
                    }
                    connected = "worked";
                }


        });

        myHandler = e -> {
            GameButton b1 = (GameButton)e.getSource();
            if(clientConnection.CID == clientConnection.TID && b1.getText().equals("")) {
                BoardInfo boardInfo = new BoardInfo(boardArray, b1);
                System.out.println("about to send board info");
                clientConnection.sendBoard(boardInfo);
            }
            //System.out.println(b1.outerRow + " " + b1.outerCol + " " + b1.outerArrayIndex);
            //System.out.println(b1.outerRow + " " + b1.outerCol + " " + b1.outerArrayIndex);
        };
        addEasyAI.setOnAction(e->{
            clientConnection.send("new Easy AI");
        });
        addMediumAI.setOnAction(e->{
            clientConnection.send("new Medium AI");
        });
    }

    public void register(Stage primaryStage) throws SQLException, ClassNotFoundException {

        Window owner = registerButton.getScene().getWindow();

        String username = usernameField.getText();
        String password = passwordField.getText();
        String retypePassword = retypeField.getText();

        if(username.equals("")){
            showAlert(Alert.AlertType.ERROR, owner, "Invalid Form", "Please enter a username");
            return;
        } else if(password.equals("")){
            showAlert(Alert.AlertType.ERROR, owner, "Invalid Form", "Please enter a password");
            return;
        } else if(retypePassword.equals("")){
            showAlert(Alert.AlertType.ERROR, owner, "Invalid Form", "Please retype your password");
            return;
        } else if(!retypePassword.equals(password)){
            showAlert(Alert.AlertType.ERROR, owner, "Invalid Form", "Passwords do not match");
            return;
        }


        dbConDao.insertUser(username, password);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "User Registered", "You have been registered. You may now login!");
        setLoginWindow(primaryStage);
    }

    public void login(Stage primaryStage) throws SQLException, ClassNotFoundException {

        Window owner = loginButton.getScene().getWindow();

        String username = usernameField.getText();
        String password = passwordField.getText();


        if(username.equals("")){
            showAlert(Alert.AlertType.ERROR, owner, "Invalid Form", "Please enter a username");
            return;
        } else if(password.equals("")){
            showAlert(Alert.AlertType.ERROR, owner, "Invalid Form", "Please enter a password");
            return;
        }


        if(dbConDao.login(username, password)){
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successful", "You have logged in successfully!");
            setIntroMenu(primaryStage);
            Scene scene3 = new Scene(introMenu,940,600);
            primaryStage.setScene(scene3);
            primaryStage.show();
            playerWins = dbConDao.playerwin;
            playerLosses = dbConDao.playerloss;
            playerPoints = dbConDao.points;
        } else {
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Failed", "Incorrect username or password!");
        }


    }

    void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    void setLoginScene(Stage primaryStage){
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        row1.setSpacing(15);
        row2.setSpacing(15);
        row1.getChildren().addAll(enterUsername, usernameField);
        row2.getChildren().addAll(enterPassword, passwordField);
        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(row1, row2, vbox2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        pane.setCenter(vbox);
        pane.setTop(backArrow);
        vbox2.getChildren().addAll(loginButton);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(60);
        pane.setStyle("-fx-border-color:blue;-fx-background-color:linear-gradient( to bottom, lightblue, blue );");
        Scene scene = new Scene(pane, 940,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void setRegistrationScene(Stage primaryStage){
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        row1.setSpacing(15);
        row2.setSpacing(15);
        row3.setSpacing(15);
        row1.getChildren().addAll(enterUsername, usernameField);
        row2.getChildren().addAll(enterPassword, passwordField);
        row3.getChildren().addAll(enterPasswordAgain, retypeField);
        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(row1, row2, row3,vbox2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        pane.setCenter(vbox);
        pane.setTop(backArrow);
        vbox2.getChildren().addAll(registerButton);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(60);
        pane.setStyle("-fx-border-color:blue;-fx-background-color:linear-gradient( to bottom, lightblue, blue );");
        Scene scene = new Scene(pane, 940,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void setLoginWindow(Stage primaryStage) {
        HBox horizontalBox = new HBox();

        registerScreenButton.setStyle("-fx-font: 25 arial; -fx-base: #b6e7c9;");
        registerScreenButton.setMinHeight(50);
        registerScreenButton.setMinWidth(50);
        loginScreenButton.setStyle("-fx-font: 25 arial; -fx-base: #b6e7c9;");
        loginScreenButton.setMinHeight(50);
        loginScreenButton.setMinWidth(50);

        horizontalBox.getChildren().addAll(registerScreenButton,loginScreenButton);
        horizontalBox.setAlignment(Pos.CENTER);
        horizontalBox.setSpacing(50);
        horizontalBox.setStyle("-fx-border-color:blue;-fx-background-color:linear-gradient( to bottom, lightblue, blue );");
        horizontalBox.setStyle("-fx-background-image: url(TicTacToeBPic.png);-fx-background-size: 940, 600;"
                + "-fx-background-repeat: no-repeat;");
        Scene scene = new Scene(horizontalBox, 940, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void setStartMenu(Stage primaryStage) {
        primaryStage.setTitle("Player Menu");
        Scene scene4 = new Scene(playerMenu,940,600);
        primaryStage.setScene(scene4);
        StartGame.setOnAction(e->{
            gameScreen();
            primaryStage.setScene(gameScene);
            primaryStage.show();
        });
    }

    void setWinnerScene(Stage primaryStage, String winner) {
        primaryStage.setTitle("Final Scene");

        BorderPane pane = new BorderPane();
        Text winnerText = new Text("The winner is player: " +winner);
        winnerText.setStyle("-fx-font: 40 arial; -fx-base: #b6e7c9;");
        pane.setStyle("-fx-border-color:blue;-fx-background-color:linear-gradient( to bottom, lightblue, blue );");
        Button b1 = new Button("Play Again");
        b1.setStyle("-fx-font: 25 arial; -fx-base: #b6e7c9;");
        b1.setOnAction(e->{
            primaryStage.setScene(gameScreen());
            primaryStage.show();
        });

        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText("The winner is player: " +winner);
        t.setFill(Color.BLACK);
        t.setFont(Font.font(null, FontWeight.BOLD, 55));

        Reflection r = new Reflection();
        r.setFraction(0.7f);
        t.setEffect(r);
      //  t.setTranslateY(400);

        b1.setStyle("-fx-font: 25 arial; -fx-border-color: black; -fx-background-color: yellow;");
        b1.setMinHeight(50);
        b1.setMinWidth(50);

        pane.setAlignment(b1,Pos.CENTER);
        pane.setAlignment(t, Pos.CENTER);
        pane.setTop(t);
        pane.setBottom(b1);
        pane.setStyle("-fx-background-image: url(TicTacToeBPic.png);-fx-background-size: 940, 600;"
                + "-fx-background-repeat: no-repeat;");
        Scene scene = new Scene(pane,940,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void setIntroMenu(Stage primaryStage) {
        setButtonStyle();

        instructions.setOnAction(e-> {
            Stage instructionsWindow = new Stage();
            Text instructionsText = new Text(
                    "1. Four players take turns placing their smybol on one of th 81 spaces\n" +
                    "2. The whole 9x9 board is divided into nine standard 3x3 tic-tac-toe boards\n" +
                    "3. If a player achieves a three-in-a-row on one of the standard 3x3 boards, that board is\n considered \"won\" by that player, and their symbol will be placed on top of that board\n" +
                    "4. If a player achieves wins three of the smaller 3x3 boards all in a row, that player is\n the victor");
            Button closeInstructions = new Button("Close Instructions");
            closeInstructions.setMinHeight(50);
            closeInstructions.setMinWidth(90);
            closeInstructions.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
            closeInstructions.setOnAction(b -> {
                instructionsWindow.close();
            });
            BorderPane instructionsPane = new BorderPane();
            instructionsPane.setTop(instructionsText);
            instructionsPane.setBottom(closeInstructions);
            instructionsPane.setPadding(new Insets(10, 10, 10, 10));
            instructionsPane.setStyle("-fx-background-color: lightblue;");
            closeInstructions.setAlignment(Pos.BOTTOM_CENTER);
            Scene instructionsScene = new Scene(instructionsPane, 500, 300);
            instructionsWindow.setScene(instructionsScene);
            instructionsWindow.show();
        });
        playerData.setOnAction(e->{
            Text win = new Text("Wins: " + playerWins);
            Text loss = new Text("Losses: " + playerLosses);
            Text point = new Text("Points: " + playerPoints);
            VBox box = new VBox(win,loss,point);
            box.setAlignment(Pos.CENTER);
            box.setSpacing(60);
            Stage dataWindow = new Stage();
            box.setStyle("-fx-border-color:blue;-fx-background-color:linear-gradient( to bottom, lightblue, blue );");
            Scene instructionsScene = new Scene(box, 500, 300);
            dataWindow.setScene(instructionsScene);
            dataWindow.show();
        });
        continueToGame.setOnAction(e -> {
            setJoinServerScene(primaryStage);
        });
        introMenu.setStyle("-fx-background-color: lightblue;");
        introMenu.setStyle("-fx-background-image: url(TicTacToeBackPic.jfif);-fx-background-size: 900, 600;"
                        + "-fx-background-repeat: no-repeat;");
        introMenu.setBottom(introButtons);
        introButtons.setAlignment(Pos.BOTTOM_CENTER);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text t = new Text();
        t.setEffect(ds);
        t.setCache(true);
        t.setX(10.0f);
        t.setY(270.0f);
        t.setFill(Color.RED);
        t.setText("Welcome to 4 Player TicTacToe");
        t.setFont(Font.font(null, FontWeight.BOLD, 43));
        introMenu.setAlignment(t, Pos.CENTER);
        introMenu.setTop(t);

    }
    void setPlayerMenu(Stage primaryStage) {

        setButtonStyle();
        setStartMenu(primaryStage);
        playerMenu.setLeft(players);
        playerMenu.setRight(listView);
        listView.setAlignment(Pos.TOP_RIGHT);
        players.setAlignment(Pos.TOP_LEFT);
        playerMenu.setBottom(menu);
        listView.setStyle("-fx-background-color: lightblue;");
        menu.setStyle("-fx-background-color: lightblue;");
        menu.setAlignment(Pos.BOTTOM_CENTER);
        playerMenu.setStyle("-fx-background-color: lightblue;");
        playerMenu.setStyle("-fx-background-image: url(TicTacToeBackPic.jfif);-fx-background-size: 900, 600;"
                + "-fx-background-repeat: no-repeat;");
    }

    void setButtonStyle() {
        continueToGame.setMinHeight(50);
        continueToGame.setMinWidth(90);
        continueToGame.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        instructions.setMinHeight(50);
        instructions.setMinWidth(90);
        instructions.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

        addEasyAI.setMinHeight(50);
        addEasyAI.setMinWidth(70);
        addEasyAI.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        addMediumAI.setMinHeight(50);
        addMediumAI.setMinWidth(70);
        addMediumAI.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

        addText.setMinHeight(50);
        addText.setMinWidth(70);
        addText.setStyle("-fx-font: 12 arial; -fx-base: #b6e7c9;");

        addPlayer.setMinHeight(50);
        addPlayer.setMinWidth(70);
        addPlayer.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

        StartGame.setMinHeight(50);
        StartGame.setMinWidth(70);
        StartGame.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

        setShadow(continueToGame);
        setShadow(instructions);
        setShadow(addEasyAI);
        setShadow(addMediumAI);
        setShadow(addPlayer);
        setShadow(StartGame);
        setShadow(addText);

        players.setSpacing(10);
        menu.setSpacing(10);
        introButtons.setSpacing(10);

        addText.setOnAction(e->{
            list.getItems().add("Player x: Chat goes here");
        });
    }

    void updateUI() {

        if(gameInfo.updateLobby){
            players.getChildren().clear();
            System.out.println(gameInfo.playerList.size());
            for(int i = 0; i < gameInfo.playerList.size(); i++){
                Image image = new Image(getClass().getResourceAsStream("GenericProfilePic.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(35);
                imageView.setFitHeight(35);
                image = imageView.snapshot(null, null);
                Label label = new Label();
                if(gameInfo.playerList.get(i).isAI){
                    System.out.println("hello from AI");
                    label = new Label(gameInfo.playerList.get(i).getDifficulty() + " AI " + gameInfo.playerList.get(i).turn);
                    label.setGraphic(new ImageView(image));
                    label.setFont(new Font("Arial", 32));
                } else {
                    System.out.println("hello from Player");
                    label = new Label("Player " + gameInfo.playerList.get(i).turn);
                    label.setGraphic(new ImageView(image));
                    label.setFont(new Font("Arial", 32));
                }

                players.getChildren().add(label);
            }
        }


    }

    void setShadow(Button b) {
        DropShadow shadow = new DropShadow();

        // Adding the shadow when the mouse cursor is on
        b.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                b.setEffect(shadow);
            }
        });
        b.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                b.setEffect(null);
            }
        });
    }

    private Scene gameScreen(){
        view.setStyle("-fx-border-color: #ff0000;");
        playerMove.setStyle("-fx-font: 32 arial;");

        view.setPrefWidth(100);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        BorderPane.setAlignment(playerMove, Pos.CENTER);
        BorderPane.setMargin(playerMove, new Insets(15,15,15,15));

        GameScreenGui();
        
        pane.setCenter(grid);
        pane.setLeft(view);
        pane.setTop(bar);
        pane.setBottom(playerMove);
        pane.setRight(listView);

        addTicTacToes(grid);

        MenuItem exitMenuItem = new MenuItem("exit");
        //exitMenuItem.setOnAction(exitHandler);
        MenuItem newGameMenuItem = new MenuItem("new game");
        //newGameMenuItem.setOnAction(startOverHandler);
        MenuItem howToMenuItem = new MenuItem("how to play");
        //howToMenuItem.setOnAction(howToHandler);
        optionMenu.getItems().addAll(howToMenuItem, newGameMenuItem, exitMenuItem);

        bar.getMenus().addAll(gamePlayMenu, themeMenu, optionMenu);
        gameScene = new Scene(pane, 1000,700);

        return gameScene;
    }

    public void GameScreenGui(){
        grid.setStyle("-fx-border-color:blue;-fx-background-color:linear-gradient( to bottom, lightblue, blue );");
        bar.setStyle("-fx-background-color:#8c8cd9;");
        playerMove.setStyle("-fx-background-color:#8c8cd9;");
        view.setStyle("-fx-background-color:#d9d9f2;");

//        for(Node e:grid.getChildren()) {
//            GameButton B = (GameButton) e;  // curr
//            B.setstyle("-fx-background-color: blue;");
//        }
    }
    
    public void addTicTacToes(GridPane grid){
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToe board = new TicTacToe();
                for(int o = 0; o < 3; o++) {
                    for (int p = 0; p < 3; p++) {
                        GameButton b1 = new GameButton(o, p);
                        b1.outerRow = i;
                        b1.outerCol = j;
                        b1.outerArrayIndex = (3*i)+j;
                        b1.setPrefSize(75, 75);
                        b1.setOnAction(myHandler);
                        board.checkGrid[o][p] = b1;
                        board.add(b1, p ,o);
                        String styles = "-fx-border-color: #ff0000;";
                        b1.setStyle(styles);
                        b1.setText(b1.Piece);
                        b1.setStyle("-fx-font-size: 30");
                    }
                }
                boardArray[(3*i)+j] = board;
                grid.add(board, j, i);
            }
        }
    }

    public void updateTicTacToes(MasterBoard updateBoard,Stage primaryStage){
        for(int i =0; i< 3; i++){
            for(int j=0; j<3; j++){
                for(int k = 0; k < 3; k++){
                    for(int z = 0; z<3; z++){
                        boardArray[(3*i)+j].checkGrid[k][z].setText(updateBoard.theBoard[i][j][k][z]);
                    }
                }
            }
        }

        for(int i = 0; i < 9; i++){
            if(updateBoard.whoWon[i] != 0){
                boardArray[i].getChildren().clear();
                Button b1 = new Button(Integer.toString(updateBoard.whoWon[i]));
                b1.setPrefSize(225, 225);
                b1.setStyle("-fx-font: 100 arial;");
                boardArray[i].add(b1,1,1);
                if(updateBoard.endGame){
                    setWinnerScene(primaryStage,Integer.toString(updateBoard.whoWon[i]));
                }
                //updateBoard.whoWon[i] = 0;
            }
        }

    }

}








