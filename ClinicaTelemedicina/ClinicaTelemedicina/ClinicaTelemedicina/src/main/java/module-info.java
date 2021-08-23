module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires mail;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.utils;
    opens org.example.utils to javafx.fxml;
}