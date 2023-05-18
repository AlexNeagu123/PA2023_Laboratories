package ro.server.tournament;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Match {
    private final int firstPlayer;
    private final int secondPlayer;
}
