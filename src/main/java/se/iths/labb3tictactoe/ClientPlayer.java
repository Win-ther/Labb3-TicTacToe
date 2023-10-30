package se.iths.labb3tictactoe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public record ClientPlayer(StringProperty name, IntegerProperty points, StringProperty symbol) {
}
