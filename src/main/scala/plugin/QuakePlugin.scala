package plugin

import java.io.File
import javafx.scene.image.ImageView

import scala.io.Source
import scalafx.scene.control.{MenuItem, Menu}
import scalafx.scene.image.Image
import scalafx.Includes._
import scalafx.stage.FileChooser

class QuakePlugin(pluginMenu: Menu, keyImageMap: Map[String, ImageView]) extends KeyboardPlugin {
  val iconAndAction = List(
    ("/weapons/gauntlet.png", "weapon 1"),
    ("/weapons/machinegun.png", "weapon 2"),
    ("/weapons/shotgun.png", "weapon 3"),
    ("/weapons/grenade.png", "weapon 4"),
    ("/weapons/rocket.png", "weapon 5"),
    ("/weapons/lightning.png", "weapon 6"),
    ("/weapons/railgun.png", "weapon 7"),
    ("/weapons/plasma.png", "weapon 8"),
    ("/weapons/bfg.png", "weapon 9"),
    ("/weapons/grapple.png", "weapon 10"),
    ("/weapons/nailgun.png", "weapon 11"),
    ("/weapons/proxmine.png", "weapon 12"),
    ("/weapons/chaingun.png", "weapon 13"),
    ("/movement/left.png", "\\+moveleft"),
    ("/movement/right.png", "\\+moveright"),
    ("/movement/forward.png", "\\+forward"),
    ("/movement/back.png", "\\+back"),
    ("/movement/jump.png", "\\+moveup"),
    ("/movement/crouch.png", "\\+movedown"),
    ("/misc/zoom.png", "\\+zoom"),
    ("/misc/balloon.png", "say")
  )
  var configFile: File = _
  pluginMenu.items = List(new MenuItem("Load config"){
    onAction = handle {
      val fileChooser = new FileChooser
      configFile = fileChooser.showOpenDialog(null)
      if(configFile != null){
        getBinds(configFile)
      }
    }
  })


  def getBinds(configFile: File) = {
    for(e <- getKeys(configFile)){
      e._2.foreach(x => keyImageMap(x.toUpperCase).setImage(e._1))
    }
  }

  def getKeys(configFile: File): List[(Image,List[String])] = {
    val cfgLines = Source.fromFile(configFile).getLines.toList
    val bindsFromCfg = QuakeConfigBinds.generateBindsMap(cfgLines)

    iconAndAction.map(e =>
      (new Image(getClass.getResourceAsStream(e._1)),
        QuakeConfigBinds.findBindKeysForAction(bindsFromCfg, e._2)))
  }

  override def iconListForKey = {

  }

}
