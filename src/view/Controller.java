package view;

import core.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ConfValue;
import org.controlsfx.dialog.Dialogs;

public class Controller {

    @FXML
    private TableView<ConfValue> table;
    @FXML
    private TableColumn<ConfValue, String> firstNameColumn;
    @FXML
    private TableColumn<ConfValue, String> lastNameColumn;

    private Main mainApp;

    public Controller() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        table.setItems(mainApp.getData());
    }

    @FXML
    private void handleDeleteMetal() {
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            table.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No row selected")
                    .message("Please select a row in the table.")
                    .showWarning();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewMetal() {
        ConfValue tempMetal = new ConfValue();
        boolean okClicked = mainApp.showMetalEditDialog(tempMetal);
        if (okClicked) {
            mainApp.getData().add(tempMetal);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditMetal() {
        ConfValue selectedMetal = table.getSelectionModel().getSelectedItem();
        if (selectedMetal != null) {
            mainApp.showMetalEditDialog(selectedMetal);
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No row selected")
                    .message("Please select a row in the table.")
                    .showWarning();
        }
    }

}
