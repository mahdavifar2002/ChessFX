package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ScoreItem;

public class ScoreboardBox extends GameView {

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Scoreboard");
        //window.setMinWidth(250);

        Label label = new Label();
        label.setText("Please enter new password:");

        VBox layout = new VBox(10);
        layout.getStylesheets().add("view/style/table.css");

        //Table Columns
        int minWidth = 100;
        TableColumn<ScoreItem, String> nameColumn = new TableColumn<>("User");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setMinWidth(minWidth);
        TableColumn<ScoreItem, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setMinWidth(minWidth);
        TableColumn<ScoreItem, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setMinWidth(minWidth);
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<ScoreItem, Integer> drawsColumn = new TableColumn<>("Draws");
        drawsColumn.setMinWidth(minWidth);
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));
        TableColumn<ScoreItem, Integer> loosesColumn = new TableColumn<>("Looses");
        loosesColumn.setMinWidth(minWidth);
        loosesColumn.setCellValueFactory(new PropertyValueFactory<>("looses"));

        TableView<ScoreItem> table = new TableView<>();
        table.setItems(manager.getScores());
        System.out.println(manager.getScores().size());
        table.getColumns().addAll(nameColumn, scoreColumn, winsColumn, drawsColumn, loosesColumn);

        layout.getChildren().add(table);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
