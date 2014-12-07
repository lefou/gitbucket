package plugin

import scala.collection.mutable.ListBuffer
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import app.Context
import plugin.PluginSystem._
import plugin.PluginSystem.RepositoryMenu
import plugin.Security._
import service.RepositoryService.RepositoryInfo

// TODO This is a sample implementation for Scala based plug-ins.
class ScalaPlugin(val id: String, val version: String,
                  val author: String, val url: String, val description: String) extends Plugin {

  private val repositoryMenuList   = ListBuffer[RepositoryMenu]()
  private val globalMenuList       = ListBuffer[GlobalMenu]()
  private val repositoryActionList = ListBuffer[RepositoryAction]()
  private val globalActionList     = ListBuffer[Action]()
  private val javaScriptList       = ListBuffer[JavaScript]()

  def repositoryMenus       : List[RepositoryMenu]   = repositoryMenuList.toList
  def globalMenus           : List[GlobalMenu]       = globalMenuList.toList
  def repositoryActions     : List[RepositoryAction] = repositoryActionList.toList
  def globalActions         : List[Action]           = globalActionList.toList
  def javaScripts           : List[JavaScript]       = javaScriptList.toList

  def addRepositoryMenu(label: String, name: String, url: String, icon: String)(condition: (Context) => Boolean): Unit = {
    repositoryMenuList += RepositoryMenu(label, name, url, icon, condition)
  }

  def addGlobalMenu(label: String, url: String, icon: String)(condition: (Context) => Boolean): Unit = {
    globalMenuList += GlobalMenu(label, url, icon, condition)
  }

  def addGlobalAction(method: String, path: String, security: Security = All())(function: (HttpServletRequest, HttpServletResponse, Context) => Any): Unit = {
    globalActionList += Action(method, path, security, function)
  }

  def addRepositoryAction(method: String, path: String, security: Security = All())(function: (HttpServletRequest, HttpServletResponse, Context, RepositoryInfo) => Any): Unit = {
    repositoryActionList += RepositoryAction(method, path, security, function)
  }

  def addJavaScript(filter: String => Boolean, script: String): Unit = {
    javaScriptList += JavaScript(filter, script)
  }

}
