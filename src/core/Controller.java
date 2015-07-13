package core;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Metals;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Администратор on 13.07.2015.
 */
public class Controller {

    @FXML
    private TableView<Metals> metalTable;
    @FXML
    private TableColumn<Metals, String> firstNameColumn;
    @FXML
    private TableColumn<Metals, Double> lastNameColumn;

    private Main mainApp;

    public Controller() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        metalTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        metalTable.setItems(mainApp.getPersonData());
    }

    private void showPersonDetails(Metals metal) {
        if (metal != null) {
            // Fill the labels with info from the person object.
            firstNameColumn.setText(metal.getTitle());
            lastNameColumn.setText(Double.toString(metal.getPrice()));
        } else {
            firstNameColumn.setText("");
            lastNameColumn.setText("");
        }
    }

    @FXML
    private void handleDeleteMetal() {
        int selectedIndex = metalTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            metalTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("Не был выбран метал")
                    .message("Пожалуйста выбирете метал в таблице")
                    .showWarning();
        }
    }

}
