package view;

import core.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ConfValue;
import model.PriceMetal;
import model.SSHConnect;
import org.controlsfx.dialog.Dialogs;

import java.util.List;

public class Controller {

    //Connect
    @FXML
    private TextField login;
    @FXML
    private TextField password;

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

    //For join different department
    @FXML
    private ComboBox<String> loadDiffDepart;

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
    private ObservableList<CheckBox> checkboxesItemsDepartment;


    private Main mainApp;

    public Controller() {
    }

    @FXML
    private void initialize() {
        //Инициализируем таблицу (колонки)
        sort.setCellValueFactory(cellData -> cellData.getValue().sortProperty());
        title.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        value.setCellValueFactory(cellData -> cellData.getValue().valueProperty());

        //Добавляем все отделения CheckBox (галочки) в массив
        checkboxesItemsDepartment = FXCollections.observableArrayList();
        checkboxesItemsDepartment.addAll(department1, department2, department3, department4,
                department5, department6, department7, department9, department0);

        //Проходим по всем галочкам и добавляем с них текст в выпадающий список при загрузки конф файла
        ObservableList<String> listItemsDepartment = FXCollections.observableArrayList();
        for(CheckBox department : checkboxesItemsDepartment){
            listItemsDepartment.add(department.getText());
        }
        loadDiffDepart.setItems(listItemsDepartment);
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

    @FXML
    private void typeChoosenDepartments() {
        int count = 0;
        String choices = "";
        for (CheckBox department : checkboxesItemsDepartment) {
            if (department.isSelected()) {
                count++;
                choices += department.getText() + "\n";
            }
        }
        list_departments.setText(choices);
        count_departments.setText("Количество выбраных отеделений: " + count);
    }

    @FXML
    private void loadConfFile(){
        mainApp.getData().removeAll(mainApp.getData());
        SSHConnect connect = new SSHConnect(loadDiffDepart.getValue(), login.getText(), password.getText());
        List<ConfValue> dataFromFile = connect.loadConfFileFromSSH();
        mainApp.getData().addAll(dataFromFile);
    }

    @FXML
    private void saveNewValue(){

    }

}
