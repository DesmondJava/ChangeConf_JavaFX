package view;

import core.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Metals;
import org.controlsfx.dialog.Dialogs;

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
//        showMetalDetails(null);

        // Listen for selection changes and show the person details when changed.
   /*     metalTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMetalDetails(newValue));*/
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        metalTable.setItems(mainApp.getPersonData());
    }

 /*   private void showMetalDetails(Metals metal) {
        if (metal != null) {
            // Fill the labels with info from the person object.
            firstNameColumn.setText("yo");
            lastNameColumn.setText(Double.toString(metal.getPrice()));
        } else {
            firstNameColumn.setText("");
            lastNameColumn.setText("");
        }
    }*/

    @FXML
    private void handleDeleteMetal() {
        int selectedIndex = metalTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            metalTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Metal Selected")
                    .message("Please select a metal in the table.")
                    .showWarning();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewMetal() {
        Metals tempMetal = new Metals();
        boolean okClicked = mainApp.showMetalEditDialog(tempMetal);
        if (okClicked) {
            mainApp.getPersonData().add(tempMetal);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditMetal() {
        Metals selectedMetal = metalTable.getSelectionModel().getSelectedItem();
        if (selectedMetal != null) {
            boolean okClicked = mainApp.showMetalEditDialog(selectedMetal);
         /*   if (okClicked) {
                showMetalDetails(selectedMetal);
            }*/

        } else {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Metal Selected")
                    .message("Please select a metal in the table.")
                    .showWarning();
        }
    }

}
