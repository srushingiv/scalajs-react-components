package chandu0101.macros.tojs

import scala.annotation.StaticAnnotation
import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  * @author steven
  *
  */
class svgIcon(iconName: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro SvgIconMacro.svgIcon_impl
}

object SvgIconMacro {
  def svgIcon_impl(c: whitebox.Context)(annottees: c.Expr[Any]*) = {
    import c.universe._

    val iconName = c.macroApplication match {
      case Apply(Select(Apply(_, List(Literal(Constant(s: String)))), _), _) => TermName(s)
      case _ => c.abort(c.enclosingPosition, "You must provide an iconName")
    }

    annottees.map(_.tree) match {
      case List(q"case class $name() { ..$body }") if body.isEmpty =>
        c.Expr[Any](
          q"""
             import scala.scalajs.js
             import japgolly.scalajs.react._
             import chandu0101.scalajs.react.components.materialui.Mui

               case class $name(key: js.UndefOr[String] = js.undefined,
                                ref: js.UndefOr[String] = js.undefined,
                                color: js.UndefOr[String] = js.undefined,
                                hoverColor: js.UndefOr[String] = js.undefined,
                                onMouseEnter: js.UndefOr[ReactMouseEventH ⇒ Callback] = js.undefined,
                                onMouseLeave: js.UndefOr[ReactMouseEventH ⇒ Callback] = js.undefined,
                                style: js.UndefOr[js.Any] = js.undefined,
                                viewBox: js.UndefOr[String] = js.undefined) {
                 def apply() = {
                   import chandu0101.macros.tojs.JSMacro
                   val props = JSMacro[$name](this)
                   val f = React.asInstanceOf[js.Dynamic].createFactory(Mui.SvgIcons.$iconName)
                   f(props).asInstanceOf[ReactComponentU_]
                 }
               }
           """
        )

      case _ => c.abort(c.enclosingPosition, "You must annotate an empty case class definition with an empty body.")
    }
  }
}
