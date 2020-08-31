package com.ingenio.game.minesweeper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingenio.game.minesweeper.dto.GameInfo;
import com.ingenio.game.minesweeper.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;

//@SpringBootTest
class MinesweeperApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {

		String objeto = "[[\"X\",\"X\",\"X\"],[\"X\",\"M\",\"X\"],[\"X\",\"X\",\"X\"]]";

		//ObjectMapper mapper = new ObjectMapper();
		//String[][] asArray = mapper.readValue(objeto, String[][].class);

		String[][] asArray = JsonUtils.convert(objeto, String[][].class);
		System.out.println("Board " + asArray);

		var g = GameInfo.builder()
				.gameId("asd123")
				.status("FINISHED")
				.board(asArray)
				.dateCreated(Instant.now().getEpochSecond())
				.lastUpdated(Instant.now().getEpochSecond())
				.timeDurationSeconds(500L)
				.build();

		System.out.println("Game " + g);

	}

}
