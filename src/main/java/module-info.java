module com.example.multimedia2021 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires android.json;
    requires javafx.media;

    opens com.example.multimedia2021 to javafx.fxml;
    exports com.example.multimedia2021;
}