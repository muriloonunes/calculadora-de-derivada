package mpbhd.calculadoradederivada.model;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CalculadoraModel {
    private final ExprEvaluator evaluator = new ExprEvaluator();

    public String calcularPrimeiraDerivada(String expressao) {
        return avaliarExpressao("D(" + expressao + ", x)");
    }

    public String calcularSegundaDerivada(String expressao) {
        return avaliarExpressao("D(" + expressao + ", {x, 2})");
    }

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

    public Image criarDerivadaImage(String primeira, String segunda) throws IOException {
        String primeiraLaTeX = converterParaLatex(primeira);
        String segundaLaTeX = converterParaLatex(segunda);
        StringBuilder sb = new StringBuilder("\\begin{aligned}");
        sb.append("x^{\\prime} &= ");
        sb.append(primeiraLaTeX);
        sb.append(" \\\\");
        sb.append("x^{\\prime\\prime} &= ");
        sb.append(segundaLaTeX);
        sb.append("\\end{aligned}");

        return gerarImagemLatex(sb);
    }

    public Image criarDerivadaImage(String expressao) throws IOException {
        String expressaoLatex = converterParaLatex(expressao);
        StringBuilder sb = new StringBuilder("\\begin{aligned}");
        sb.append("x^{\\prime} &= ");
        sb.append(expressaoLatex);
        sb.append("\\end{aligned}");

        return gerarImagemLatex(sb);
    }

    public Image criarDerivadaImplicitaImage(String expressao) throws IOException {
        String expressaoLatex = converterParaLatex(expressao);
        StringBuilder sb = new StringBuilder("\\frac{dy}{dx} = ");
        sb.append(expressaoLatex);

        return gerarImagemLatex(sb);
    }

    public Image criarIntegralImage(String input, String expressao) throws IOException {
        String expressaoLatex = converterParaLatex(expressao);
        StringBuilder sb = new StringBuilder("\\begin{aligned}");
        sb.append("\\int_{}^{} ").append(input).append("\\,dx &= ");
        sb.append(expressaoLatex);
        sb.append("+ C");
        sb.append("\\end{aligned}");

        return gerarImagemLatex(sb);
    }

    public Image criarIntegralImage(String input, String expressao, String limSup, String limInf) throws IOException {
        String expressaoLatex = converterParaLatex(expressao);
        StringBuilder sb = new StringBuilder("\\begin{aligned}");
        sb.append("\\int_{").append(limInf).append("}^{").append(limSup).append(" }").append(input).append("\\,dx &= ");
        sb.append(expressaoLatex);
        sb.append("\\end{aligned}");

        return gerarImagemLatex(sb);
    }

    private static @NotNull Image gerarImagemLatex(StringBuilder sb) throws IOException {
        TeXFormula form = new TeXFormula(sb.toString());
        TeXIcon icon = form.createTeXIcon(TeXFormula.SERIF, 40);
        icon.setForeground(Color.WHITE);
        BufferedImage image = new BufferedImage(
                icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = image.createGraphics();
        g2.setColor(new Color(255, 255, 255, 0));
        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        icon.paintIcon(null, g2, 0, 0);
        g2.dispose();

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", bos);
            bos.flush();
            try (InputStream is = new ByteArrayInputStream(bos.toByteArray())) {
                return new Image(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String converterParaLatex(String expressao) {
        return String.valueOf(evaluator.eval("TeXForm(" + expressao + ")"));
    }
}
