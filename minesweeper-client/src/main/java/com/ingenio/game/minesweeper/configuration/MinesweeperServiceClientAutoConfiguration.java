package com.ingenio.game.minesweeper.configuration;

import com.ingenio.game.minesweeper.MinesweeperGameClient;
import com.ingenio.game.minesweeper.MinesweeperUserClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "minesweeper-service.client.api", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean({MinesweeperUserClient.class, MinesweeperGameClient.class})
@EnableFeignClients(clients = {MinesweeperUserClient.class, MinesweeperGameClient.class})
public class MinesweeperServiceClientAutoConfiguration {

}
