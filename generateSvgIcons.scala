import java.io.{PrintWriter, File}

def header = """package chandu0101.scalajs.react.components.materialui
               |
               |import chandu0101.macros.tojs.svgIcon
               |
               |object MuiSvgIcons {
               |""".stripMargin

val pw = new PrintWriter(new File("core/src/main/scala/chandu0101/scalajs/react/components/materialui/MuiSvgIcons.scala"), "UTF-8")

pw.write(header)

val svgIdx = io.Source.fromFile(new File("demo/node_modules/material-ui/lib/svg-icons/index.js"))

val iconRe = """\s+(.*): require.*""".r

svgIdx getLines() foreach {
  case iconRe(iconName) => pw.write(s"""  @svgIcon("$iconName") case class $iconName()\n""")
  case _ =>
}

pw.write("}\n")

pw.close()
