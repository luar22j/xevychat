package net.xevy;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void cerrarsesion() throws IOException {
        App.setRoot("primary");
    }
}