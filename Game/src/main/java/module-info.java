module com.example.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.game to javafx.fxml;
    exports com.example.game;
    exports com.example.game.Arcanoid;
    opens com.example.game.Arcanoid to javafx.fxml;
}