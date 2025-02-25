module com.example.project_ap_ver2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.project_ap_ver2 to javafx.fxml;
    exports com.example.project_ap_ver2;
}