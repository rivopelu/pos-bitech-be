package com.pos.controller.impl;

import com.pos.annotations.BaseControllerImpl;
import com.pos.controller.PingController;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class PingControllerImpl implements PingController {

    @Override
    public String ping() {
        return "pong";
    }
}
