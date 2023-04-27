package ro.game.maps;

import lombok.Getter;
import ro.shared.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>Cell</tt> class represents the space where all the {@link Token} objects are stored in each node.
 */
@Getter
public class Cell {
    public List<Token> tokenList;

    public Cell() {
        tokenList = new ArrayList<>();
    }

    public void addToken(Token token) {
        tokenList.add(token);

    }
}