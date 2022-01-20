package az.guavapay.user.service.impl;

import az.guavapay.user.dao.entity.CourierEntity;
import az.guavapay.user.dao.entity.CustomerEntity;
import az.guavapay.user.dao.entity.RoleEntity;
import az.guavapay.user.dao.entity.UserEntity;
import az.guavapay.user.dao.repository.CourierRepository;
import az.guavapay.user.dao.repository.CustomerRepository;
import az.guavapay.user.dao.repository.RoleRepository;
import az.guavapay.user.dao.repository.UserRepository;
import az.guavapay.user.exception.RegistrationException;
import az.guavapay.user.exception.RoleNotFoundException;
import az.guavapay.user.exception.UserExistException;
import az.guavapay.user.exception.UserNotFoundException;
import az.guavapay.user.mapper.CourierMapper;
import az.guavapay.user.mapper.CustomerMapper;
import az.guavapay.user.model.dto.AuthRequestDto;
import az.guavapay.user.model.dto.CourierDto;
import az.guavapay.user.model.dto.CustomerDto;
import az.guavapay.user.model.view.UserView;
import az.guavapay.user.model.enums.CourierStatus;
import az.guavapay.user.model.enums.Roles;
import az.guavapay.user.model.response.UserResponse;
import az.guavapay.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static az.guavapay.user.model.enums.ExceptionCode.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(CourierRepository courierRepository,
                           CustomerRepository customerRepository,
                           UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addCustomer(CustomerDto customerDto) {
        log.info("*****add customer start*****");
        boolean isExist = userRepository.findByUsername(customerDto.getUsername()).isPresent();
        if (isExist) {
            throw new RegistrationException(USER_ALREADY_REGISTERED.getCode(), USER_ALREADY_REGISTERED.getMessage());
        }
        RoleEntity roleEntity = roleRepository.findByName(Roles.CUSTOMER.name())
                .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND.getCode(), ROLE_NOT_FOUND.getMessage()));
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.mapEntity(customerDto);
        customerEntity.getUser().setRole(roleEntity);
        customerRepository.save(customerEntity);
        log.info("*****add customer end*****");
    }

    @Override
    public void addCourier(CourierDto courierDto) {
        log.info("*****add courier start*****");
        boolean isExist = userRepository.findByUsername(courierDto.getUsername()).isPresent();
        if (isExist) {
            throw new UserExistException(USER_IS_EXIST.getCode(), USER_IS_EXIST.getMessage());
        }
        RoleEntity roleEntity = roleRepository.findByName(Roles.COURIER.name())
                .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND.getCode(), ROLE_NOT_FOUND.getMessage()));
        CourierEntity courierEntity = CourierMapper.INSTANCE.dtoToEntity(courierDto);
        courierEntity.getUser().setRole(roleEntity);
        courierEntity.setCourierStatus(CourierStatus.NEW);
        courierRepository.save(courierEntity);
        log.info("*****add courier end*****");
    }

    @Override
    public UserView getUser(AuthRequestDto authRequestDto) {
        log.info("checkUser start for username {}", authRequestDto.getUsername());
        UserEntity userEntity = userRepository.
                findByUsernameAndPassword(authRequestDto.getUsername(), authRequestDto.getPassword())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        return UserView.builder().id(userEntity.getId())
                .roleName(userEntity.getRole().getName()).build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("get user by id = " + id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        return UserResponse.builder()
                .userId(userEntity.getId())
                .roleId(userEntity.getRole() != null ? userEntity.getRole().getId() : null)
                .customerId(userEntity.getCustomer() != null ? userEntity.getCustomer().getId() : null)
                .courierId(userEntity.getCourier() != null ? userEntity.getCourier().getId() : null)
                .build();
    }
}
