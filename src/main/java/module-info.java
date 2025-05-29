module com.example.trafficlightwithjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trafficlightwithjava to javafx.fxml;
    exports com.example.trafficlightwithjava.view;
    opens com.example.trafficlightwithjava.view to javafx.fxml;
    exports com.example.trafficlightwithjava;
}














