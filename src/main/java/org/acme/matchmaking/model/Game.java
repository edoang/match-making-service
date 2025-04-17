package org.acme.matchmaking.model;

import lombok.Data;

import java.util.Date;


@Data
public class Game {

    public Long id;
    public String userId;
    public Integer won;
    public Integer lost;
    public Boolean over;
    public Date created;
}
