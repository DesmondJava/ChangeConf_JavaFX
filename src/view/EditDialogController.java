package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ConfValue;
import org.controlsfx.dialog.Dialogs;

public class EditDialogController {

        @FXML
        private TextField title;
        @FXML
        private TextField value;
        @FXML
        private Label description;

        private Stage dialogStage;
        private ConfValue confValue;
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

        public void setMetal(ConfValue confValue) {
            this.confValue = confValue;
            this.title.setText(confValue.getTitle());
            this.value.setText(confValue.getValue());
            this.description.setText(confValue.getDescription());
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
                confValue.setTitle(title.getText());
                confValue.setPrice(value.getText());
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
            if (value.getText() == null || value.getText().length() == 0) {
                errorMessage += "No valid value!\n";
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
