package javafx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class menuEvent extends Application {
	
	public static void main(String[] args) {
        Application.launch(args);
    }
	
    public void start(Stage stage) {
    	stage.setTitle("Kim Joongle");
        Group root = new Group();
         
         
        final TextArea textArea = TextAreaBuilder.create()
                .prefWidth(400)
                .wrapText(true)
                .build();
         
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(1200);
        scrollPane.setPrefHeight(800);
        

 
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
        
        
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        
        // Create MenuItems
        MenuItem newItem = new MenuItem("New");
        MenuItem exitItem = new MenuItem("Exit");
        
        MenuItem xmlOpen = new MenuItem("Open Xml Files");
        MenuItem readMe = new MenuItem("Read ME");
        
        // Set Accelerator for Exit MenuItem.
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
        xmlOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
 
        // When user click on the Exit item.
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        
        xmlOpen.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				 
				//Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);
				 
				//Show save file dialog
				File file = fileChooser.showOpenDialog(stage);
				if(file != null){
				    textArea.setText(readFile(file));
				}
            	
            }
        });
        
        readMe.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				 
				//Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MD FILES (README.md)", "README.md");
				fileChooser.getExtensionFilters().add(extFilter);
				 
				//Show save file dialog
				File file = fileChooser.showOpenDialog(stage);
				if(file != null){
				    textArea.setText(readFile(file));
				}
            	
            }
        });
        
        
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(newItem, exitItem);
        editMenu.getItems().addAll(xmlOpen);
        helpMenu.getItems().add(readMe);
        
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        
        BorderPane root1 = new BorderPane();
        root1.setTop(menuBar);
        @SuppressWarnings("deprecation")
		VBox vBox = VBoxBuilder.create()
                .children(menuBar, scrollPane)
                .build();
         
        root.getChildren().add(vBox);
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }
    
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
    
} 