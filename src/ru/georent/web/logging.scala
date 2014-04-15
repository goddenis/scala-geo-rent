package ru.georent.web

import javax.servlet._
import java.util.logging.LogManager.getLogManager
import org.slf4j.bridge.SLF4JBridgeHandler
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory.getLogger
import scala.actors.threadpool.AtomicInteger
import collection.JavaConversions._

class JavaUtilLoggerRedirector extends ServletContextListener {

  def contextInitialized(e: ServletContextEvent) {
    val rootLogger = getLogManager().getLogger("")

    rootLogger.getHandlers.foreach(h => rootLogger.removeHandler(h))

    SLF4JBridgeHandler.install()

  }

  def contextDestroyed(e: ServletContextEvent) {}


}

class RequestLogFilter extends Filter {
  private val logger = getLogger("requestLogger")
  private val nodeId = Integer.toHexString((Math.random() * 256).asInstanceOf[Int]) + "-"
  private val requestId = new AtomicInteger(0)


  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

    val http = request.asInstanceOf[HttpServletRequest]

    def serealizeParams(request: ServletRequest): String = {

      def normalize(sa: String): String = {
        sa.toString.replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t")
      }

      val m = request.getParameterMap.toMap

      if (m.isEmpty) "no params" else (for (me <- m) yield s"${me._1} = {${normalize(me._2.reduce(_ + "," + _))}} ").reduce(_ + "\n" + _)
    }


    try {
      Thread.currentThread().setName(nodeId + requestId.incrementAndGet())
      logger.info(request.getRemoteAddr + " " + http.getMethod + " " + http.getRequestURI + "\n" + serealizeParams(request))

    } catch {
      case e: Throwable => logger.error("", e)
    } finally {
      chain.doFilter(request, response)
    }
  }

  def init(filterConfig: FilterConfig) {
  }

  def destroy() {
  }
}


