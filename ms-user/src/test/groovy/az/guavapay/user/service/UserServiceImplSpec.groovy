package az.guavapay.user.service

import az.guavapay.user.dao.entity.CourierEntity
import az.guavapay.user.dao.entity.CustomerEntity
import az.guavapay.user.dao.entity.RoleEntity
import az.guavapay.user.dao.entity.UserEntity
import az.guavapay.user.dao.repository.CourierRepository
import az.guavapay.user.dao.repository.CustomerRepository
import az.guavapay.user.dao.repository.RoleRepository
import az.guavapay.user.dao.repository.UserRepository
import az.guavapay.user.exception.RegistrationException
import az.guavapay.user.exception.RoleNotFoundException
import az.guavapay.user.exception.UserExistException
import az.guavapay.user.exception.UserNotFoundException
import az.guavapay.user.model.dto.AddressDto
import az.guavapay.user.model.dto.AuthRequestDto
import az.guavapay.user.model.dto.CourierDto
import az.guavapay.user.model.dto.CustomerDto
import az.guavapay.user.model.enums.CourierTypes
import az.guavapay.user.model.enums.Roles
import az.guavapay.user.service.impl.UserServiceImpl
import spock.lang.Specification

class UserServiceImplSpec extends Specification {

    private CourierRepository courierRepository = Mock()
    private CustomerRepository customerRepository = Mock()
    private UserRepository userRepository = Mock()
    private RoleRepository roleRepository = Mock()
    private UserService userService

    def setup() {
        userService = new UserServiceImpl(courierRepository,
                customerRepository,
                userRepository,
                roleRepository)
    }


    def "addCustomer success"() {
        given:
        def addressDto = AddressDto.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .build()

        def customerDto = CustomerDto.builder()
                .email("yusif@mail.ru")
                .username("yusif123")
                .password("123456")
                .fullName("Yusif Hajiyev")
                .address(addressDto)
                .build()

        def roleEntity = RoleEntity.builder()
                .name("CUSTOMER").build()


        when:
        userService.addCustomer(customerDto)

        then:
        1 * userRepository.findByUsername(customerDto.username) >> Optional.empty()
        1 * roleRepository.findByName(Roles.CUSTOMER.name()) >> Optional.of(roleEntity)
        1 * customerRepository.save(_ as CustomerEntity)
    }

    def "addCustomer isExist true"() {
        given:
        def addressDto = AddressDto.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .build()

        def customerDto = CustomerDto.builder()
                .email("yusif@mail.ru")
                .username("yusif123")
                .password("123456")
                .fullName("Yusif Hajiyev")
                .address(addressDto)
                .build()

        when:
        userService.addCustomer(customerDto)

        then:
        1 * userRepository.findByUsername(customerDto.username) >> Optional
                .of(new CustomerEntity())
        def ex = thrown(RegistrationException)
        ex.code == "exception.user-already-registered"
        ex.message == "user already registered"
    }

    def "addCustomer role not found"() {
        given:
        def addressDto = AddressDto.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .build()

        def customerDto = CustomerDto.builder()
                .email("yusif@mail.ru")
                .username("yusif123")
                .password("123456")
                .fullName("Yusif Hajiyev")
                .address(addressDto)
                .build()

        when:
        userService.addCustomer(customerDto)

        then:
        1 * userRepository.findByUsername(customerDto.username) >> Optional.empty()
        1 * roleRepository.findByName(Roles.CUSTOMER.name()) >> Optional.empty()
        def ex = thrown(RoleNotFoundException)
        ex.message == "role not found"
        ex.code == "exception.role-not-found"
    }

    def "addCourier success"() {
        given:
        def addressDto = AddressDto.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .build()

        def courierDto = CourierDto.builder()
                .username("yusif123")
                .password("123456")
                .fullName("Yusif Hajiyev")
                .courierType(CourierTypes.LuggageService)
                .courierCompanyName("Fedex")
                .address(addressDto)
                .description("description")
                .build()

        def roleEntity = RoleEntity.builder()
                .name("COURIER").build()


        when:
        userService.addCourier(courierDto)

        then:
        1 * userRepository.findByUsername(courierDto.username) >> Optional.empty()
        1 * roleRepository.findByName(Roles.COURIER.name()) >> Optional.of(roleEntity)
        1 * courierRepository.save(_ as CourierEntity)
    }

    def "addCourier isExist true"() {
        given:
        def addressDto = AddressDto.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .build()

        def courierDto = CourierDto.builder()
                .username("yusif123")
                .password("123456")
                .fullName("Yusif Hajiyev")
                .courierType(CourierTypes.LuggageService)
                .courierCompanyName("Fedex")
                .address(addressDto)
                .description("description")
                .build()

        when:
        userService.addCourier(courierDto)

        then:
        1 * userRepository.findByUsername(courierDto.username) >> Optional
                .of(new CourierEntity())
        def ex = thrown(UserExistException)
        ex.code == "exception.user-is-exist"
        ex.message == "user is exist"
    }

    def "addCourier role not found"() {
        given:
        def addressDto = AddressDto.builder()
                .address("Baku, Nizami district")
                .region("Nizami")
                .postalCode(12239494)
                .country("Azerbaijan")
                .city("Baku")
                .phone("+994513845846")
                .build()

        def courierDto = CourierDto.builder()
                .username("yusif123")
                .password("123456")
                .fullName("Yusif Hajiyev")
                .courierType(CourierTypes.LuggageService)
                .courierCompanyName("Fedex")
                .address(addressDto)
                .description("description")
                .build()

        when:
        userService.addCourier(courierDto)

        then:
        1 * userRepository.findByUsername(courierDto.username) >> Optional.empty()
        1 * roleRepository.findByName(Roles.COURIER.name()) >> Optional.empty()
        def ex = thrown(RoleNotFoundException)
        ex.message == "role not found"
        ex.code == "exception.role-not-found"
    }

    def "getUser success"() {
        given:
        def authRequestDto = AuthRequestDto.builder()
                .username("yusif123")
                .password("123456")
                .build()

        def roleEntity = RoleEntity.builder()
                .name("CUSTOMER").build()

        def userEntity = UserEntity.builder()
                .id(1)
                .username("yusif123")
                .password("123456")
                .role(roleEntity)
                .build()

        when:
        def actual = userService.getUser(authRequestDto)

        then:
        1 * userRepository.findByUsernameAndPassword(authRequestDto.username, authRequestDto.password) >> Optional.of(userEntity)
        actual.id == userEntity.id
        actual.roleName == userEntity.role.name
    }

    def "getUser error"() {
        given:
        def authRequestDto = AuthRequestDto.builder()
                .username("yusif123")
                .password("123456")
                .build()

        when:
        userService.getUser(authRequestDto)

        then:
        1 * userRepository.findByUsernameAndPassword(authRequestDto.username, authRequestDto.password) >> Optional.empty()
        def ex = thrown(UserNotFoundException)
        ex.code == "exception.user-not-found"
        ex.message == "user not found"
    }

    def "getUserById success"() {
        given:
        def id = 5

        def roleEntity = RoleEntity.builder()
                .name("CUSTOMER").build()

        def userEntity = UserEntity.builder()
                .id(1)
                .username("yusif123")
                .password("123456")
                .role(roleEntity)
                .build()

        when:
        def actual = userService.getUserById(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(userEntity)
        actual.userId == userEntity.id
        actual.roleId == userEntity.role.id
    }

    def "getUserById error"() {
        given:
        def id = 5

        when:
        userService.getUserById(id)

        then:
        1 * userRepository.findById(id) >> Optional.empty()
        def ex = thrown(UserNotFoundException)
        ex.code == "exception.user-not-found"
        ex.message == "user not found"
    }
}
