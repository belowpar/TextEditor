import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;   
import javafx.stage.Stage;

public class TextEditor extends Application
{
    public void start(Stage primaryStage) throws Exception
    {
        Parent parent = FXMLLoader.load(
            getClass().getResource("TextEditor.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setTitle("TextEditor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    } 
}
