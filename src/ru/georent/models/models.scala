package ru.georent.models

import scala.beans.BeanProperty
import javax.persistence._


@Embeddable
class Location(@BeanProperty var lat: Double, @BeanProperty var lon: Double) extends Serializable {

  override def toString() = lat + "," + lon

  def this() = this(0,0)
}

object Location {
  def apply(latitude: Double, longitude: Double) = new Location(latitude, longitude)
}

@Entity
case class Spot(@BeanProperty var name: String,
                @BeanProperty var location: Location,
                @BeanProperty var price: Double = 0,
                @BeanProperty var typ: String = "",
                @BeanProperty var description: String = "") {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = _

  def this() = this("", Location(0, 0))
}

object Spot {
  def apply(name: String, location: Location) = new Spot(name, location)

  def apply(name: String, long: Double, lat: Double) = new Spot(name, Location(lat, long))
}

