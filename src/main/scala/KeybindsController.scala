import java.util.logging.{Level, Logger}
import javafx.scene.image.ImageView
import javafx.scene.paint.Paint
import javafx.scene.shape.SVGPath

import org.jnativehook.keyboard.{NativeKeyEvent, NativeKeyListener}
import org.jnativehook.{GlobalScreen, NativeHookException}
import plugin.QuakePlugin

import scalafx.scene.Group
import scalafx.scene.control.Menu
import scalafx.scene.input.MouseEvent
import scalafxml.core.macros.sfxml


@sfxml
class KeybindsController(keySVG: Group, keyIMG: Group, weaponIcons: Group, pluginMenu: Menu) extends NativeKeyListener{

  addGlobalKeyListener()

  val keymap = getMapOfKeyLayout

  val keyImageMap = getMapOfKeyImages
  val plugin = new QuakePlugin(pluginMenu, keyImageMap)

  def getMapOfKeyImages = {
    keyIMG.children.toArray.map(e => (e.asInstanceOf[ImageView].getId, e.asInstanceOf[ImageView]))
    .toMap[String,ImageView]
  }

  def getMapOfKeyLayout = {
    keySVG.children.toArray.map(e =>
      (e.asInstanceOf[SVGPath].getId, (e.asInstanceOf[SVGPath],e.asInstanceOf[SVGPath].getFill)))
      .toMap[String,(SVGPath,Paint)]
  }

  def addGlobalKeyListener() = {
    try{
      GlobalScreen.registerNativeHook()
    }catch{
      case e: NativeHookException =>
        System.err.println("There was a problem registering the native hook.")
        System.err.println(e.getMessage)

        System.exit(1)
    }
    GlobalScreen.addNativeKeyListener(this)
    setGlobalKeyListenerLoggingLevel()
  }

  def setGlobalKeyListenerLoggingLevel() = {
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
