package de.spiderlinker.thunderbirdthemeswitcher;

import de.spiderlinker.thunderbirdthemeswitcher.ui.MainView;
import de.spiderlinker.thunderbirdthemeswitcher.utils.ThunderbirdUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ThunderbirdThemeSwitcher extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    System.out.println(ThunderbirdUtils.getProfileFolders());
    Scene scene = new Scene(new MainView().getRoot());
    scene.getStylesheets().add(getClass().getClassLoader().getResource("css/ProgressBarStyle.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }


}
