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

//    public String calcularDerivadaImplicita(String expressao) {
//        try {
//            String derivada = "D(" + expressao + ", x)";
//            String solucao = "Solve(" + expressao + ", {x, y})";
//            return avaliarExpressao("Solve(2*x - 2*y - y^2 + (-2*x - 2*x*y - 3*y^2)*dy == 0, dy)");
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao calcular derivada implícita.");
//        }
//    }

    //public String calcularDerivadaImplicita(String expressao) {
    //    try {
    //        String derivada = "Solve(D(" + expressao + ", x) + D(" + expressao + ", y)*D(y, x) == 0, D(y, x))";
    //        return avaliarExpressao(derivada);
    //    } catch (Exception e) {
    //        throw new RuntimeException("Erro ao calcular derivada implícita: " + e.getMessage());
    //    }
    //}

    public String calcularDerivadaImplicita(String expressao) {
        try {
            if (!expressao.contains("=")) {
                throw new IllegalArgumentException("A expressão deve conter '=' para derivada implícita.");
            }

            String[] lados = expressao.split("=");
            if (lados.length != 2) {
                throw new IllegalArgumentException("Expressão mal formatada para derivada implícita.");
            }

            String fxy = "(" + lados[0].trim() + ")-(" + lados[1].trim() + ")";
            String derivada = "-(D(" + fxy + ", x) / D(" + fxy + ", y))";
            return avaliarExpressao(derivada);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular derivada implícita: " + e.getMessage());
        }
    }


    public String calcularIntegralIndef(String expressao) {
        return avaliarExpressao("Integrate(" + expressao + ", x)");
    }

    public String calcularIntegralDefinida(String expressao, String limiteInferior, String limiteSuperior) {
        return avaliarExpressao("Integrate(" + expressao + ", {x, " + limiteInferior + ", " + limiteSuperior + "})");
    }

    private String avaliarExpressao(String entrada) {
        try {
            IExpr resultado = evaluator.eval(entrada);
            return resultado.toString();
        } catch (Exception e) {
            throw new RuntimeException("Expressão inválida. Use apenas letras, números e operadores matemáticos como + - * / ^.");
        }
    }
}
