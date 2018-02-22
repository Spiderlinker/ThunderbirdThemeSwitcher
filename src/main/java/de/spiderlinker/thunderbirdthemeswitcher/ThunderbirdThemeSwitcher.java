package de.spiderlinker.thunderbirdthemeswitcher;

import de.spiderlinker.thunderbirdthemeswitcher.ui.MainView;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThunderbirdThemeSwitcher extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(ThunderbirdThemeSwitcher.class);

  private Stage stage;
  private Scene scene;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    LOGGER.info("Found profiles on startup: {}", ThunderbirdUtils.getProfileFolders());
    this.stage = primaryStage;

    createSceneForStage();
    setupSceneStylesheet();
    setupStageTitle();
    showApplication();
  }

  private void createSceneForStage() {
    this.scene = new Scene(new MainView().getRoot());
    stage.setScene(scene);
  }

  private void setupSceneStylesheet() {
    scene.getStylesheets().add(getClass().getClassLoader().getResource("css/ProgressBarStyle.css").toExternalForm());
  }

  private void setupStageTitle() {
    stage.setTitle("Thunderbird Theme Switcher - 1.0 - by Spiderlinker");
  }

  private void showApplication() {
    stage.show();
  }

}
