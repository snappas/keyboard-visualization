package plugin

import scala.collection
import scala.collection.AbstractSeq
import scala.collection.immutable.{SortedMap, TreeMap}
import scala.collection.parallel.mutable
import scala.io.Source

object QuakeConfigBinds {

  def findBindKeysForAction(bindsMap: SortedMap[String,String], action: String): List[String] ={
      var listOfKeysForAction = collection.mutable.MutableList[String]()
      val bindRegex = (""".*("""+action+""")\b.*""").r
      for(key <- bindsMap.keys){
        val line = bindsMap(key)
        line match {
          case bindRegex(command) => listOfKeysForAction += key
          case default =>
        }
      }
      listOfKeysForAction.toList
  }



  def extractBind(cfgLine: String) = {
    val bindRegex = """(bind)\s([A-z|0-9]+[_]?)\s"(.*)"""".r
    cfgLine match {
      case bindRegex(_, key, command) => Some((key, command))
      case default => None
    }
  }



  def generateBindsMap(cfgLines: AbstractSeq[String]) = {
    var bindsMap: SortedMap[String,String] = TreeMap()
    val result = cfgLines.map(extractBind).filter(_.isDefined)
    result.foreach { element =>
      bindsMap += (element.get._1 -> element.get._2)
    }
    bindsMap
  }



}
