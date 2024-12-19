package com.example.bluff_fx_beta;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class BluffGameGUI extends Application {
    private static final String[] RANKS = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private final Deck deck = new Deck(RANKS, SUITS);
    private final List<String> pile = new ArrayList<>();
    private final Player humanPlayer = new Player("You", new ArrayList<>());
    private final Player computerPlayer = new Player("Computer", new ArrayList<>());
    private int currentRankIndex = 0;
    private int computerTurnCounter = 0; // Counter for computer turns
    private boolean isFirstTurnOfRound = true; // Tracks if it's the first turn of the round

    private Label gameStateLabel;
    private ListView<String> humanHandView;
    private ListView<String> pileView;
    private Label firstCardLabel; // Label to display the first card of the round
    private Button playButton, passButton, bluffButton;

    //------------------------------------------
    TableView<Score> tableView=new TableView<>();
    private final ObservableList<Score> data =
            FXCollections.observableArrayList(
                    new Score("COMPUTER","2 times","5 times"),
                    new Score("YOU     ","5 times","2 times")
            );

    @Override
    public void start(Stage primaryStage) {

        BorderPane mainLayout=new BorderPane();

        FileInputStream bgImagePath = null;
        try {
            bgImagePath = new FileInputStream("D:\\Java Programes\\Images\\Bluff_Intro_enhanced.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image backgroundImage = new Image(bgImagePath);
//        BackgroundImage bgImage = new BackgroundImage(
//                backgroundImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT);
//        mainLayout.setBackground(new Background(bgImage));

        ImageView bgImage=new ImageView(backgroundImage);
        bgImage.setFitHeight(600);
        bgImage.setFitWidth(1600);
//        bgImage.setPreserveRatio(true);
        mainLayout.setCenter(bgImage);

        VBox bottomVbox=new VBox(10);
//        bottomVbox.setPadding(new Insets(6));
        bottomVbox.setAlignment(Pos.CENTER);
        bottomVbox.setBackground(Background.fill(Paint.valueOf("black")));

        Label quote=new Label("----Trust Or Betray---");
        quote.setFont(Font.font("Chiller", 35));
        quote.setStyle("-fx-background-color: black; -fx-text-fill: Red;");
        bottomVbox.getChildren().add(quote);

        Button playButton=new Button("Play Game");
        bottomVbox.getChildren().add(playButton);
        playButton.setFont(Font.font("Chiller", 20)); // Change font size to 20
        playButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Set background and text color
        // Set preferred button size
//        playButton.setPrefWidth(200);
//        playButton.setPrefHeight(40);

        playButton.setOnAction(e-> {
            playWindow(primaryStage);
//            stage.hide();
        });


        Button veiwStaticsButton=new Button("View Statics ");
        bottomVbox.getChildren().add(veiwStaticsButton);
        veiwStaticsButton.setFont(Font.font("Chiller", 20)); // Change font size to 20
        veiwStaticsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Set background and text color
        // Set preferred button size
//        veiwStaticsButton.setPrefWidth(200);
//        veiwStaticsButton.setPrefHeight(40);
          veiwStaticsButton.setOnAction(e->{
//            primaryStage.hide();
              viewStaticsWindow();
        });

        Button exitButton=new Button("Exit ");
        bottomVbox.getChildren().add(exitButton);
        exitButton.setFont(Font.font("Chiller", 20)); // Change font size to 20
        exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Set background and text color
        // Set preferred button size
        // exitButton.setPrefWidth(200);
      //  exitButton.setPrefHeight(40);
        exitButton.setOnAction(e->{
            primaryStage.hide();
        });

        mainLayout.setBottom(bottomVbox);

        Scene scene=new Scene(mainLayout);
        primaryStage.setTitle("Bluff Intro ");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);

    }


    public void playWindow(Stage primaryStage){
//        Stage primaryStage=new Stage();
        initializeGame();

        // Main layout using BorderPane
        BorderPane mainLayout = new BorderPane();

        // Create a GridPane for arranging UI components
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        // Create VBox for both left and right padding to center the content
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        leftVBox.setPadding(new Insets(300));
        rightVBox.setPadding(new Insets(250));

        // Set the background image
        FileInputStream bgImagePath = null;
        try {
            bgImagePath = new FileInputStream("D:\\Java Programes\\Images\\bluff_background1.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image backgroundImage = new Image(bgImagePath);
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        mainLayout.setBackground(new Background(bgImage));

        // Create and style the banner label (Bluff Game)
        Label bannerLabel = new Label("Bluff Master");
        bannerLabel.setFont(Font.font("Edwardian Script ITC", FontWeight.BOLD, 40));
        bannerLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        bannerLabel.setPadding(new Insets(10));
        GridPane.setColumnSpan(bannerLabel, 3); // Make banner span across multiple columns
        root.add(bannerLabel, 0, 0, 3, 1); // Add banner at the top

        // Game state label
        gameStateLabel = new Label("Welcome to Bluff! Your turn.");
        gameStateLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        gameStateLabel.setStyle("-fx-background-color: cyan; ");
        root.add(gameStateLabel, 0, 1, 3, 1);

        // First card label
        firstCardLabel = new Label("First Card of Round: None");
        firstCardLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        firstCardLabel.setStyle(" -fx-background-color: cyan;");
        root.add(firstCardLabel, 0, 2, 3, 1);

        // Human hand
        humanHandView = new ListView<>();
        humanHandView.getItems().addAll(humanPlayer.getHand());
        humanHandView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Label handLabel=new Label("Your Hand: ");
        handLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        handLabel.setStyle(" -fx-background-color: cyan;");
        VBox humanHandBox = new VBox(handLabel, humanHandView);
        root.add(humanHandBox, 0, 3, 3, 1);

        // Pile view
        pileView = new ListView<>();
        updatePileView();
        Label pileLabel=new Label("Pile:");
        pileLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        pileLabel.setStyle(" -fx-background-color: cyan;");
        pileView.setPrefHeight(150);
        VBox pileBox = new VBox(pileLabel, pileView);
        root.add(pileBox, 0, 4, 3, 1);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        playButton = new Button("Play Cards");
        passButton = new Button("Pass");
        bluffButton = new Button("Call Bluff");
        Button exitButton=new Button("Exit");

        exitButton.setFont(Font.font("Book Antiqua", 15)); // Change font size to 20
        exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        playButton.setFont(Font.font("Book Antiqua", 15)); // Change font size to 20
        playButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        passButton.setFont(Font.font("Book Antiqua", 15)); // Change font size to 20
        passButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        bluffButton.setFont(Font.font("Book Antiqua", 15)); // Change font size to 20
        bluffButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");


        buttonBox.getChildren().addAll(playButton, passButton, bluffButton,exitButton);
//        root.add(buttonBox, 0, 5, 3, 1);


        // Button actions
        playButton.setOnAction(e -> playTurn());
        passButton.setOnAction(e -> passTurn());
        bluffButton.setOnAction(e -> callBluff());
        exitButton.setOnAction(e-> start(primaryStage));

        // Add the VBox padding on the left and right sides to center the content
        mainLayout.setLeft(leftVBox);  // Add left padding
        mainLayout.setRight(rightVBox);  // Add right padding
        mainLayout.setBottom(buttonBox);
        // Set root layout in the center of the BorderPane
        mainLayout.setCenter(root);

        // Set up the stage with a responsive layout
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Bluff Game");
        primaryStage.setScene(new Scene(mainLayout)); // Adjusted initial size
        primaryStage.show();

    }

//    public void playWindow(Stage primaryStage) {
//        initializeGame();
//
//        // Create main layout as BorderPane
//        BorderPane mainLayout = new BorderPane();
//
//        // Create GridPane for UI components
//        GridPane root = new GridPane();
//        root.setHgap(10);
//        root.setVgap(10);
//        root.setPadding(new Insets(20));
//
//        // Add a StackPane for centering content and wrapping the main UI components
//        StackPane centerPane = new StackPane();
//
//        // Create VBox for both left and right padding to center the content
//        VBox leftVBox = new VBox();
//        VBox rightVBox = new VBox();
//        leftVBox.setPadding(new Insets(330));
//        rightVBox.setPadding(new Insets(30));
//
//        // Set the background image for the main layout
//        FileInputStream bgImagePath = null;
//        try {
//            bgImagePath = new FileInputStream("D:\\Java Programes\\Images\\bluff_background1.jpg");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        Image backgroundImage = new Image(bgImagePath);
//        BackgroundImage bgImage = new BackgroundImage(
//                backgroundImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT);
//        mainLayout.setBackground(new Background(bgImage));
//
//        // Create and style the banner label
//        Label bannerLabel = new Label("Bluff Game");
//        bannerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//        bannerLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
//        bannerLabel.setPadding(new Insets(10));
//        GridPane.setColumnSpan(bannerLabel, 3);
//        root.add(bannerLabel, 0, 0, 3, 1);
//
//        // Game state label
//        gameStateLabel = new Label("Welcome to Bluff! Your turn.");
//        gameStateLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
//        gameStateLabel.setStyle("-fx-background-color: cyan; ");
//        root.add(gameStateLabel, 0, 1, 3, 1);
//
//        // First card label
//        firstCardLabel = new Label("First Card of Round: None");
//        firstCardLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
//        firstCardLabel.setStyle(" -fx-background-color: cyan;");
//        root.add(firstCardLabel, 0, 2, 3, 1);
//
//        // Human hand
//        humanHandView = new ListView<>();
//        humanHandView.getItems().addAll(humanPlayer.getHand());
//        humanHandView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        Label handLabel = new Label("Your Hand: ");
//        handLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
//        handLabel.setStyle(" -fx-background-color: cyan;");
//        VBox humanHandBox = new VBox(handLabel, humanHandView);
//        root.add(humanHandBox, 0, 3, 3, 1);
//
//        // Pile view
//        pileView = new ListView<>();
//        updatePileView();
//        Label pileLabel = new Label("Pile:");
//        pileLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
//        pileLabel.setStyle(" -fx-background-color: cyan;");
//        VBox pileBox = new VBox(pileLabel, pileView);
//        root.add(pileBox, 0, 4, 3, 1);
//
//        // Buttons
//        HBox buttonBox = new HBox(10);
//        buttonBox.setAlignment(Pos.CENTER);
//        playButton = new Button("Play Cards");
//        passButton = new Button("Pass");
//        bluffButton = new Button("Call Bluff");
//        buttonBox.getChildren().addAll(playButton, passButton, bluffButton);
//        root.add(buttonBox, 0, 5, 3, 1);
//
//        // Button actions
//        playButton.setOnAction(e -> playTurn());
//        passButton.setOnAction(e -> passTurn());
//        bluffButton.setOnAction(e -> callBluff());
//
//        // Center all UI elements within StackPane
//        centerPane.getChildren().add(root);
//
//        // Add left and right padding VBoxes
//        mainLayout.setLeft(leftVBox);
//        mainLayout.setRight(rightVBox);
//
//        // Set centerPane as the center of the BorderPane
//        mainLayout.setCenter(centerPane);
//
//        // Set up the stage with a responsive layout
//        primaryStage.setTitle("Bluff Game");
//        Scene scene = new Scene(mainLayout);
//        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
//            // Ensure content is always centered when resizing the window
//            double width = newVal.doubleValue();
//            if (width > 600) {
//                leftVBox.setPadding(new Insets(330));
//                rightVBox.setPadding(new Insets(30));
//            } else {
//                leftVBox.setPadding(new Insets(200)); // Reduce padding for smaller windows
//                rightVBox.setPadding(new Insets(20));
//            }
//        });
//
//        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true); // Maximize the window initially
//        primaryStage.show();
//    }

    void viewStaticsWindow(){
        Stage stage=new Stage();

        BorderPane mainLayout=new BorderPane();
        FileInputStream bgImagePath = null;
        try {
            bgImagePath = new FileInputStream("D:\\Java Programes\\Images\\bluff_background3.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image backgroundImage = new Image(bgImagePath);
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        mainLayout.setBackground(new Background(bgImage));

        Label score=new Label("Final Score ");
        score.setFont(Font.font("Freestyle Script",40));

        TableColumn<Score,String> nameColumn=new TableColumn<>(" Name:    ");
//        nameColumn.setMaxWidth(2000);
        TableColumn<Score,String> wonColumn=new TableColumn<>("Won     ");
//        wonColumn.setMaxWidth(2000);
        TableColumn<Score,String> lostColumn=new TableColumn<>("Lost    ");
//        lostColumn.setMaxWidth(2000);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Score,String>("wonColumn")
        );
        wonColumn.setCellValueFactory(
                new PropertyValueFactory<Score,String>("nameColumn")
        );
        lostColumn.setCellValueFactory(
                new PropertyValueFactory<Score,String>("lostColumn")
        );

        tableView.setItems(data);

        tableView.getColumns().addAll(nameColumn,wonColumn,lostColumn);

        VBox staticsVbox=new VBox(10);
        staticsVbox.setPadding(new Insets(200));
        staticsVbox.getChildren().addAll(score,tableView);
        mainLayout.setCenter(staticsVbox);


        Scene scene=new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setTitle("View Statics ");
        stage.setScene(scene);
        stage.show();



    }

    private void initializeGame() {
        deck.shuffle();
        int cardsPerPlayer = deck.size() / 2;
        humanPlayer.getHand().addAll(deck.deal(cardsPerPlayer));
        computerPlayer.getHand().addAll(deck.deal(cardsPerPlayer));
    }

    private void playTurn() {
        List<String> selectedCards = new ArrayList<>(humanHandView.getSelectionModel().getSelectedItems());
        if (selectedCards.isEmpty()) {
            gameStateLabel.setText("Select cards to play or pass your turn.");
            return;
        }

        if (humanPlayer.playCards(selectedCards)) {
            if (pile.isEmpty()) {
                String firstCardRank = selectedCards.get(0).split(" ")[0]; // Extract rank only
                firstCardLabel.setText("First Card of Round: " + firstCardRank);
            }
            pile.addAll(selectedCards);
            humanHandView.getItems().setAll(humanPlayer.getHand());
            updatePileView();
            gameStateLabel.setText("You played " + selectedCards.size() + " card(s). Computer's turn!");

            if (humanPlayer.getHand().isEmpty()) {
                gameStateLabel.setText("You have won the game!");
                disableGame();
                return;
            }

            computerTurn();
        } else {
            gameStateLabel.setText("Invalid cards. Try again.");
        }
    }

    private void passTurn() {
        gameStateLabel.setText("You passed. Computer's turn!");
        computerTurn();
    }

    private void callBluff() {
        boolean isBluff = checkBluff(pile, RANKS[currentRankIndex]);
        if (isBluff) {
            gameStateLabel.setText("Bluff successful! Computer picks up the pile.");
            computerPlayer.pickUpPile(pile);
        } else {
            gameStateLabel.setText("Bluff failed! You pick up the pile.");
            humanPlayer.pickUpPile(pile);
            humanHandView.getItems().setAll(humanPlayer.getHand());
        }
        startNewRound();
    }

    private void computerTurn() {
        if (computerPlayer.getHand().isEmpty()) {
            gameStateLabel.setText("Computer has won the game!");
            disableGame();
            return;
        }

        computerTurnCounter++;

        // Computer will not call bluff on the first turn of the round
        if (!isFirstTurnOfRound && (computerTurnCounter % 5 == 0 || new Random().nextInt(5) == 0)) {
            gameStateLabel.setText("Computer called bluff!");
            boolean isBluff = checkBluff(pile, RANKS[currentRankIndex]);
            if (isBluff) {
                gameStateLabel.setText("Bluff successful! You pick up the pile.");
                humanPlayer.pickUpPile(pile);
                humanHandView.getItems().setAll(humanPlayer.getHand());
            } else {
                gameStateLabel.setText("Bluff failed! Computer picks up the pile.");
                computerPlayer.pickUpPile(pile);
            }
            startNewRound();
            return;
        }

        isFirstTurnOfRound = false; // Reset first-turn flag after computer's first turn

        // Computer decides to play cards or pass
        Random random = new Random();
        boolean willPlay = random.nextBoolean();

        if (willPlay) {
            int numCards = random.nextInt(3) + 1; // Play 1-3 cards
            List<String> cardsToPlay = new ArrayList<>();
            for (int i = 0; i < numCards && !computerPlayer.getHand().isEmpty(); i++) {
                cardsToPlay.add(computerPlayer.getHand().remove(0));
            }

            if (pile.isEmpty()) {
                String firstCardRank = cardsToPlay.get(0).split(" ")[0]; // Extract rank only
                firstCardLabel.setText("First Card of Round: " + firstCardRank);
            }
            pile.addAll(cardsToPlay);

            updatePileView();
            gameStateLabel.setText("Computer played " + numCards + " card(s). Your turn!");
        } else {
            gameStateLabel.setText("Computer passed. Your turn!");
        }

        currentRankIndex = (currentRankIndex + 1) % RANKS.length;
    }

    private boolean checkBluff(List<String> playedCards, String currentRank) {
        for (String card : playedCards) {
            if (!card.startsWith(currentRank)) {
                return true;
            }
        }
        return false;
    }

    private void startNewRound() {
        pile.clear();
        updatePileView();
        firstCardLabel.setText("First Card of Round: None");
        isFirstTurnOfRound = true; // Mark the new round's first turn
    }

    private void updatePileView() {
        pileView.getItems().clear();
        for (int i = 0; i < pile.size(); i++) {
            pileView.getItems().add("Card Played");
        }
    }

    private void disableGame() {
        playButton.setDisable(true);
        passButton.setDisable(true);
        bluffButton.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}