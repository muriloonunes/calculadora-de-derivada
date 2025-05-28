package mpbhd.calculadoradederivada.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mpbhd.calculadoradederivada.controller.CalculadoraController;

import java.io.IOException;
import java.util.Objects;

public class CalculadoraApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(Objects.requireNonNull(CalculadoraApplication.class.getResource("/font/Roboto-Regular.ttf")).toExternalForm(), 10);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mpbhd/calculadoradederivada/calculadora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 560);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());

        CalculadoraController calculadoraController = fxmlLoader.getController();

        stage.getIcons().add(new Image(Objects.requireNonNull(CalculadoraApplication.class.getResourceAsStream("/images/icon.png"))));
        stage.setTitle("Calculadora de Derivadas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}