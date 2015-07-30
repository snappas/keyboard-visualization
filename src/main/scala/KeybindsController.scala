import java.io.InputStream
import java.util.logging.{Level, Logger}


import org.jnativehook.{NativeHookException, GlobalScreen}
import org.jnativehook.keyboard.{NativeKeyListener, NativeKeyEvent}
import plugin.QuakePlugin

import scala.io.Source
import scalafx.embed.swing.SwingFXUtils
import scalafx.event.ActionEvent
import scalafx.scene.control.Menu
import scalafx.scene.{Node, Group}
import javafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.scene.input.MouseEvent
import javafx.scene.paint.Paint
import javafx.scene.shape.SVGPath
import scalafxml.core.macros.sfxml


@sfxml
class KeybindsController(keySVG: Group, keyIMG: Group, weaponIcons: Group, pluginMenu: Menu) extends NativeKeyListener{

  addGlobalKeyListener

  val keymap = getMapOfKeyLayout

  val keyImageMap = getMapOfKeyImages


  def getMapOfKeyImages = {
    keyIMG.children.toArray.map(e => (e.asInstanceOf[ImageView].getId, e.asInstanceOf[ImageView]))
    .toMap[String,ImageView]
  }

  val plugin = new QuakePlugin(pluginMenu, keyImageMap)

  def getMapOfKeyLayout = {
    keySVG.children.toArray.map(e =>
      (e.asInstanceOf[SVGPath].getId, (e.asInstanceOf[SVGPath],e.asInstanceOf[SVGPath].getFill)))
      .toMap[String,(SVGPath,Paint)]
  }

  def addGlobalKeyListener = {
    try{
      GlobalScreen.registerNativeHook()
    }catch{
      case e: NativeHookException =>
        System.err.println("There was a problem registering the native hook.")
        System.err.println(e.getMessage())

        System.exit(1)
    }
    GlobalScreen.addNativeKeyListener(this)
    setGlobalKeyListenerLoggingLevel
  }

  def setGlobalKeyListenerLoggingLevel = {
    val handlers = Logger.getLogger("").getHandlers
    for (i <- handlers.indices) {
      handlers(i).setLevel(Level.WARNING)
    }
  }


  def handleKeyClicked(event: MouseEvent) = {
    println(event.getTarget.asInstanceOf[SVGPath].setFill(Paint.valueOf("GREEN")))
  }

  override def nativeKeyPressed(nativeKeyEvent: NativeKeyEvent): Unit = {
    System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode))
    keymap(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode))._1.setFill(Paint.valueOf("GREEN"))
  }

  override def nativeKeyReleased(nativeKeyEvent: NativeKeyEvent): Unit = {
    val key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode)
    keymap(key)._1.setFill(keymap(key)._2)
  }

  override def nativeKeyTyped(nativeKeyEvent: NativeKeyEvent): Unit = {
    System.out.println("Key Typed: " + nativeKeyEvent.getKeyChar)
  }


}
