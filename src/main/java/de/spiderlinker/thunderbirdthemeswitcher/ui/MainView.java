package de.spiderlinker.thunderbirdthemeswitcher.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import de.spiderlinker.thunderbirdthemeswitcher.core.ThemeInstaller;
import de.spiderlinker.thunderbirdthemeswitcher.core.ThemeSearcher;
import de.spiderlinker.thunderbirdthemeswitcher.core.ThemeSwitcher;
import de.spiderlinker.thunderbirdthemeswitcher.core.ThemeUninstaller;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainView {

  @FXML
  private JFXButton btnInstallTheme;
  @FXML
  private JFXButton btnUninstallTheme;
  @FXML
  private JFXButton btnSwitchTheme;

  @FXML
  private JFXProgressBar progressInstall;
  @FXML
  private JFXProgressBar progressUninstall;

  @FXML
  private ImageView imgPreview;
  @FXML
  private JFXComboBox<String> boxChooseTheme;

  private BorderPane root;

  private ObservableMap<String, String> themesAndTheirImages = FXCollections.observableHashMap();

  private BooleanProperty installing = new SimpleBooleanProperty(false);
  private BooleanProperty uninstalling = new SimpleBooleanProperty(false);

  public MainView() {
    loadFxmlFile();
    styleProgressBars();

    setupComboBoxBinding();
    setupProgressBarBindings();
    setupImageBinding();

    updateThemeImages();
  }

  private void loadFxmlFile() {
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainView.fxml"));
    loader.setController(this);
    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void styleProgressBars() {
    progressInstall.getStyleClass().add("install-progress-bar");
    progressUninstall.getStyleClass().add("uninstall-progress-bar");
  }

  private void setupComboBoxBinding() {
    boxChooseTheme.promptTextProperty().bind(Bindings.when(Bindings.isEmpty(themesAndTheirImages)).then("No Themes installed!").otherwise("Select a theme..."));
    boxChooseTheme.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(themesAndTheirImages.keySet()), themesAndTheirImages));
  }

  private void setupProgressBarBindings() {
    progressInstall.visibleProperty().bind(installing);
    progressUninstall.visibleProperty().bind(uninstalling);
  }

  private void setupImageBinding() {
    boxChooseTheme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> imgPreview.setImage(newValue == null ? null : getImageOfSelectedTheme()));
  }

  private Image getImageOfSelectedTheme() {
    String selected = getSelectedImage();
    return selected == null ? null : new Image(new File(selected).toURI().toString());
  }

  @FXML
  private void onInstallTheme(ActionEvent e) {
    new Thread(() -> {
      installing.setValue(true);
      try {
        System.out.println("Installing theme...");
        ThemeInstaller.installTheme();
        System.out.println("Theme installed!");
        updateThemeImages();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      installing.setValue(false);
    }).start();
  }

  @FXML
  private void onUninstallTheme(ActionEvent e) {
    new Thread(() -> {
      uninstalling.setValue(true);
      ThemeUninstaller.uninstallTheme();
      updateThemeImages();
      uninstalling.setValue(false);
    }).start();
  }

  @FXML
  private void onSwitchTheme(ActionEvent e) {
    String theme = boxChooseTheme.getSelectionModel().getSelectedItem();

    if (theme == null || theme.trim().isEmpty()) {
      return;
    }

    ThemeSwitcher.switchThemeTo(theme);
  }

  @FXML
  private void onImageClicked(MouseEvent e) {
    String selectedImage = getSelectedImage();
    if (selectedImage != null) {
      try {
        Desktop.getDesktop().open(new File(selectedImage));
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  private String getSelectedImage() {
    return themesAndTheirImages.get(boxChooseTheme.getSelectionModel().getSelectedItem());
  }

  private void updateThemeImages() {
    System.out.println("Updating available themes...");
    Platform.runLater(() ->
    {
      themesAndTheirImages.clear();
      themesAndTheirImages.putAll(ThemeSearcher.getThemesAndImages());
      boxChooseTheme.requestFocus();
      System.out.println("Found available themes: " + themesAndTheirImages);
    });
  }

  public BorderPane getRoot() {
    return root;
  }
}
