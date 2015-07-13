package core;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Metals;

import java.io.IOException;

public class Main extends Application {

    private ObservableList<Metals> metalData = FXCollections.observableArrayList();
    private Stage window;
    private BorderPane rootLayout;

    public Main(){
        metalData.add(new Metals("Au333", 237.41));
        metalData.add(new Metals("Au500", 356.47));
        metalData.add(new Metals("Au750", 534.70));
        metalData.add(new Metals("Au809", 576.76));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;
        this.window.setTitle("Pectorale configuration");
        showPersonOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout = (BorderPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
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



    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Metals> getPersonData() {
        return metalData;
    }
}
