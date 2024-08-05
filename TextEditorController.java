import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.text.FontWeight;
import javafx.event.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.application.Platform;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextEditorController {

    @FXML
    private ToggleGroup fontGroup;

    @FXML
    private AnchorPane anchor;

    @FXML
    private CheckMenuItem bold;

    @FXML
    private Menu fileMenu;

    @FXML
    private CheckMenuItem italic;

    @FXML
    private RadioMenuItem monospaced;

    @FXML
    private MenuItem newFileButton;

    @FXML
    private MenuItem openButton;

    @FXML
    private MenuItem quitButton;

    @FXML
    private RadioMenuItem sansserif;
    
    @FXML
    private MenuItem saveAsButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private SeparatorMenuItem seperator;

    @FXML
    private RadioMenuItem serif;

    @FXML
    private TextArea textArea;

    @FXML
    private Menu textMenu;

    @FXML
    private VBox vbox;

    // keep track of current font family
    private String currentFontString = null;  
    // Holds the current open file   
    private File currentFile = null;
    // holds the default font size
    private int fontSize = 12;
    // track if bolded or italics
    boolean isBold = false;
    boolean isItalic = false;

    public void initialize()
    {
        sansserif.setOnAction(new ButtonClickHandler());
        monospaced.setOnAction(new ButtonClickHandler());
        serif.setOnAction(new ButtonClickHandler());
        
        italic.setOnAction(event -> 
        {
            if (italic.isSelected())
            {
                isItalic = true;
                setUpFont();
            }
            else
            {
                isItalic = false;
                setUpFont();
            }
            
        });

        bold.setOnAction(event -> 
        {
            if (bold.isSelected())
            {
                isBold = true;
                setUpFont();
            }
            else
            {
                isBold = false;
                setUpFont();
            }
        });

        openButton.setOnAction(new OpenButtonHandler());

        newFileButton.setOnAction(new NewButtonHandler());

        saveAsButton.setOnAction(new SaveAsButtonHandler());

        saveButton.setOnAction(new SaveButtonHandler());

        quitButton.setOnAction(event ->
        {
            Platform.exit();
        });

        // hot keys for the menu items
        textArea.addEventHandler(KeyEvent.KEY_PRESSED, event -> 
        {
            if (event.getCode() == KeyCode.S && event.isControlDown()) 
            {
                if (currentFile == null)    // check for open file
                {
                    FileChooser fileChooser = new FileChooser();
                    currentFile = 
                    fileChooser.showSaveDialog(anchor.getScene().getWindow());
                }
                else
                {
                    try
                    {
                        FileWriter myWriter = new FileWriter(currentFile);
                        myWriter.write(textArea.getText());
                        myWriter.close();
                    }
                    catch (IOException w)
                    {
                        System.out.println(w);
                    }
                }
                event.consume();
            }
            else if (event.getCode() == KeyCode.N && event.isControlDown())
            {
                currentFile = null;
                textArea.clear();
                event.consume();
            }
            else if (event.getCode() == KeyCode.O && event.isControlDown())
            {
                FileChooser fileChooser = new FileChooser();
                currentFile = 
                    fileChooser.showOpenDialog(anchor.getScene().getWindow());
                Scanner fileScanner = null;
                try
                {
                    fileScanner = new Scanner(currentFile);
                }
                catch (FileNotFoundException e)
                {
                    System.out.println(e);
                }
                textArea.clear();
                while (fileScanner.hasNextLine())
                {
                    textArea.appendText(fileScanner.nextLine());

                }
                
                fileScanner.close();
                event.consume();
            }
            else if (event.getCode() == KeyCode.A && event.isControlDown())
            {
                FileChooser fileChooser = new FileChooser();
                currentFile = 
                fileChooser.showSaveDialog(vbox.getScene().getWindow());
                event.consume();
            }
            else if (event.getCode() == KeyCode.X && event.isControlDown())
            {
                Platform.exit();
            }
        }); 
    }
    /**
     * Handles the font family buttons
     */
    public class ButtonClickHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            if (monospaced.isSelected())
            {
                currentFontString = "monospace";
                setUpFont();
            }
            else if (sansserif.isSelected())
            {
                currentFontString = "sans-serif";
                setUpFont();
            }
            else if (serif.isSelected())
            {
                currentFontString = "serif";
                setUpFont();
            }
        }
    }
    /**
     * Handles the save button
     */
    public class SaveButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            if (currentFile == null)
                {
                    FileChooser fileChooser = new FileChooser();
                    currentFile = 
                    fileChooser.showSaveDialog(anchor.getScene().getWindow());
                }
                else
                {
                    try
                    {
                    FileWriter myWriter = new FileWriter(currentFile);
                    myWriter.write(textArea.getText());
                    myWriter.close();
                    }
                    catch (IOException w)
                    {
                        System.out.println(w);
                    }
                }
        }
    }
    /**
     * Handles the new button
     */
    public class NewButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            currentFile = null;
            textArea.clear();
        }
    }
    /**
     * Handles the Save As button
     */
    public class SaveAsButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            FileChooser fileChooser = new FileChooser();
            currentFile = 
                fileChooser.showSaveDialog(vbox.getScene().getWindow());
        }
    }
    /**
     * Handles the open button
     */
    public class OpenButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            FileChooser fileChooser = new FileChooser();
            currentFile = 
                fileChooser.showOpenDialog(anchor.getScene().getWindow());
            Scanner fileScanner = null;
            try
            {
                fileScanner = new Scanner(currentFile);
            }
            catch (FileNotFoundException w)
            {
                System.out.println(w);
            }
            textArea.clear();
            while (fileScanner.hasNextLine())
            {
                textArea.appendText(fileScanner.nextLine());

            }
            
            fileScanner.close();
        }
    }
    /**
     * Sets up the font based on flags and the currentFontString
     */
    public void setUpFont()
    {
        if (isBold && isItalic)
        {
            textArea.setFont(Font.font(currentFontString, FontWeight.BOLD,
                FontPosture.ITALIC, fontSize));
        }
        else if (isBold)
        {
            textArea.setFont(Font.font(currentFontString, FontWeight.BOLD,
                 fontSize));
        }
        else if (isItalic)
        {
            textArea.setFont(Font.font(currentFontString, FontPosture.ITALIC, 
                                    fontSize));
        }
        else 
        {
            textArea.setFont(Font.font(currentFontString, fontSize));
        }
    }
}
