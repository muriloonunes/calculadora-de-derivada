module mpbhd.calculadoradederivada {
    requires javafx.controls;
    requires javafx.fxml;
    requires matheclipse.core;
    requires java.desktop;

    exports mpbhd.calculadoradederivada.view;
    opens mpbhd.calculadoradederivada.view to javafx.fxml;
    exports mpbhd.calculadoradederivada.controller;
    opens mpbhd.calculadoradederivada.controller to javafx.fxml;
}