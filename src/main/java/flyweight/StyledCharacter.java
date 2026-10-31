//CS5800 - HW9 - FLYWEIGHT
//RAJ AHIR - 019330454

package flyweight;

public class StyledCharacter {

    private final char character;
    private final CharacterStyle style;

    public StyledCharacter(char character, CharacterStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("Character style cannot be null");
        }
        this.character = character;
        this.style = style;
    }

    public char getCharacter() {
        return character;
    }

    public CharacterStyle getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return character + " [Font: " + style.getFont() + ", Size: " + style.getSize()
                + ", Color: " + style.getColor() + "]";
    }
}
