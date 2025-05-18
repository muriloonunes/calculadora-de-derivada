package mpbhd.calculadoradederivada.model;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

public class CalculadoraModel {

    private final ExprEvaluator evaluator = new ExprEvaluator();

    public String calcularPrimeiraDerivada(String expressao) {
        return avaliarExpressao("D(" + expressao + ", x)");
    }

    public String calcularSegundaDerivada(String expressao) {
        return avaliarExpressao("D(" + expressao + ", {x, 2})");
    }

    public String calcularIntegral(String expressao) {
        return avaliarExpressao("Integrate(" + expressao + ", x)");
    }

    private String avaliarExpressao(String entrada) {
        try {
            IExpr resultado = evaluator.evaluate(entrada);
            return resultado.toString();
        } catch (Exception e) {
            throw new RuntimeException("Expressão inválida.");
        }
    }
}
