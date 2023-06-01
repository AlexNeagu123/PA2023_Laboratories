package com.ro.server.apitest;

import com.ro.api.dto.request.PlayerRequestDto;
import com.ro.server.apirequests.PlayerRequestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LatencyTestThread extends Thread {
    public final int id;

    @Override
    public void run() {
        PlayerRequestService playerRequestService = new PlayerRequestService();
        playerRequestService.addPlayer(new PlayerRequestDto("p" + id));
    }
}
