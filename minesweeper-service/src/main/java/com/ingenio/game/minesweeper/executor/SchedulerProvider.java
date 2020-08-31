package com.ingenio.game.minesweeper.executor;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public interface SchedulerProvider {

    Scheduler DB_USER_SCHEDULER = Schedulers.newParallel("mysql-user-db-scheduler");
}
