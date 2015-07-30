
import java.util.logging.{Logger, LogManager, Level}

import org.jnativehook.{NativeHookException, GlobalScreen}

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{NoDependencyResolver, FXMLView}
import scalafx.Includes._

object driver extends JFXApp {
  stage = new PrimaryStage() {
    title = "Keybinds"
    scene =  new Scene(FXMLView(getClass.getResource("keyboard.fxml"),NoDependencyResolver))
  }
  stage.onCloseRequest  = handle{ System.exit(0) }



}
