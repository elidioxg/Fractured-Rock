package fractureanalysis.stages;

import fractureanalysis.FractureAnalysis;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OpenDataStage {

    public TextField tfFilename;

    public Stage createWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(FractureAnalysis.getInstance().getClass()
                .getResource("views/openFileFXML.fxml"));
        Stage stage = new Stage();
        GridPane parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Open Dataset");
        stage.setScene(scene);
        stage.showAndWait();
        return stage;
    }

    public void setFilename(String fn) {
        this.tfFilename.setText(fn);
    }

}
