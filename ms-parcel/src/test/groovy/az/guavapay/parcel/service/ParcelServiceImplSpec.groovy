package az.guavapay.parcel.service

import com.guavapay.parcel.client.*
import com.guavapay.parcel.entity.*
import com.guavapay.parcel.exception.ParcelNotFoundException
import com.guavapay.parcel.exception.UserNotFoundException
import com.guavapay.parcel.model.dto.AssignParcelDto
import com.guavapay.parcel.model.dto.ChangeParcelDestDto
import com.guavapay.parcel.model.dto.ChangeParcelStatusDto
import com.guavapay.parcel.model.dto.ParcelDto
import com.guavapay.parcel.model.enums.CourierStatus
import com.guavapay.parcel.model.enums.ParcelStatus
import com.guavapay.parcel.model.request.ChangeCourierStatusRequest
import com.guavapay.parcel.model.response.UserResponse
import com.guavapay.parcel.repository.ParcelRepository

import com.guavapay.parcel.service.ParcelService
import com.guavapay.parcel.service.impl.ParcelServiceImpl
import spock.lang.Specification

class ParcelServiceImplSpec extends Specification {

    private ParcelRepository parcelRepository = Mock()
    private UserClient userClient = Mock()
    private CourierClient courierClient = Mock()
    private ParcelService parcelService

    def setup() {
        parcelService = new ParcelServiceImpl(parcelRepository, userClient, courierClient)
    }

    def "createParcel success"() {
        given:
        def parcelDto = ParcelDto.builder()
                .userId(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        def userResponse = UserResponse.builder()
                .userId(1)
                .roleId(1)
                .customerId(1)
                .build()

        when:
        parcelService.createParcel(parcelDto)

        then:
        1 * userClient.getUserById(parcelDto.userId) >> userResponse
        1 * parcelRepository.save(_ as ParcelEntity)
    }

    def "createParcel error"() {
        given:
        def parcelDto = ParcelDto.builder()
                .userId(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        parcelService.createParcel(parcelDto)

        then:
        1 * userClient.getUserById(parcelDto.userId) >> null
        def ex = thrown(UserNotFoundException)
        ex.code == "exception.user-not-found"
        ex.message == "user not found"
    }

    def "changeParcelDestination success"() {
        given:
        def changeParcelDestDto = ChangeParcelDestDto.builder()
                .parcelId(5)
                .build()

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        parcelService.changeParcelDestination(changeParcelDestDto)

        then:
        1 * parcelRepository.findById(changeParcelDestDto.parcelId) >> Optional.of(parcelEntity)
        1 * parcelRepository.save(_ as ParcelEntity)
    }

    def "changeParcelDestination error"() {
        given:
        def changeParcelDestDto = ChangeParcelDestDto.builder()
                .parcelId(5)
                .build()

        when:
        parcelService.changeParcelDestination(changeParcelDestDto)

        then:
        1 * parcelRepository.findById(changeParcelDestDto.parcelId) >> Optional.empty()
        def ex = thrown(ParcelNotFoundException)
        ex.code == "exception.parcel-not-found"
        ex.message == "parcel not found"
    }

    def "cancelParcel success"() {
        given:
        def parcelId = 2

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        parcelService.cancelParcel(parcelId)

        then:
        1 * parcelRepository.findById(parcelId) >> Optional.of(parcelEntity)
        1 * parcelRepository.save(_ as ParcelEntity)
    }

    def "cancelParcel error"() {
        given:
        def parcelId = 2

        when:
        parcelService.cancelParcel(parcelId)

        then:
        1 * parcelRepository.findById(parcelId) >> Optional.empty()
        def ex = thrown(ParcelNotFoundException)
        ex.code == "exception.parcel-not-found"
        ex.message == "parcel not found"
    }

    def "getParcelDetails success"() {
        given:
        def parcelId = 2

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        def actual = parcelService.getParcelDetails(parcelId)

        then:
        1 * parcelRepository.findById(parcelId) >> Optional.of(parcelEntity)
        actual.quantity == parcelEntity.quantity
        actual.weight == parcelEntity.weight
        actual.height == parcelEntity.height
        actual.length == parcelEntity.length
        actual.width == parcelEntity.width
    }

    def "getParcelDetails error"() {
        given:
        def parcelId = 2

        when:
        parcelService.getParcelDetails(parcelId)

        then:
        1 * parcelRepository.findById(parcelId) >> Optional.empty()
        def ex = thrown(ParcelNotFoundException)
        ex.code == "exception.parcel-not-found"
        ex.message == "parcel not found"
    }

    def "getAllParcelsForAdmin success"() {
        given:

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        def actual = parcelService.getAllParcelsForAdmin()

        then:
        1 * parcelRepository.findAll() >> [parcelEntity]
        actual[0].quantity == parcelEntity.quantity
        actual[0].weight == parcelEntity.weight
        actual[0].height == parcelEntity.height
        actual[0].length == parcelEntity.length
        actual[0].width == parcelEntity.width
    }

    def "getAllParcelsByUser success"() {
        given:
        def userId = 3

        def userResponse = UserResponse.builder()
                .userId(1)
                .roleId(2)
                .customerId(4)
                .courierId(3)
                .build()

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        def actual = parcelService.getAllParcelsByUser(userId)

        then:
        1 * userClient.getUserById(userId) >> userResponse
        1 * parcelRepository.findByCustomerId(userResponse.customerId) >> [parcelEntity]
        actual[0].quantity == parcelEntity.quantity
        actual[0].weight == parcelEntity.weight
        actual[0].height == parcelEntity.height
        actual[0].length == parcelEntity.length
        actual[0].width == parcelEntity.width
    }

    def "getAllParcelsByUser error"() {
        given:
        def userId = 3

        when:
        parcelService.getAllParcelsByUser(userId)

        then:
        1 * userClient.getUserById(userId) >> null
        def ex = thrown(UserNotFoundException)
        ex.code == "exception.user-not-found"
        ex.message == "user not found"
    }

    def "changeParcelStatus success"() {
        given:
        def changeParcelStatusDto = ChangeParcelStatusDto.builder()
                .parcelId(1)
                .parcelStatus(ParcelStatus.NEW)
                .build()

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        when:
        parcelService.changeParcelStatus(changeParcelStatusDto)

        then:
        1 * parcelRepository.findById(changeParcelStatusDto.parcelId) >> Optional.of(parcelEntity)
    }

    def "changeParcelStatus error"() {
        given:
        def changeParcelStatusDto = ChangeParcelStatusDto.builder()
                .parcelId(1)
                .parcelStatus(ParcelStatus.NEW)
                .build()

        when:
        parcelService.changeParcelStatus(changeParcelStatusDto)

        then:
        1 * parcelRepository.findById(changeParcelStatusDto.parcelId) >> Optional.empty()
        def ex = thrown(ParcelNotFoundException)
        ex.code == "exception.parcel-not-found"
        ex.message == "parcel not found"
    }

    def "assignParcelToCourier success"() {
        given:
        def assignParcelDto = AssignParcelDto.builder()
                .parcelId(1)
                .courierId(2)
                .build()

        def parcelEntity = ParcelEntity.builder()
                .id(5)
                .quantity(1)
                .weight(1.5)
                .length(2.0)
                .width(1.4)
                .height(2.5)
                .description("test description")
                .build()

        def changeCourierStatusRequest = ChangeCourierStatusRequest.builder()
                .id(assignParcelDto.getCourierId())
                .status(CourierStatus.IN_EXECUTION.ordinal())
                .build()

        when:
        parcelService.assignParcelToCourier(assignParcelDto)

        then:
        1 * parcelRepository.findById(assignParcelDto.parcelId) >> Optional.of(parcelEntity)
        1 * parcelRepository.save(_ as ParcelEntity)
        1 * courierClient.changeCourierStatus(changeCourierStatusRequest)
    }

    def "assignParcelToCourier error"() {
        given:
        def assignParcelDto = AssignParcelDto.builder()
                .parcelId(1)
                .courierId(2)
                .build()

        when:
        parcelService.assignParcelToCourier(assignParcelDto)

        then:
        1 * parcelRepository.findById(assignParcelDto.parcelId) >> Optional.empty()
        def ex = thrown(ParcelNotFoundException)
        ex.code == "exception.parcel-not-found"
        ex.message == "parcel not found"
    }
}
