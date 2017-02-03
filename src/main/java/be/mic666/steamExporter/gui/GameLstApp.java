package be.mic666.steamExporter.gui;

import be.mic666.steamExporter.api.SteamApiCaller;
import be.mic666.steamExporter.exchangeObject.Game;
import be.mic666.steamExporter.exchangeObject.GameLst;
import be.mic666.steamExporter.gson.GsonParser;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLstApp extends Application {

    private final static int rowsPerPage = 15;
    private TableView<Game> table = new TableView<>();
    private List<Game> gameList = null;
    private Map<Integer, Image> gameCoverMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    private void getGamelstToShow() {
        SteamApiCaller caller = new SteamApiCaller();
        String gamesListWithDetails = caller.getGamesListWithDetails();
        //System.out.println(gamesListWithDetails);
        GameLst gameLst = GsonParser.fromJsonToGameLst(gamesListWithDetails);
        gameList = gameLst.getResponse().getGames();
        gameList.stream().sorted((game1, game2) -> game1.getName().compareTo(game2.getName()));
        for (Game game : gameList) {
            final long timeBeforeLoadingImage = Calendar.getInstance().getTimeInMillis();
            gameCoverMap.put(game.getAppid(), new Image("http://media.steampowered.com/steamcommunity/public/images/apps/" + game.getAppid() + "/" + game.getImgLogoUrl() + ".jpg"));
            final long timeAfterLoadingImage = Calendar.getInstance().getTimeInMillis();
            System.out.println("Loading of image for game : " + game.getName() + " took " + (timeAfterLoadingImage - timeBeforeLoadingImage) + " ms");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Testing javaFx for my steam library");
        getGamelstToShow();
        StackPane root = new StackPane();

        TableColumn<Game, String> appidColumn = new TableColumn<>();
        appidColumn.setText("appId");
        appidColumn.setCellValueFactory(new PropertyValueFactory<>("appid"));

        TableColumn<Game, String> nameColumn = new TableColumn<>();
        nameColumn.setText("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Game, String> playTimeColumn = new TableColumn<>();
        playTimeColumn.setText("Play time");
        playTimeColumn.setCellValueFactory(new PropertyValueFactory<>("playtimeForever"));

        TableColumn<Game, Image> gameLogoColumn = new TableColumn<>();
        gameLogoColumn.setCellValueFactory(cellValueFactory -> {
            Image logo = gameCoverMap.get(cellValueFactory.getValue().getAppid());
                    return new SimpleObjectProperty<>(logo);//TODO PRELOAD IMAGE TO AVOID SLOWNESS
                }
        );
        gameLogoColumn.setCellFactory(tableColumn -> new TableCell<Game, Image>() {
            public void updateItem(Image image, boolean empty) {
                if (image != null) {
                    setText(image.toString());
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    ImageView imageView = new ImageView(image);
                    setGraphic(imageView);
                }
            }
        });

        table.getColumns().addAll(appidColumn, nameColumn, playTimeColumn, gameLogoColumn);
        Pagination paginator = new Pagination(gameList.size() / rowsPerPage);
        paginator.setPageFactory(this::getPageFactory);
        root.getChildren().add(paginator);
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();

    }

    private Node getPageFactory(Integer pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, gameList.size());
        table.setItems(FXCollections.observableArrayList(gameList.subList(fromIndex, toIndex)));
        return new BorderPane(table);
    }
}
