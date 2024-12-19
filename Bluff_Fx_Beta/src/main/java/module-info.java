module com.example.bluff_fx_beta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.bluff_fx_beta to javafx.fxml;
    exports com.example.bluff_fx_beta;
}