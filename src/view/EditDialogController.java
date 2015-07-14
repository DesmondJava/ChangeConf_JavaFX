package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Metals;
import org.controlsfx.dialog.Dialogs;

public class EditDialogController {

        @FXML
        private TextField title;
        @FXML
        private TextField price;

        private Stage dialogStage;
        private Metals metal;
        private boolean okClicked = false;

        /**
         * Initializes the controller class. This method is automatically called
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
        }

        /**
         * Sets the stage of this dialog.
         *
         * @param dialogStage
         */
        public void setDialogStage(Stage dialogStage) {
            this.dialogStage = dialogStage;
        }

        public void setMetal(Metals metal) {
            this.metal = metal;
            title.setText(metal.getTitle());
            price.setText(Double.toString(metal.getPrice()));
        }

        /**
         * Returns true if the user clicked OK, false otherwise.
         *
         * @return
         */
        public boolean isOkClicked() {
            return okClicked;
        }

        /**
         * Called when the user clicks ok.
         */
        @FXML
        private void handleOk() {
            if (isInputValid()) {
                metal.setTitle(title.getText());
                metal.setPrice(Double.parseDouble(price.getText()));
                okClicked = true;
                dialogStage.close();
            }
        }

        /**
         * Called when the user clicks cancel.
         */
        @FXML
        private void handleCancel() {
            dialogStage.close();
        }

        /**
         * Validates the user input in the text fields.
         *
         * @return true if the input is valid
         */
        private boolean isInputValid() {
            String errorMessage = "";

            if (title.getText() == null || title.getText().length() == 0) {
                errorMessage += "No valid title!\n";
            }
            if (price.getText() == null || price.getText().length() == 0) {
                errorMessage += "No valid price!\n";
            }
            else {
                // try to parse the postal code into an int.
                try {
                    Double.parseDouble(price.getText());
                } catch (NumberFormatException e) {
                    errorMessage += "No valid price (must be an double like 20.4)!\n";
                }
            }
            if (errorMessage.length() == 0) {
                return true;
            } else {
                // Show the error message.
                Dialogs.create()
                        .title("Invalid Fields")
                        .masthead("Please correct invalid fields")
                        .message(errorMessage)
                        .showError();
                return false;
            }
        }
}
