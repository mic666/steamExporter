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
import java.util.List;

public class GameLstApp extends Application {

    private final static int rowsPerPage = 15;
    private TableView<Game> table = new TableView<>();
    private List<Game> data = null;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    private void getGamelstToShow() {
        SteamApiCaller caller = new SteamApiCaller();
        String gamesListWithDetails = caller.getGamesListWithDetails();
        //System.out.println(gamesListWithDetails);
        GameLst gameLst = GsonParser.fromJsonToGameLst(gamesListWithDetails);
        data = gameLst.getResponse().getGames();
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
                    Image logo = new Image("http://media.steampowered.com/steamcommunity/public/images/apps/" + cellValueFactory.getValue().getAppid() + "/" + cellValueFactory.getValue().getImgLogoUrl() + ".jpg");
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
        Pagination paginator = new Pagination(data.size() / rowsPerPage);
        paginator.setPageFactory(this::getPageFactory);
        root.getChildren().add(paginator);
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();

    }

    private Node getPageFactory(Integer pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        return new BorderPane(table);
    }
}
