package mpbhd.calculadoradederivada.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import mpbhd.calculadoradederivada.model.CalculadoraModel;

public class CalculadoraController {
    @FXML
    private Label labelErroDerivada, labelErroIntegral;

    @FXML
    private Button derivadaButton, integralButton;

    @FXML
    private TextField derivadaField, integralField;

    @FXML
    private Label derivadaSaidaPrimeiraOrdem, derivadaSaidaSegundaOrdem;

    @FXML
    private Label integralSaidaPrimeiraOrdem, integralSaidaSegundaOrdem;

    CalculadoraModel calculadoraModel;

    @FXML
    private void initialize() {
        //hartur viado do caralho
        alterarVisibilidade(labelErroDerivada);
        alterarVisibilidade(labelErroIntegral);
        integralSaidaSegundaOrdem.setText("");
        calculadoraModel = new CalculadoraModel();
    }

    @FXML
    protected void onDerivadaButtonClick() {
        animarCliqueBotao(Color.CYAN, derivadaButton);

        System.out.println("Derivada clicado");

        String expressaoD = derivadaField.getText();

        if (expressaoD.isEmpty() || expressaoD.equals(" ")) {
            labelErroDerivada.setVisible(true);
            labelErroDerivada.setText("Campo vazio");
            return;
        }

        // "A calculadora tem q fazer derivada implícita (mas não vou cobrar trigonométricas, logarítmicas, ..., somente polinomiais)
        // e integral definida/indefinida"

        try {
            String primeira = calculadoraModel.calcularPrimeiraDerivada(expressaoD);
            String segunda = calculadoraModel.calcularSegundaDerivada(expressaoD);

//            saidaLabel.setText("Primeira ordem: " + primeira + "\n" + "Segunda ordem: " + segunda);

            derivadaSaidaPrimeiraOrdem.setText("1ª Derivada: " + primeira);
            derivadaSaidaSegundaOrdem.setText("2ª Derivada: " + segunda);

            labelErroDerivada.setVisible(false);

        } catch (Exception e) {
            System.out.println("Erro ao calcular a derivada: " + e.getMessage());
            labelErroDerivada.setVisible(true);
            labelErroDerivada.setText("Erro ao calcular a derivada: " + e.getMessage());
        }
    }

    @FXML
    protected void onIntegralButtonClick() {
        animarCliqueBotao(Color.GOLDENROD, integralButton);

        System.out.println("Integral clicado");

        String expressao = integralField.getText().trim();

        if (expressao.isEmpty() || expressao.equals(" ")) {
            mostrarErro(labelErroIntegral, "Campo vazio.");
            return;
        }

        //todo fazer a diferenciação se é definida ou indef

        try {
            String resultado = calculadoraModel.calcularIntegralIndef(expressao);
//            saidaLabel.setText("Integral: " + resultado);

            integralSaidaPrimeiraOrdem.setText("Integral: " + resultado);
            //todo conferir o uso do +C

            labelErroIntegral.setVisible(false);
        } catch (Exception e) {
            mostrarErro(labelErroIntegral, e.getMessage());
        }

    }

    private void mostrarErro(Label label, String mensagem) {
        label.setText(mensagem);
        label.setVisible(true);
    }

    private void alterarVisibilidade(Label label) {
        label.setVisible(!label.isVisible());
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