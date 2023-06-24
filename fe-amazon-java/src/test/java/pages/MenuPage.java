package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import enums.MenuEnum;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

/**
 * All pages used are menu based
 */
public abstract class MenuPage extends PageObject {
    @FindBy(id = "hmenu-canvas-background")
    private WebElementFacade menuBackground;

    @FindBy(id = "nav-hamburger-menu")
    private WebElementFacade hamburgerMenu;

    @FindBy(css = "[data-menu-id='8']")
    private WebElementFacade artsAndCrafts;

    @FindBy(xpath = "//*[@data-menu-id='8']//a[text()='Beading & Jewelry Making']")
    private WebElementFacade beadingAndJewelryMaking;

    public void openHamburgerMenu() {
        hamburgerMenu.click();
        withTimeoutOf(Duration.ofSeconds(1))
                .waitFor(ExpectedConditions.visibilityOf(menuBackground));
        guaranteeMenuOpened();
    }

    // By some reason, sometimes menu closes by itself. This is to guarantee menu opened
    private void guaranteeMenuOpened() {
        try {
            withTimeoutOf(Duration.ofSeconds(1))
                    .waitFor(ExpectedConditions.invisibilityOf(menuBackground));
            hamburgerMenu.click();
        }
        catch (Exception ignore) {}
    }

    public void clickMenu(@NotNull MenuEnum menu) {
        WebElementFacade menuSelected;
        switch (menu) {
            case ARTS_AND_CRAFTS:
                menuSelected = artsAndCrafts;
                break;
            case BEADING_JEWELRY_MAKING:
                menuSelected = beadingAndJewelryMaking;
                break;
            default:
                throw new NotImplementedException(String.format("Menu %s has not yet implemented identifier", menu));
        }

        menuSelected.click();
    }
}
