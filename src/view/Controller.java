package view;

import core.Main;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ConfValue;
import model.PriceMetal;
import org.controlsfx.dialog.Dialogs;

public class Controller {

    @FXML
    private TextField filterField;
    //Gold and silver fields for change price
    @FXML
    private TextField gold;
    @FXML
    private TextField silver;

    //Table
    @FXML
    private TableView<ConfValue> table;
    //First column - type
    @FXML
    private TableColumn<ConfValue, String> sort;
    //Second column - key
    @FXML
    private TableColumn<ConfValue, String> title;
    //Third column - value
    @FXML
    private TableColumn<ConfValue, String> value;

    //CheckBox
    @FXML
    private CheckBox department1;
    @FXML
    private CheckBox department2;
    @FXML
    private CheckBox department3;
    @FXML
    private CheckBox department4;
    @FXML
    private CheckBox department5;
    @FXML
    private CheckBox department6;
    @FXML
    private CheckBox department7;
    @FXML
    private CheckBox department9;
    @FXML
    private CheckBox department0;
    @FXML
    private Label count_departments;
    @FXML
    private Label list_departments;


    private Main mainApp;

    public Controller() {
    }

    @FXML
    private void initialize() {
        sort.setCellValueFactory(cellData -> cellData.getValue().sortProperty());
        title.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        value.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        ObservableList data = mainApp.getData();
        FilteredList<ConfValue> filteredData = new FilteredList<>(data, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(confValue -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (confValue.getSort().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (confValue.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        SortedList<ConfValue> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     */
    @FXML
    private void handleEditConfValue() {
        ConfValue selectedRow = table.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            mainApp.showConfValueEditDialog(selectedRow);
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
     * Called when the user clicks the ok button around gold field. Changes price of the golds
     */
    @FXML
    private void changeGold(){
        String priceGold = gold.getText();
        try {
            double new_price = Double.parseDouble(priceGold);
            PriceMetal.changeGoldPrice(mainApp.getData(), new_price);
        } catch (NumberFormatException e) {
            Dialogs.create()
                    .title("Invalid Fields")
                    .masthead("Please correct invalid fields")
                    .message("Type pls number double like '20.4'")
                    .showWarning();
        }
        gold.setText("");
    }

    /**
     * Called when the user clicks the ok button around silver field. Changes price of the silvers
     */
    @FXML
    private void changeSilver(){
        String priceSilver = silver.getText();
        try {
            double new_price = Double.parseDouble(priceSilver);
            PriceMetal.changeSilverPrice(mainApp.getData(), new_price);
        } catch (NumberFormatException e){
            Dialogs.create()
                    .title("Invalid Fields")
                    .masthead("Please correct invalid fields")
                    .message("Type pls number double like '20.4'")
                    .showWarning();
        }
        silver.setText("");
    }

    @FXML private void typeChoosenDepartments(){
        int count = 0;
        String choices = "";
        if(department1.isSelected()){
            count++;
            choices += department1.getText() + "\n";
        }
        if(department2.isSelected()){
            count++;
            choices += department2.getText() + "\n";
        }
        if(department3.isSelected()){
            count++;
            choices += department3.getText() + "\n";
        }
        if(department4.isSelected()){
            count++;
            choices += department4.getText() + "\n";
        }
        if(department5.isSelected()){
            count++;
            choices += department5.getText() + "\n";
        }
        if(department6.isSelected()){
            count++;
            choices += department6.getText() + "\n";
        }
        if(department7.isSelected()){
            count++;
            choices += department7.getText() + "\n";
        }
        if(department9.isSelected()){
            count++;
            choices += department9.getText() + "\n";
        }
        if(department0.isSelected()){
            count++;
            choices += department0.getText() + "\n";
        }
        list_departments.setText(choices);
        count_departments.setText("Количество выбраных отеделений: " + count);
    }
}
