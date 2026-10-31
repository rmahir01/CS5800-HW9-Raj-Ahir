//CS5800 - HW9 - FLYWEIGHT
//RAJ AHIR - 019330454

package flyweight;

public final class CharacterStyle {

    private final String font;
    private final int size;
    private final String color;

    public CharacterStyle(String font, int size, String color) {
        if (font == null || font.isBlank() || color == null || color.isBlank() || size <= 0) {
            throw new IllegalArgumentException("Invalid character style");
        }
        this.font = font;
        this.size = size;
        this.color = color;
    }

    public String getFont() {
        return font;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CharacterStyle other)) {
            return false;
        }

        return size == other.size
                && font.equals(other.font)
                && color.equals(other.color);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(font, size, color);
    }
}
