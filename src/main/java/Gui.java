import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Gui extends Application {
    private Controller controller = new Controller(this);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MongoDB CRUD Operations");
        
        Label idLabel = new Label("ID:");
        TextField idTxtFld = new TextField();
        
        Label nameLabel = new Label("Name:");
        TextField nameTxtFld = new TextField();
        
        Label ageLabel = new Label("Age:");
        TextField ageTxtFld = new TextField();
        
        Label cityLabel = new Label("City:");
        TextField cityTxtFld = new TextField();

        Button addBtn = new Button("Add");
        Button readBtn = new Button("Read");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");

        idTxtFld.setPrefWidth(150);
        idTxtFld.setPrefHeight(50);
        nameTxtFld.setPrefWidth(150);
        nameTxtFld.setPrefHeight(50);
        ageTxtFld.setPrefWidth(150);
        ageTxtFld.setPrefHeight(50);
        cityTxtFld.setPrefWidth(150);
        cityTxtFld.setPrefHeight(50);

        addBtn.setPrefWidth(150);
        addBtn.setPrefHeight(50);
        updateBtn.setPrefWidth(150);
        updateBtn.setPrefHeight(50);
        deleteBtn.setPrefWidth(150);
        deleteBtn.setPrefHeight(50);
        readBtn.setPrefWidth(150);
        readBtn.setPrefHeight(50);

        GridPane gridPane = new GridPane();

        gridPane.add(idLabel, 0, 0);
        gridPane.add(idTxtFld, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameTxtFld, 1, 1);
        gridPane.add(ageLabel, 0, 2);
        gridPane.add(ageTxtFld, 1, 2);
        gridPane.add(cityLabel, 0, 3);
        gridPane.add(cityTxtFld, 1, 3);

        gridPane.add(addBtn, 0, 4);
        gridPane.add(readBtn, 1, 4);
        gridPane.add(deleteBtn, 1, 5);
        gridPane.add(updateBtn, 0, 5);

        Scene scene = new Scene(gridPane, 300, 200);

        addBtn.setOnAction(e -> controller.add(idTxtFld.getText(), nameTxtFld.getText(), ageTxtFld.getText(), cityTxtFld.getText()));
        updateBtn.setOnAction(e -> controller.update(idTxtFld.getText(), nameTxtFld.getText(), ageTxtFld.getText(), cityTxtFld.getText()));
        deleteBtn.setOnAction(e -> controller.delete(idTxtFld.getText()));
        readBtn.setOnAction(e -> controller.read(idTxtFld.getText()));

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
