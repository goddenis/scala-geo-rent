package ru.georent

import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.apache.log4j.xml.DOMConfigurator


object Launcher extends App {
  //System.setProperty("log4j.configuration","config/log4j.xml");


  val server = new Server()
  val connector = new SelectChannelConnector()

  connector.setPort(8080)



  server.addConnector(connector)

  val context = new WebAppContext("webapp", "/")

  if (System.getProperty("os.name").toLowerCase().contains("windows"))
    context.getInitParams.put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false")

  server.setHandler(context)

  server.start();

}

