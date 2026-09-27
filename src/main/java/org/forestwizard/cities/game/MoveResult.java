package org.forestwizard.cities.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MoveResult {
    private final GameStatus status;
    private final String message;
    private int playerScore;
    private int computerScore;
}
