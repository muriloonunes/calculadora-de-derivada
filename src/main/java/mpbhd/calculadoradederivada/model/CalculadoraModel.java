package mpbhd.calculadoradederivada.model;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

public class CalculadoraModel {

    public String calcularPrimeiraDerivada(String expressao) {
        ExprEvaluator util = new ExprEvaluator();

        IExpr primeira = util.evaluate("D(" + expressao + ", x)");
        System.out.println("Derivada: " + primeira);
        return primeira.toString();
    }

    public String calcularSegundaDerivada(String expressao) {
        ExprEvaluator util = new ExprEvaluator();

        IExpr segunda = util.evaluate("D(" + expressao + ", {x, 2})");
        System.out.println("Derivada: " + segunda);
        return segunda.toString();
    }

    public String calcularIntegral(String expressao) {
        ExprEvaluator util = new ExprEvaluator();

        IExpr integral = util.evaluate("Integrate(" + expressao + ", x)");
        System.out.println("Integral: " + integral);
        return integral.toString();
    }
}
