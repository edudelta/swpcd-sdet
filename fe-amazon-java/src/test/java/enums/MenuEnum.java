package enums;

/**
 * Menu naming to enum
 */
public enum MenuEnum {
    ARTS_AND_CRAFTS("Arts & Craft"),
    BEADING_JEWELRY_MAKING("Beading & Jewelry Making");

    private String textDescription;

    MenuEnum(String textDescription) {
        this.textDescription = textDescription;
    }

    public static MenuEnum fromString(String text) {
        for (MenuEnum menu : MenuEnum.values()) {
            if (menu.textDescription.equalsIgnoreCase(text)) {
                return menu;
            }
        }
        return null;
    }
}
