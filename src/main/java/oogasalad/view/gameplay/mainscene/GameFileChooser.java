package oogasalad.view.gameplay.mainscene;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.shared.widgetfactory.WidgetConfiguration;
import oogasalad.shared.widgetfactory.WidgetFactory;

//public class GameFileChooser {
//
//  private WidgetFactory factory;
//  private int width;
//  private int height;
//  private Image fileIcon;
//  private FlowPane flowPane;
//  private File[] files;
//
//
//  public GameFileChooser(int width, int height) {
//    this.factory = new WidgetFactory();
//    this.width = width;
//    this.height = height;
//
//    Path gamePath = Paths.get("/data");
//    File gameDirectory = gamePath.toFile();
//    if (gameDirectory.exists() && gameDirectory.isDirectory()) {
//      File[] files = gameDirectory.listFiles();
//      if (files != null) {
//        for (File file : files) {
//          System.out.println(file.getName());
//        }
//      }
//    } else {
//      System.out.println("Directory does not exist or is not a directory.");
//    }
//
//    InputStream stream = getClass().getResourceAsStream("/images/FileIcon.png");
//    fileIcon = new Image(stream);
//  }
//
//  /**
//   * Sets up the scrollpane used for selecting files.
//   * @return a scrollpane with populated image icons representing JSON level files.
//   */
//  private VBox setUpFileChooser() {
//    this.flowPane = factory.createFlowPane(new WidgetConfiguration(width - 50,
//        height / 4, "flowpane"));
//
//    //TODO: change to actual file #
//    populateFiles(10, flowPane);
//    ScrollPane pane = factory.makeScrollPane(flowPane, width - 50);
//    Text paneLabel = factory.generateLine(new WidgetConfiguration("Games"));
//    VBox labelAndChooser = factory.wrapInVBox(paneLabel, height / 3);
//    labelAndChooser.getChildren().add(pane);
//    return labelAndChooser;
//  }
//
//  /**
//   * Iterates through default game files and populates them within a flowpane. When the file icons
//   * representing each game file are clicked, an individual popup dialog window appears with their
//   * information.
//   *
//   * @param numFiles number of default game level JSON files
//   * @param flowPane javafx object containing the clickable file icons
//   */
//  private void populateFiles(int numFiles, FlowPane flowPane) {
//    for (int i = 1; i <= numFiles; i++) {
//      ImageView imageView = new ImageView(fileIcon);
//      imageView.setFitWidth(80);
//      imageView.setFitHeight(80);
//
//      Text fileName = factory.generateCaption("File: " + i);
//      VBox imageAndLabel = new VBox(10);
//      imageAndLabel.getChildren().addAll(imageView, fileName);
//
//      // Make each file icon clickable: TODO: connect to json
//      imageView.setOnMouseClicked(event -> {
//        Text text = new Text("TEMPORARY: file info goes here");
//        VBox vbox = new VBox(text);
//        factory.createPopUpWindow(new WidgetConfiguration(width - 100,
//            height / 4, "FileInformation"), vbox);
//      });
//      flowPane.getChildren().add(imageAndLabel);
//    }
//  }
//
//}
