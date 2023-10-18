package fr.univtln.bruno.demos.demojfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PersonApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(PersonApplication.class.getResource("person-view.fxml"));

        Locale currentLocale = Locale.getDefault();
        var locale = new Locale(currentLocale.getLanguage(), currentLocale.getCountry());

        var demoJfxI18N = ResourceBundle.getBundle("demoJFX_I18N", locale);

        fxmlLoader.setResources(demoJfxI18N);
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Person manager");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }
}