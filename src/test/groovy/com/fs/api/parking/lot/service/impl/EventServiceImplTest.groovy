package com.fs.api.parking.lot.service.impl

import com.fs.api.parking.lot.TestUtils
import com.fs.api.parking.lot.dao.ParkingEventRepository
import com.fs.api.parking.lot.dao.model.Gate
import com.fs.api.parking.lot.dao.model.ParkingEvent
import com.fs.api.parking.lot.dao.model.Payment
import com.fs.api.parking.lot.dao.model.Slot
import com.fs.api.parking.lot.dao.model.Tariff
import com.fs.api.parking.lot.dao.model.Vehicle
import com.fs.api.parking.lot.model.ExitRequest
import com.fs.api.parking.lot.model.GateDto
import com.fs.api.parking.lot.model.VehicleDto
import com.fs.api.parking.lot.model.enums.PaymentStatus
import com.fs.api.parking.lot.service.EventService
import com.fs.api.parking.lot.service.GateService
import com.fs.api.parking.lot.service.PaymentService
import com.fs.api.parking.lot.service.SlotService
import com.fs.api.parking.lot.service.TariffService
import com.fs.api.parking.lot.service.VehicleService
import com.fs.api.parking.lot.util.EventHelper
import spock.lang.Specification

class EventServiceImplTest extends Specification {
    private GateService gateService = Mock()
    private VehicleService vehicleService = Mock()
    private SlotService slotService = Mock()
    private PaymentService paymentService = Mock()
    private TariffService tariffService = Mock()
    private ParkingEventRepository parkingEventRepository = Mock()
    private EventHelper eventHelper = Mock()

    private EventService eventService = new EventServiceImpl(
            gateService,
            vehicleService,
            slotService,
            paymentService,
            tariffService,
            parkingEventRepository,
            eventHelper
    )

    def "VehicleEntranceService"() {
        given:
        def vehicleDto = TestUtils.random.nextObject(VehicleDto)
        def vehicle = TestUtils.random.nextObject(Vehicle)
        def gateDto = TestUtils.random.nextObject(GateDto)
        def gate = TestUtils.random.nextObject(Gate)
        gate.id = gateDto.id

        def slot = TestUtils.random.nextObject(Slot)
        def tariff = TestUtils.random.nextObject(Tariff)
        def event = TestUtils.random.nextObject(ParkingEvent)
        event.slot = slot

        when:
        def eventResp = eventService.vehicleEntrance(vehicleDto, gateDto)

        then:
        1 * gateService.find(*_) >> gate
        1 * vehicleService.save(vehicleDto) >> vehicle
        1 * slotService.findSlotForVehicle(vehicle, gate) >> slot
        1 * tariffService.findTariffByVehicle(vehicleDto) >> tariff
        1 * parkingEventRepository.save(*_) >> Optional.of(event)
        eventResp.slotInfoDto.slotNumber == String.valueOf(slot.number)
        noExceptionThrown()
    }

    def "vehicleExit"() {
        given:
        def request = TestUtils.random.nextObject(ExitRequest)
        def event = TestUtils.random.nextObject(ParkingEvent)
        def payment = TestUtils.random.nextObject(Payment)
        payment.status = PaymentStatus.SUCCESS

        when:
        def resp = eventService.vehicleExit(request)

        then:
        1 * eventHelper.findEventByTicket(request.getTicket()) >> event
        1 * paymentService.findPaymentByEvent(event) >> payment
        1 * slotService.freeSlot(event.getSlot())
        1 * parkingEventRepository.save(event)
        noExceptionThrown()
        resp.ticketNumber == event.ticketNumber
    }
}
