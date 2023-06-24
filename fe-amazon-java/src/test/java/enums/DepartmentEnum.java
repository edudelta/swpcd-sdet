package enums;

/**
 * All departments naming mapping to enum
 */
public enum DepartmentEnum {
    ENGRAVING_MACHINES("Engraving Machines & Tools");

    private String textDescription;

    DepartmentEnum(String textDescription) {
        this.textDescription = textDescription;
    }

    public static DepartmentEnum fromString(String text) {
        for (DepartmentEnum menu : DepartmentEnum.values()) {
            if (menu.textDescription.equalsIgnoreCase(text)) {
                return menu;
            }
        }
        return null;
    }
}
