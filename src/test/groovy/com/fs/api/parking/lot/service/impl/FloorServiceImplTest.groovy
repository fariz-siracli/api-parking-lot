package com.fs.api.parking.lot.service.impl

import com.fs.api.parking.lot.dao.FloorRepository
import com.fs.api.parking.lot.dao.model.Floor
import com.fs.api.parking.lot.exception.DPException
import com.fs.api.parking.lot.service.FloorService
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.fs.api.parking.lot.model.enums.FloorState.AVAILABLE

class FloorServiceImplTest extends Specification {

    private FloorRepository floorRepository = Mock()
    private FloorService floorService = new FloorServiceImpl(floorRepository)

    def "FindAvailableByHeightAndWeightFloors no floor"() {
        given:
        def height = 3
        def weight = 2
        def floor = Floor.builder().id(1L).build()

        when:
        floorService.findAvailableByHeightAndWeightFloors(height, weight, floor)

        then:
        1 * floorRepository.findByStateAndHeightLessThan(AVAILABLE.name(), height, floor.getId()) >> List.of()
        def exc = thrown(DPException)
        exc.errorCode == "exception.parking-lot.no-floor-available"
    }

    def "FindAvailableByHeightAndWeightFloors"() {
        given:
        def height = 3
        def weight = 2
        def floor = Floor.builder().id(1L).build()
        def availableFloors = EnhancedRandom.randomListOf(3, Floor)

        availableFloors.get(0).slotSet.forEach({ s -> s.currentVehicle.weight = 1 })
        availableFloors.get(0).weight = availableFloors.get(0).slotSet.size() + weight + 2
        availableFloors.get(1).weight = 1
        availableFloors.get(2).weight = 1

        when:
        def list = floorService.findAvailableByHeightAndWeightFloors(height, weight, floor)

        then:
        1 * floorRepository.findByStateAndHeightLessThan(AVAILABLE.name(), height, floor.getId()) >> availableFloors
        list.size() == 1
    }
}
