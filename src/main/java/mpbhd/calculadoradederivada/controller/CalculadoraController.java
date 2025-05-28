package mpbhd.calculadoradederivada.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import mpbhd.calculadoradederivada.model.CalculadoraModel;

public class CalculadoraController {

    @FXML
    private TextField limSuperiorField, limInferiorField;
    @FXML
    private Label labelErroDerivada, labelErroIntegral;

    @FXML
    private Button derivadaButton, integralButton;

    @FXML
    private TextField derivadaField, integralField;

    @FXML
    private Label derivadaSaidaPrimeiraOrdem, derivadaSaidaSegundaOrdem;

    @FXML
    private Label integralSaidaPrimeiraOrdem;

    @FXML private HBox limitesHbox;

    @FXML private RadioButton definidaRButton, indefinidaRButton;

    CalculadoraModel calculadoraModel;

    @FXML
    private void initialize() {
        labelErroDerivada.setVisible(false);
        labelErroIntegral.setVisible(false);
        derivadaSaidaPrimeiraOrdem.setVisible(false);
        derivadaSaidaSegundaOrdem.setVisible(false);
        integralSaidaPrimeiraOrdem.setVisible(false);
        definidaRButton.setOnAction(_ -> radioClicado(true));
        indefinidaRButton.setOnAction(_ -> radioClicado(false));
        calculadoraModel = new CalculadoraModel();
    }

    @FXML
    protected void onDerivadaButtonClick() {
        derivadaSaidaPrimeiraOrdem.setVisible(false);
        derivadaSaidaSegundaOrdem.setVisible(false);
        labelErroDerivada.setVisible(false);
        animarCliqueBotao(Color.CYAN, derivadaButton);

        String expressao = derivadaField.getText().trim();
        if (expressao.isEmpty()) {
            mostrarErro(labelErroDerivada, "Digite uma função");
            return;
        }

        try {
            if (expressao.contains("=")) {
                mostrarDerivadaImplicita(expressao);
            } else {
                mostrarDerivadas(expressao);
            }
        } catch (Exception e) {
            mostrarErro(labelErroDerivada, "Erro ao calcular a derivada: " + e.getMessage());
        }
    }

    @FXML
    protected void onIntegralButtonClick() {
        integralSaidaPrimeiraOrdem.setVisible(false);
        labelErroIntegral.setVisible(false);
        animarCliqueBotao(Color.GOLDENROD, integralButton);

        String expressao = integralField.getText().trim();
        if (expressao.isEmpty()) {
            mostrarErro(labelErroIntegral, "Digite uma função");
            return;
        }
        if (!definidaRButton.isSelected() && !indefinidaRButton.isSelected()) {
            mostrarErro(labelErroIntegral, "Selecione o tipo de integral (definida ou indefinida).");
            return;
        }

        try {
            if (definidaRButton.isSelected()) {
                mostrarIntegralDefinida(expressao);
            } else {
                mostrarIntegralIndefinida(expressao);
            }
        } catch (Exception e) {
            mostrarErro(labelErroIntegral, "Erro: " + e.getMessage());
        }
    }

    private void mostrarDerivadaImplicita(String expressao) {
        String resultado = calculadoraModel.calcularDerivadaImplicita(expressao);
        derivadaSaidaPrimeiraOrdem.setText("dy/dx = " + resultado);
        derivadaSaidaPrimeiraOrdem.setVisible(true);
    }

    private void mostrarDerivadas(String expressao) {
        String primeira = calculadoraModel.calcularPrimeiraDerivada(expressao);
        String segunda = calculadoraModel.calcularSegundaDerivada(expressao);
        derivadaSaidaPrimeiraOrdem.setText("x' = " + primeira);
        derivadaSaidaSegundaOrdem.setText("x'' = " + segunda);
        derivadaSaidaPrimeiraOrdem.setVisible(true);
        derivadaSaidaSegundaOrdem.setVisible(true);
    }

    private void mostrarIntegralDefinida(String expressao) {
        String limInf = limInferiorField.getText().trim();
        String limSup = limSuperiorField.getText().trim();
        if (limInf.isEmpty() || limSup.isEmpty()) {
            mostrarErro(labelErroIntegral, "Preencha os limites inferior e superior.");
            return;
        }
        String resultado = calculadoraModel.calcularIntegralDefinida(expressao, limInf, limSup);
        integralSaidaPrimeiraOrdem.setText("∫ " + limSup + ", " + limInf + " = " + resultado);
        integralSaidaPrimeiraOrdem.setVisible(true);
    }

    private void mostrarIntegralIndefinida(String expressao) {
        String resultado = calculadoraModel.calcularIntegralIndef(expressao);
        integralSaidaPrimeiraOrdem.setText("∫ = " + resultado + " + C");
        integralSaidaPrimeiraOrdem.setVisible(true);
    }

    private void radioClicado(boolean showLimites) {
        labelErroIntegral.setVisible(false);
        integralSaidaPrimeiraOrdem.setVisible(false);
        limitesHbox.setVisible(showLimites);
    }

    private void mostrarErro(Label label, String mensagem) {
        label.setVisible(true);
        label.setText(mensagem);
    }

    private void animarCliqueBotao(Color cor, Button botao) {
        DropShadow glow = new DropShadow();
        glow.setColor(cor);
        glow.setRadius(0);
        glow.setInput(null);
        botao.setEffect(glow);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.radiusProperty(), 0)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(glow.radiusProperty(), 30)),
                new KeyFrame(Duration.seconds(0.55), new KeyValue(glow.radiusProperty(), 0))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
}