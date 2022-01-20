package az.guavapay.auth.service

import az.guavapay.auth.AuthService
import az.guavapay.auth.AuthServiceImpl
import az.guavapay.auth.client.UserClient
import az.guavapay.auth.exception.AuthException
import az.guavapay.auth.model.AuthRequest
import az.guavapay.auth.model.AuthResponse
import az.guavapay.auth.model.UserView
import az.guavapay.auth.security.JwtResponse
import az.guavapay.auth.security.JwtTokenUtil
import spock.lang.Specification

class AuthServiceImplSpec extends Specification {

    private JwtTokenUtil jwtTokenUtil = Mock()
    private UserClient userClient = Mock()
    private AuthService authService

    def setup() {
        authService = new AuthServiceImpl(jwtTokenUtil, userClient)
    }

    def "login success"() {
        given:
        def authRequest = AuthRequest.builder()
                .username("yusif123")
                .password("123456")
                .build()

        def userView = UserView.builder()
                .id(1)
                .roleName("ADMIN")
                .build()

        def token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGlzYWhpYjkwO"

        def authResponse = AuthResponse.builder()
                .userId(1)
                .token(token)
                .build()

        when:
        def actual = authService.login(authRequest)

        then:
        1 * userClient.getUser(authRequest) >> userView
        1 * jwtTokenUtil.generateToken(authRequest.username, userView.roleName) >> token
        actual.userId == authResponse.userId
        actual.token == authResponse.token
    }

    def "login error"() {
        given:
        def authRequest = AuthRequest.builder()
                .username("yusif123")
                .password("123456")
                .build()

        when:
        authService.login(authRequest)

        then:
        1 * userClient.getUser(authRequest) >> null
        def ex = thrown(AuthException)
        ex.code == "exception.auth-forbidden"
        ex.message == "username or password is incorrect"
    }

    def "validateToken success"() {
        given:
        def token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGlzYWhpYjkwO"
        def username = "yusif123"

        def jwtResponse = JwtResponse.builder()
                .isValidToken(true)
                .roleName("ADMIN")
                .build()

        when:
        def actual = authService.validateToken(token, username)

        then:
        1 * jwtTokenUtil.validateToken(token, username) >> jwtResponse
        actual.validToken == jwtResponse.validToken
        actual.roleName == jwtResponse.roleName
    }
}
