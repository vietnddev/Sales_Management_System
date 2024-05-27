package com.flowiee.pms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInfo {
    private String ip;
    private int port;

    public ServerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}