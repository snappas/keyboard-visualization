/**
 * Created by Ryan on Jul 30.
 */
object RegexTest{
  def main(args: Array[String]) {
    val bindRegex = """.*(weapon 5)\b.*""".r
    """weapon 5;cg_drawcrosshair 7;cg_crosshairsize 33;cg_playerlean 1""" match {
      case bindRegex(action) => println(action)
      case default => println("")
    }
  }

}
