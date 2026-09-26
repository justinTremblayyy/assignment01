package justintremblay.ass1;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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

    private Map<KeyCode, Button> keys = new HashMap<>();
    private int textNum = 0;
    private int correctCount = 0;
    private int wrongCount = 0;

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
        btnNext.setOnAction(event -> next());
        Button btnReset = new Button("Reset");
        btnReset.setOnAction(event -> reset());

        
        HBox buttons = new HBox(10, btnNext, btnReset);
        
                GridPane keyboard = makeKeyboard();

        root = new VBox(12, progress, lblText, txtfTyped,
                stats, keyboard, buttons);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 720, 480);
        scene.setOnKeyPressed(event -> keyPressed(event));
        scene.setOnKeyReleased(event -> keyReleased(event));

        primaryStage.setTitle("Typing Tutor");
        primaryStage.setScene(scene);
        primaryStage.show();
        showText(0);
    }
    
    
    private GridPane makeKeyboard() {
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(4);
        grid.setAlignment(Pos.CENTER);

        String[] nums = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        KeyCode[] numCodes = {
            KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
            KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0
        };
        for (int i = 0; i < nums.length; i++) {
            grid.add(makeKey(nums[i], numCodes[i], 45), i, 0);
        }
        
        
        String[] top = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        KeyCode[] topCodes = {
            KeyCode.Q, KeyCode.W, KeyCode.E, KeyCode.R, KeyCode.T,
            KeyCode.Y, KeyCode.U, KeyCode.I, KeyCode.O, KeyCode.P
        };
        for (int i = 0; i < top.length; i++) {
            grid.add(makeKey(top[i], topCodes[i], 45), i, 1);
        }
        String[] middle = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        KeyCode[] midCodes = {KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.F, KeyCode.G,
            KeyCode.H, KeyCode.J, KeyCode.K, KeyCode.L};
        for (int col = 0; col < middle.length; col++) {
            grid.add(makeKey(middle[col], midCodes[col], 45), col, 2);
        }
        
        
        grid.add(makeKey("Shift", KeyCode.SHIFT, 70), 0, 3, 2, 1);
        String[] bottom = {"Z", "X", "C", "V", "B", "N", "M"};
        KeyCode[] bottomKeys = {
            KeyCode.Z, KeyCode.X, KeyCode.C, KeyCode.V, KeyCode.B, KeyCode.N, KeyCode.M
        };
        for (int i = 0; i < bottom.length; i++) {
            grid.add(makeKey(bottom[i], bottomKeys[i], 45), i + 2, 3);
        }
        Button backspace = makeKey("Backspace", KeyCode.BACK_SPACE, 90);
        grid.add(backspace, 9, 3);

        grid.add(makeKey(",", KeyCode.COMMA, 45), 0, 4);
        Button space = makeKey("Space", KeyCode.SPACE, 350);
        grid.add(space, 1, 4, 8, 1);
        grid.add(makeKey(".", KeyCode.PERIOD, 45), 9, 4);
        

        return grid;
    }
    
    
    /**
     * creates a key for the keyboard and puts it in the map
     * @param label the text on the key
     * @param code the real key it matches
     * @param width width of the key
     * @return the button
     */
    private Button makeKey(String label, KeyCode code, double width) {
        Button button = new Button(label);
        button.setPrefWidth(width);
        button.setPrefHeight(38);
        button.setOnAction(event -> root.requestFocus());
        keys.put(code, button);

        return button;
    }
    
    
    /**
     * runs when a key is pressed on the real keyboard
     * @param event the key event
     */
    private void keyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        Button key = keys.get(code);
        if (key != null) {
            key.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            
            lblKey.setText(code.getName());
            lblKey.setStyle("-fx-text-fill: black;");
            
            String typed = txtfTyped.getText();
            if (code == KeyCode.BACK_SPACE) {
                if (typed.length() > 0) {
                    txtfTyped.setText(typed.substring(0, typed.length() - 1));
                }
            } else {
                String text = event.getText();
                if (text != null && text.length() == 1) {
                    String target = texts[textNum];
                    if (typed.length() < target.length()) {
                        if (text.charAt(0) == target.charAt(typed.length())) {
                            correctCount++;
                        } else {
                            wrongCount++;
                        }
                        updateScore();
                    }
                    txtfTyped.setText(typed + text);
                }
            }
            
        } else {
            lblKey.setText("Not handled");
            lblKey.setStyle("-fx-text-fill: red;");
        }
    }
    
    private void keyReleased(KeyEvent event) {
        Button key = keys.get(event.getCode());
        if (key != null) {
            key.setStyle("");
        }
        root.requestFocus();

    }
    
    
    private void updateScore() {
        correctLabel.setText(String.valueOf(correctCount));
        lblWrong.setText(String.valueOf(wrongCount));
    }

    
    private void showText(int num) {
        textNum = num;
        lblText.setText(texts[textNum]);
        txtfTyped.setText("");
        progress.setText((textNum + 1) + " of " + texts.length);
        btnNext.setDisable(textNum == texts.length - 1);
        root.requestFocus();
    }

    private void next() {
        if (textNum < texts.length - 1) {
            showText(textNum + 1);
        }
    }

    private void reset() {
        correctCount = 0;
        wrongCount = 0;
        updateScore();
        lblKey.setText("-");
        lblKey.setStyle("-fx-text-fill: black;");
        showText(0);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
