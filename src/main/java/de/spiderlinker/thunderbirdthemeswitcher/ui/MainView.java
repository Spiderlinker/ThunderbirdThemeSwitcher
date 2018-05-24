package de.spiderlinker.thunderbirdthemeswitcher.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSnackbar;
import de.spiderlinker.thunderbirdthemeswitcher.Config;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainView {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainView.class);

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
  private ImageView           imgPreview;
  @FXML
  private JFXComboBox<String> boxChooseTheme;

  @FXML
  private JFXSnackbar snackbarInfo;

  private BorderPane root;

  private ObservableMap<String, String> themesAndTheirImages = FXCollections.observableHashMap();

  private BooleanProperty installing   = new SimpleBooleanProperty(false);
  private BooleanProperty uninstalling = new SimpleBooleanProperty(false);

  public MainView() {
    loadFxmlFile();
    styleProgressBars();

    completeSnackbarWidget();
    setupWidgetBindings();

    updateAvailableThemeImagesToDisplay();
  }

  private void loadFxmlFile() {
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainView.fxml"));
    loader.setController(this);
    try {
      root = loader.load();
    } catch (IOException e) {
      LOGGER.error("Could not load fxml!", e);
    }
  }

  private void styleProgressBars() {
    progressInstall.getStyleClass().add("install-progress-bar");
    progressUninstall.getStyleClass().add("uninstall-progress-bar");
  }

  private void completeSnackbarWidget() {
    snackbarInfo.registerSnackbarContainer(root);
  }

  private void setupWidgetBindings() {
    setupComboBoxBinding();
    setupProgressBarBindings();
    setupImageBinding();
    setupButtonBindings();
  }

  private void setupComboBoxBinding() {
    boxChooseTheme
        .promptTextProperty()
        .bind(Bindings.when(
            Bindings.isEmpty(themesAndTheirImages))
            .then("No Themes installed!")
            .otherwise("Select a theme..."));
    boxChooseTheme
        .itemsProperty()
        .bind(Bindings.createObjectBinding(
            () -> FXCollections.observableArrayList(themesAndTheirImages.keySet()), themesAndTheirImages));
  }

  private void setupProgressBarBindings() {
    progressInstall.visibleProperty().bind(installing);
    progressUninstall.visibleProperty().bind(uninstalling);
  }

  private void setupImageBinding() {
    imgPreview.mouseTransparentProperty().bind(imgPreview.imageProperty().isNull());
    imgPreview.setImage(null);
    boxChooseTheme.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> imgPreview.setImage(newValue == null ? null : getImageOfSelectedTheme()));

  }

  private void setupButtonBindings() {
    btnInstallTheme.disableProperty().bind(installing.or(uninstalling));
    btnUninstallTheme.disableProperty().bind(uninstalling.or(installing));
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
        LOGGER.info("Installing theme...");
        ThemeInstaller.installTheme();
        showMessage("Theme successfully installed!");
        updateAvailableThemeImagesToDisplay();
      } catch (IOException exc) {
        LOGGER.error("Could not install themes!", exc);
      }
      installing.setValue(false);
    }).start();
  }

  @FXML
  private void onUninstallTheme(ActionEvent e) {
    new Thread(() -> {
      uninstalling.setValue(true);
      ThemeUninstaller.uninstallTheme();
      updateAvailableThemeImagesToDisplay();
      showMessage("Theme successfully uninstalled!");
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
    showMessage("Theme switched to " + theme);
  }

  @FXML
  private void onImageClicked(MouseEvent e) {
    String selectedImage = getSelectedImage();
    if (selectedImage != null) {
      try {
        Desktop.getDesktop().open(new File(selectedImage));
      } catch (IOException exc) {
        LOGGER.error("Could not open image in os viewer!", exc);
      }
    }
  }

  @FXML
  private void onVisitThemeSite(ActionEvent e) {
    try {
      Desktop.getDesktop().browse(new URI(Config.URL_THEME));
    } catch (IOException | URISyntaxException exc) {
      LOGGER.error("Could not open URL: " + Config.URL_THEME, exc);
    }
  }

  private String getSelectedImage() {
    return themesAndTheirImages.get(boxChooseTheme.getSelectionModel().getSelectedItem());
  }

  private void updateAvailableThemeImagesToDisplay() {
    LOGGER.info("Updating available themes...");
    Platform.runLater(() ->
    {
      themesAndTheirImages.clear();
      themesAndTheirImages.putAll(ThemeSearcher.getThemesAndImages());
      boxChooseTheme.requestFocus();
      LOGGER.info("Found available themes: {}", themesAndTheirImages.keySet());
    });
  }

  private void showMessage(String message) {
    LOGGER.info(message);
    Platform.runLater(() -> snackbarInfo.show(message, 2000));
  }

  public BorderPane getRoot() {
    return root;
  }
}
