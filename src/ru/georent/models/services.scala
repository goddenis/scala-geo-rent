package ru.georent.models

import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.hibernate3.HibernateOperations

@Repository
class SpotRepository {
  @Autowired var hibernate: HibernateOperations = _

  def getAllSpots() = hibernate.loadAll(classOf[Spot])

  def persist(spot: Spot): Spot = {
    hibernate.saveOrUpdate(spot)
    return spot
  }
}
