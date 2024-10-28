package com.pos.service;

import com.pos.enums.ResponseEnum;
import com.pos.request.RequestCreateClient;

public interface ClientService {
    ResponseEnum createClient(RequestCreateClient req);
}
