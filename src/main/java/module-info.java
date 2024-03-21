module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.base;
    requires bigaNotFx;
    requires firebase.admin;

    opens com.example to javafx.fxml;
    exports com.example;
}
