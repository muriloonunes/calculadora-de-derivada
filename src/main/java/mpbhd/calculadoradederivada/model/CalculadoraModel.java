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

    //todo ver se precisa dessa e das presentes nos radiobuttons
//    public String calcularDerivadaImplicita(String expressao) {
//        try {
//            String derivada = "D(" + expressao + ", x)";
//            String solucao = "Solve(" + derivada + " == 0, D(y, x))";
//            return avaliarExpressao(solucao);
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao calcular derivada implícita.");
//        }
//    }


    private String avaliarExpressao(String entrada) {
        try {
            IExpr resultado = evaluator.evaluate(entrada);
            return resultado.toString();
        } catch (Exception e) {
            throw new RuntimeException("Expressão inválida. Use apenas letras, números e operadores matemáticos como + - * / ^.");
        }
    }
}
