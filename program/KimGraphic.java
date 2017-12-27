package program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import source.BrowseFile;
import source.FileData;
import source.SearchMatcher;
import source.SitemapGenerator;

public class KimGraphic extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(final Stage stage) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		ImageView logo = null;
		stage.setTitle("Kim Joongle");
		final FlowPane root = new FlowPane();
		final Scene scene = new Scene(root, 1200, 600);

		try {
			logo = new ImageView(new Image(new FileInputStream("kim.png"), 300, 150, true, true));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		final TextArea textArea = new TextArea();
		final Label searchArea = new Label();

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(textArea);
		scrollPane.setFitToWidth(true);
		scrollPane.setMinWidth(1180);
		scrollPane.setMinHeight(300);

		// Create the text fields
		final TextField researchField = new TextField();
		researchField.setMinWidth(500);

		// Create the button
		Button searchButton = new Button("Search");
		searchButton.setPrefSize(150, 50);
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				SearchMatcher matcher = new SearchMatcher();
				try {

					matcher.splitSearchWords(researchField.getText());
					matcher.matchKeywords("keywords.xml");
					ArrayList<String> matches = matcher.getMatches();
					ArrayList<FileData> results = new ArrayList<FileData>();
					for (String match : matches) {
						results.add(new FileData(new File(match)));
					}
					searchArea.setText("\t\t\t\t\t" + researchField.getText().toUpperCase() + " \n");
					for (FileData result : results) {
						textArea.appendText("-----------******----------\n" + result + "\n\n");
					}

				} catch (ClassNotFoundException e) {
					System.err.println(e.getMessage());
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}

			}
		});
		// Clear
		final Button buttonClear = new Button("Clear");
		buttonClear.setPrefSize(100, 50);
		buttonClear.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				textArea.clear();
			}
		});

		// Create MenuBar
		MenuBar menuBar = new MenuBar();
		menuBar.setMinWidth(2000);

		// Create menus
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");

		// Create MenuItems
		MenuItem nightMode = new MenuItem("Mode Night");
		MenuItem exitItem = new MenuItem("Exit");
		MenuItem selectDirectory = new MenuItem("Select Directory");
		MenuItem xmlOpen = new MenuItem("Open Xml Files");
		MenuItem readMe = new MenuItem("Read ME");

		// Set Accelerator for Exit MenuItem.
		exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
		xmlOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		readMe.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
		nightMode.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
		selectDirectory.setAccelerator(KeyCombination.keyCombination("Ctrl+K"));

		selectDirectory.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				File dir = directoryChooser.showDialog(stage);
				SitemapGenerator sitemap = new SitemapGenerator("http://northkorea.nk/");
				BrowseFile fileBrowser = new BrowseFile();
				if (dir != null) {
					try {
						sitemap.execute(dir.getAbsolutePath());
						fileBrowser.execute(dir.getAbsolutePath());
					} catch (IOException e) {
						textArea.setText("Data Corrupted by Capitalism!");
					}
				}

			}

		});

		nightMode.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				final URL sceneCSSURL = getClass().getResource("darkTheme.css");
				stage.setScene(scene);
				scene.getStylesheets().add(sceneCSSURL.toExternalForm());

			}
		});

		// When user click on the Exit item.
		exitItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		xmlOpen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);

				// Show save file dialog
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					textArea.setText(readFile(file));
				}

			}
		});

		readMe.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File file = new File("README.md");

				if (file != null) {
					textArea.setText(readFile(file));
				}

			}
		});

		// Add menuItems to the Menus
		fileMenu.getItems().addAll(nightMode, exitItem);
		editMenu.getItems().addAll(xmlOpen, selectDirectory);
		helpMenu.getItems().add(readMe);

		// Add Menus to the MenuBar
		menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
		root.setPadding(new Insets(0, 10, 20, 10));
		root.setVgap(20);
		root.setHgap(20);
		root.getChildren().addAll(menuBar);
		root.getChildren().addAll(logo, researchField, searchButton, buttonClear);
		root.getChildren().add(searchArea);
		root.getChildren().add(scrollPane);
		final URL sceneCSSURL = getClass().getResource("CssScene.css");
		stage.setScene(scene);
		scene.getStylesheets().add(sceneCSSURL.toExternalForm());

		stage.show();
	}

	private String readFile(File file) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		String text;

		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stringBuffer.toString();
	}

}
