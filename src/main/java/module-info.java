module fr.univtln.bruno.demos.demojfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.logging;
    requires java.sql;
    requires java.naming;

    requires static lombok;
    requires com.h2database;
    requires java.desktop;

    opens fr.univtln.bruno.demos.demojfx to javafx.fxml;
    exports fr.univtln.bruno.demos.demojfx;
    exports fr.univtln.bruno.demos.demojfx.model;
    opens fr.univtln.bruno.demos.demojfx.model to javafx.fxml;
}