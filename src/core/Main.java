package core;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ConfValue;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import view.Controller;
import view.EditDialogController;

import java.io.IOException;

public class Main extends Application {

    private ObservableList<ConfValue> data = FXCollections.observableArrayList();

    public Stage getWindow() {
        return window;
    }

    private Stage window;
    private BorderPane rootLayout;

    public Main(){
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        this.window.setTitle("Pectorale configuration");
        showConfValuesOverview();
    }

    public void showConfValuesOverview() {
        try {
            // Load table overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/root.fxml"));
            rootLayout = (BorderPane) loader.load();

            Controller controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            window.setScene(scene);
            window.setMinWidth(693);
            window.setMinHeight(600);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showConfValueEditDialog(ConfValue confValue) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/EditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConfValue(confValue);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeProgram(){
        Action response = Dialogs.create()
                .title("Close the program")
                .masthead("Close the program")
                .message("Are you sure you want to exit?")
                .showConfirm();
        if (response == Dialog.Actions.YES) {
            window.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<ConfValue> getData() {
        return data;
    }
}
