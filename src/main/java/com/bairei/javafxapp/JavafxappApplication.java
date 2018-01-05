package com.bairei.javafxapp;

import com.bairei.javafxapp.controllers.IntroController;
import com.bairei.javafxapp.controllers.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Getter
@Component
@SpringBootApplication
public class JavafxappApplication extends Application {

	private ConfigurableApplicationContext context;
	private Parent root;
	private BorderPane pane;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Hello");
		pane = (BorderPane) root;
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/introView.fxml"));
		AnchorPane sampleView = (AnchorPane) loader.load();
		IntroController controller = loader.getController();
		controller.setMain(this);
		pane.setCenter(sampleView);
	}

	@Override
	public void init() throws Exception {
		context = SpringApplication.run(JavafxappApplication.class);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/root.fxml"));
		loader.setControllerFactory(context::getBean);
		root = loader.load();
		MenuController controller = loader.getController();
		controller.setMain(this);
	}

	public static void main(String[] args) {
		launch(JavafxappApplication.class, args);
	}


}
