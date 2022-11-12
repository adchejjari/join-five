package com.example.morpionsolitaire.views;

import com.example.morpionsolitaire.controllers.View;
import com.example.morpionsolitaire.controllers.ViewSwitcher;
import com.example.morpionsolitaire.models.Grid;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class MainView {

    final private static String PRESSED_BUTTON_STYLE = "pressed-button";
    private boolean menuDisplayed = true;
    private Button toggledButton;
    @FXML
    private VBox navigationBar;
    @FXML
    private Button menuAnimationButton;
    @FXML
    private Image downArrowImage;
    @FXML
    private Image upArrowImage;
    @FXML
    private StackPane mainFrame;
    @FXML
    private Button newGameButton;
    @FXML
    private Button rankingButton;
    @FXML
    private Button settingButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button homeButton;

    MainViewListener mainViewListener;

    @FXML
    private void initialize() {
        this.toggledButton = homeButton;
        replacePressedButton(homeButton);
    }

    public void handleNavbar() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(1));
        translateTransition.setNode(this.navigationBar);
        if (menuDisplayed) {
            translateTransition.setToY(-50);
            translateTransition.play();
            translateTransition.setOnFinished(event -> {
                this.menuAnimationButton.setGraphic(new ImageView(this.downArrowImage));
                this.menuDisplayed = false;
            });
        }else{
            translateTransition.setToY(0);
            translateTransition.play();
            translateTransition.setOnFinished(event -> {
                this.menuAnimationButton.setGraphic(new ImageView(this.upArrowImage));
                this.menuDisplayed = true;
            });
        }
    }

    private void playFadeAnimation(Node node){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1500));
        fade.setFromValue(0);
        fade.setToValue(0.5);
        fade.setAutoReverse(true);
        fade.setNode(node);
        fade.play();
    }

    public void setMainViewListener(MainViewListener mvl){
        this.mainViewListener = mvl;
    }

    public void clear(){
        mainFrame.getChildren().clear();
    }

    public void add(Node node){
        mainFrame.getChildren().add(node);
    }

    @FXML
    private void onNewGameButtonClick() throws IOException {
        this.mainViewListener.setNewGamePage();
        replacePressedButton(newGameButton);
    }
    @FXML
    private void onHelpButtonClick() throws IOException {
        this.mainViewListener.setHelpPage();
        replacePressedButton(helpButton);

    }

    @FXML
    private void onSettingButtonClick() {
        mainFrame.getChildren().clear();
        replacePressedButton(settingButton);
    }

    @FXML
    private void onRankingButtonClick() throws IOException {
        this.mainViewListener.setRankingPage();
        replacePressedButton(rankingButton);
    }

    @FXML
    private void onHomeButtonClick() throws IOException {
        this.mainViewListener.setHomePage();
        replacePressedButton(homeButton);
    }

    private void replacePressedButton(Button button){
        toggledButton.getStyleClass().remove(PRESSED_BUTTON_STYLE);
        button.getStyleClass().add(PRESSED_BUTTON_STYLE);
        toggledButton = button;
    }



    public interface MainViewListener{
        void setHomePage() throws IOException;
        void setNewGamePage() throws IOException;
        void setSettingsPage();
        void setRankingPage() throws IOException;
        void setHelpPage() throws IOException;
    }
}