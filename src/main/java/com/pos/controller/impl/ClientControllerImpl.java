package com.pos.controller.impl;

import com.pos.annotations.BaseControllerImpl;
import com.pos.controller.ClientController;
import com.pos.request.RequestCreateClient;
import com.pos.response.BaseResponse;
import com.pos.service.ClientService;
import com.pos.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {
    private final ClientService clientService;

    @Override
    public BaseResponse createClient(RequestCreateClient req) {
        return ResponseHelper.createBaseResponse(clientService.createClient(req));
    }
}
