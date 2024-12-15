package org.lms.service;


import org.lms.dao.request.SignUpRequest;
import org.lms.dao.request.SigninRequest;
import org.lms.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
