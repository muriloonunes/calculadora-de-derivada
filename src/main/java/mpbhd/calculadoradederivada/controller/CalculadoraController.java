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
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

public class CalculadoraController {
    @FXML
    private Label entradaLabel, saidaLabel;

    @FXML
    private Label labelErroDerivada, labelErroIntegral;

    @FXML
    private Button derivadaButton, integralButton;

    @FXML
    private TextField derivadaField, integralField;

    @FXML
    private TabPane tabPainel;

    @FXML
    private void initialize() {
        alterarVisibilidade(labelErroDerivada);
        alterarVisibilidade(labelErroIntegral);
    }

    @FXML
    protected void onDerivadaButtonClick() {
        animarCliqueBotao(Color.CYAN, derivadaButton);
        System.out.println("Derivada clicado");
        ExprEvaluator util = new ExprEvaluator();
        IExpr result = util.evaluate("D(x^2 + 3*x, x)");  // Derivada de x^2 + 3x
        System.out.println("Derivada: " + result);
    }

    @FXML
    protected void onIntegralButtonClick() {
        animarCliqueBotao(Color.GOLDENROD, integralButton);
        System.out.println("Integral clicado");
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