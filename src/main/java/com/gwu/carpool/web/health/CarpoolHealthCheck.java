package com.gwu.carpool.web.health;

import com.codahale.metrics.health.HealthCheck;

public class CarpoolHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}