module movietrackerproject.movietracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens movietracker.core to javafx.fxml;
    exports movietracker.core;
    exports movietracker.core.data;
    opens movietracker.core.data to javafx.fxml;
    opens movietracker.core.part2 to javafx.fxml;
    exports movietracker.core.part2;
    exports movietracker.core.util;
    opens movietracker.core.util to javafx.fxml;
}