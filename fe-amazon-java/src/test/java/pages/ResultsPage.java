package pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.NotImplementedException;
import enums.DepartmentEnum;

public class ResultsPage extends MenuPage {
    @FindBy(id = "n/12896151")
    private WebElementFacade engravingMachines;

    @FindBy(css = ".a-dropdown-container:has(#s-result-sort-select)")
    private WebElementFacade sortingEnableSelect;

    @FindBy(css = "#s-result-sort-select")
    private WebElementFacade sortingSelect;

    public void clickDepartment(DepartmentEnum department) {
        WebElementFacade departmentSelected;
        switch (department) {
            case ENGRAVING_MACHINES:
                departmentSelected = engravingMachines;
                break;
            default:
                throw new NotImplementedException(String.format("Department %s has not yet implemented identifier", department));
        }

        departmentSelected.click();
    }

    public void selectSorting(String sorting) {
        sortingEnableSelect.click();
        sortingSelect.selectByVisibleText(sorting);
    }

    public void selectProductAtPosition(int position) {
        int positionId = position + 1;
        find(By.cssSelector(String.format("[data-index='%s'] a", positionId))).click();
    }
}
