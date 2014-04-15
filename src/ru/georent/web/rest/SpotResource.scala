package ru.georent.web.rest

import javax.ws.rs._
import ru.georent.models.{SpotRepository, Spot}
import org.springframework.beans.factory.annotation.Autowired
import scala.collection.JavaConversions._
import java.util.{List => JavaList}
import javax.servlet.ServletContext
import org.springframework.web.context.support.{XmlWebApplicationContext, WebApplicationContextUtils}
import javax.ws.rs.core.Context

@Path("spots")
@Produces(Array("application/json"))
class SpotResource extends SpringAwareResource {

  @Autowired var repo: SpotRepository = null

  @GET
  def getAllSpots(): JavaList[Spot] = repo.getAllSpots().asInstanceOf[JavaList[Spot]]


  @POST
  def newSpot(@FormParam("name") name: String,
              @FormParam("description") description: String,
              @FormParam("lat") lat: Double,
              @FormParam("lon") lon: Double): Spot = repo.persist(Spot(name, lon, lat))


}

abstract class SpringAwareResource {

  def autoWire(sc: ServletContext): Unit = autoWire(sc, this)

  def autoWire(sc: ServletContext, target: Any): Unit = {
    var context = WebApplicationContextUtils.getWebApplicationContext(sc).asInstanceOf[XmlWebApplicationContext]
    context.getBeanFactory.autowireBean(target)
  }

  @Context
  def setContext(sc: ServletContext) = autoWire(sc)

}