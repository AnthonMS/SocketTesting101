package javaFXChatApp;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFXGUIController
{

    public TextField txtFUserName;
    public TextField txtFServerHost;

    public void onActionConnect2(ActionEvent actionEvent)
    {

    }

    public void onActionCancelConnect(ActionEvent actionEvent)
    {
        // Gets the window that is holding the txtFieldUserName
        // Then closes it, as the user clicks Cancel
        Stage loginStage = (Stage) txtFUserName.getScene().getWindow();
        loginStage.close();
    }
}
