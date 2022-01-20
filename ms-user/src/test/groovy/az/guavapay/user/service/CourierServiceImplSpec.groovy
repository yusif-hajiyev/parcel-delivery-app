package az.guavapay.user.service

import az.guavapay.user.dao.entity.AddressEntity
import az.guavapay.user.dao.entity.CourierEntity
import az.guavapay.user.dao.entity.RoleEntity
import az.guavapay.user.dao.entity.UserEntity
import az.guavapay.user.dao.repository.CourierRepository
import az.guavapay.user.exception.UserNotFoundException
import az.guavapay.user.model.dto.ChangeCourierStatusDto
import az.guavapay.user.model.enums.CourierStatus
import az.guavapay.user.model.enums.CourierTypes
import az.guavapay.user.service.impl.CourierServiceImpl
import spock.lang.Specification

class CourierServiceImplSpec extends Specification {

    private CourierRepository courierRepository = Mock()
    private CourierService courierService

    def setup() {
        courierService = new CourierServiceImpl(courierRepository)
    }

    def "getCourierList success"() {
        given:
        def roleEntity = RoleEntity.builder()
                .name("COURIER").build()

        def userEntity = UserEntity.builder()
                .id(1)
                .username("yusif123")
                .password("123456")
                .role(roleEntity)
                .build()

        def addressEntity = AddressEntity.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .longitude("43.93939393")
                .latitude("44.49394934949")
                .build()

        CourierEntity courierEntity = CourierEntity.builder()
                .id(1)
                .fullName("Yusif Hajiyev")
                .courierType(CourierTypes.LuggageService)
                .courierCompanyName("Fedex")
                .user(userEntity)
                .address(addressEntity)
                .description("description")
                .courierStatus(CourierStatus.NEW)
                .build()

        when:
        def actual = courierService.getCourierList()

        then:
        1 * courierRepository.findAll() >> [courierEntity]
        actual[0].fullName == courierEntity.fullName
        actual[0].courierType == courierEntity.courierType
        actual[0].courierCompanyName == courierEntity.courierCompanyName
        actual[0].courierStatus == courierEntity.courierStatus
    }

    def "getCourierListByStatus success"() {
        given:
        def status = 2
        def enumStatus = CourierStatus.IN_EXECUTION

        def roleEntity = RoleEntity.builder()
                .name("COURIER").build()

        def userEntity = UserEntity.builder()
                .id(1)
                .username("yusif123")
                .password("123456")
                .role(roleEntity)
                .build()

        def addressEntity = AddressEntity.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .longitude("43.93939393")
                .latitude("44.49394934949")
                .build()

        CourierEntity courierEntity = CourierEntity.builder()
                .id(1)
                .fullName("Yusif Hajiyev")
                .courierType(CourierTypes.LuggageService)
                .courierCompanyName("Fedex")
                .user(userEntity)
                .address(addressEntity)
                .description("description")
                .courierStatus(CourierStatus.IN_EXECUTION)
                .build()

        when:
        def actual = courierService.getCourierListByStatus(status)

        then:
        1 * courierRepository.findByCourierStatus(enumStatus) >> [courierEntity]
        actual[0].fullName == courierEntity.fullName
        actual[0].courierType == courierEntity.courierType
        actual[0].courierCompanyName == courierEntity.courierCompanyName
        actual[0].courierStatus == courierEntity.courierStatus
    }

    def "changeCourierStatus success"() {
        given:
        def statusDto = ChangeCourierStatusDto.builder()
                .id(1)
                .status(2)
                .build()

        def courierEntity = CourierEntity.builder()
                .id(1)
                .fullName("Yusif Hajiyev")
                .courierType(CourierTypes.LuggageService)
                .courierCompanyName("Fedex")
                .description("description")
                .courierStatus(CourierStatus.IN_EXECUTION)
                .build()

        when:
        courierService.changeCourierStatus(statusDto)

        then:
        1 * courierRepository.findById(statusDto.id) >> Optional.of(courierEntity)
        1 * courierRepository.save(_ as CourierEntity)
    }

    def "changeCourierStatus error"() {
        given:
        def statusDto = ChangeCourierStatusDto.builder()
                .id(1)
                .status(2)
                .build()

        when:
        courierService.changeCourierStatus(statusDto)

        then:
        1 * courierRepository.findById(statusDto.id) >> Optional.empty()
        def ex = thrown(UserNotFoundException)
        ex.code == "exception.courier-not-found"
        ex.message == "courier not found"
    }
}
