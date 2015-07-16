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
import view.Controller;
import view.EditDialogController;

import java.io.IOException;

public class Main extends Application {

    private ObservableList<ConfValue> data = FXCollections.observableArrayList();
    private Stage window;
    private BorderPane rootLayout;

    public Main(){
        data.add(new ConfValue("Au333", "237.41", "Золото", "Цены на драгметаллы"));
        data.add(new ConfValue("Au500", "356.47", "Золото", "Цены на драгметаллы"));
        data.add(new ConfValue("Au750", "534.70", "Золото", "Цены на драгметаллы"));
        data.add(new ConfValue("interest_640", "0.921", "Проценты", "# проценты за пользование кредитом в зависимости от суммы кредита\n" +
                "# сумма - целое число\n" +
                "# процент - в формате 0.0999 (указать обязательно: 1 цифру в целой части, десятичную точку и хотя бы 1 цифру в дробной части)\n" +
                "# пример: кредит до 5238 (т.е. кредит меньше 5238) - процент 0.816"));
        data.add(new ConfValue("interest_1289", "0.885", "Проценты", "# проценты за пользование кредитом в зависимости от суммы кредита\n" +
                "# сумма - целое число\n" +
                "# процент - в формате 0.0999 (указать обязательно: 1 цифру в целой части, десятичную точку и хотя бы 1 цифру в дробной части)\n" +
                "# пример: кредит до 5238 (т.е. кредит меньше 5238) - процент 0.816"));
        data.add(new ConfValue("credit_5", "72.71", "Кредит", "величина суммы кредита зависит от срока залога"));
        data.add(new ConfValue("credit_7", "72.56", "Кредит", "величина суммы кредита зависит от срока залога"));
        data.add(new ConfValue("credit_8", "72.49", "Кредит", "величина суммы кредита зависит от срока залога"));
        data.add(new ConfValue("default_term", "10", "Разное", "срок залога по умолчанию"));
        data.add(new ConfValue("serial_port", "/dev/ttyS0", "Разное", "файл устройства COM-порта"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;
        this.window.setTitle("Pectorale configuration");
        showMetalOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/root.fxml"));
            rootLayout = (BorderPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMetalOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/root.fxml"));
            rootLayout = (BorderPane) loader.load();

            Controller controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showMetalEditDialog(ConfValue metal) {
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
            controller.setMetal(metal);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<ConfValue> getData() {
        return data;
    }
}
