package org.forestwizard.cities.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MoveResult {
    private final MoveResultType type;
    private final String message;
    private int playerScore;
    private int computerScore;
}
