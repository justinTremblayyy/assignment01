package justintremblay.ass1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    
    
        private final String[] texts = {
        "Try typing this text. Do it as quickly and accurately as you can.",
        "Next type another line of input data.",
        "The quick brown fox jumps over the lazy dog.",
        "Five big quacking zephyrs jolt my wax bed.",
        "Sympathizing would fix Quaker objectives.",
        "A large fawn jumped quickly over white zinc boxes."
    };

    private int textNum = 0;

    private Label lblText;
    private Label progress;
    private TextField txtfTyped;
    private Label lblKey;
    private Label correctLabel;
    private Label lblWrong;
    private Button btnNext;
    private VBox root;
    

    @Override
    public void start(Stage primaryStage) {
        
        lblText = new Label();
        lblText.setWrapText(true);
        lblText.setStyle("-fx-font-size: 18px;");
        
        progress = new Label();
        progress.setStyle("-fx-font-size: 14px;");
        
        txtfTyped = new TextField();
        txtfTyped.setPrefWidth(500);
        txtfTyped.setDisable(true);
        
        lblKey = new Label("-");
        correctLabel = new Label("0");
        lblWrong = new Label("0");
        HBox stats = new HBox(20,
                new Label("Last key:"), lblKey,
                new Label("Correct:"), correctLabel,
                new Label("Incorrect:"), lblWrong);
        stats.setAlignment(Pos.CENTER_LEFT);
        
        btnNext = new Button("Next");
        Button btnReset = new Button("Reset");
        HBox buttons = new HBox(10, btnNext, btnReset);

        root = new VBox(12, progress, lblText, txtfTyped,
                stats, keyboard, buttons);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 720, 480);
        
        primaryStage.setTitle("Typing Tutor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
