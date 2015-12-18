import java.io.{PrintWriter, File}

def header = """package chandu0101.scalajs.react.components.materialui
               |
               |import japgolly.scalajs.react._
               |import scala.scalajs.js
               |
               |object MuiSvgIcons {
               |""".stripMargin

def generateIcon(name: String) =
  s"""  case class $name(key: js.UndefOr[String] = js.undefined,
     |                   ref: js.UndefOr[String] = js.undefined,
     |                   color: js.UndefOr[String] = js.undefined,
     |                   hoverColor: js.UndefOr[String] = js.undefined,
     |                   onMouseEnter: js.UndefOr[ReactMouseEventH ⇒ Callback] = js.undefined,
     |                   onMouseLeave: js.UndefOr[ReactMouseEventH ⇒ Callback] = js.undefined,
     |                   style: js.UndefOr[js.Any] = js.undefined,
     |                   viewBox: js.UndefOr[String] = js.undefined) {
     |    def apply() = {
     |      import chandu0101.macros.tojs.JSMacro
     |      val props = JSMacro[$name](this)
     |      val f = React.asInstanceOf[js.Dynamic].createFactory(Mui.SvgIcons.$name)
     |      f(props).asInstanceOf[ReactComponentU_]
     |    }
     |  }
     |
     |""".stripMargin

val pw = new PrintWriter(new File("core/src/main/scala/chandu0101/scalajs/react/components/materialui/MuiSvgIcons.scala"), "UTF-8")

pw.write(header)

val svgIdx = io.Source.fromFile(new File("demo/node_modules/material-ui/lib/svg-icons/index.js"))

val iconRe = """\s+(.*): require.*""".r

svgIdx getLines() foreach {
  case iconRe(iconName) => pw.write(generateIcon(iconName))
  case _ =>
}

pw.write("}\n")

pw.close()
