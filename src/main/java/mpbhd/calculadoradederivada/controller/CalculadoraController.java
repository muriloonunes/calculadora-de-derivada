package mpbhd.calculadoradederivada.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
    private TextField entradaField, saidaField;

    @FXML
    private TabPane tabPainel;

    @FXML
    private void initialize() {
        alterarVisibilidade(labelErroDerivada);
        alterarVisibilidade(labelErroIntegral);
    }

    @FXML
    protected void onDerivadaButtonClick() {
        System.out.println("Derivada clicado");
        ExprEvaluator util = new ExprEvaluator();
        IExpr result = util.evaluate("D(x^2 + 3*x, x)");  // Derivada de x^2 + 3x
        System.out.println("Derivada: " + result);
    }

    @FXML
    protected void onIntegralButtonClick() {
        System.out.println("Integral clicado");
    }

    private void alterarVisibilidade(Label label) {
        label.setVisible(!label.isVisible());
    }
}