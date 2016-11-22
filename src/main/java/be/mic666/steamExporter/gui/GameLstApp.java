package be.mic666.steamExporter.gui;

import be.mic666.steamExporter.api.SteamApiCaller;
import be.mic666.steamExporter.exchangeObject.Game;
import be.mic666.steamExporter.exchangeObject.GameLst;
import be.mic666.steamExporter.gson.GsonParser;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameLstApp extends Application {

    public static void main(String[] args) throws IOException {

//        GameLst gameLst = getGamelstToShow();
//        System.out.printf("Steam result : " + gameLst);
        //System.out.print("please enter the path to save the file linux way : ");
        //Scanner in = new Scanner(System.in);
        //String inputStrPath = in.next();
        //Path outputFilePath = Paths.get(inputStrPath + "/mySteamGameLst.txt");
        //Files.write(outputFilePath, gameLst.toString().getBytes());
        //System.out.printf("succesfully save steam game list to file ==> " + outputFilePath.toString());
        launch(args);
    }

    private static GameLst getGamelstToShow() {
        SteamApiCaller caller = new SteamApiCaller();
        String gamesListWithDetails = caller.getGamesListWithDetails();
        //System.out.println(gamesListWithDetails);
        return GsonParser.fromJsonToGameLst(gamesListWithDetails);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Testing javaFx for my steam library");
        GameLst gameLst = getGamelstToShow();
        StackPane root = new StackPane();
        TableView<Game> table = new TableView<>();
        table.setItems(FXCollections.observableList(gameLst.getResponse().getGames()));

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
                    return new SimpleObjectProperty<>(logo);
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

        root.getChildren().add(table);
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();

    }
}
