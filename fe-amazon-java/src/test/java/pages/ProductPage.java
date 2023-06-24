package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class ProductPage extends MenuPage {
    @FindBy(css = "#averageCustomerReviews a[role='button'] .a-size-base")
    private WebElementFacade reviewScore;

    @FindBy(id = "priceValue")
    private WebElementFacade pricing;

    public Float getReviewScore() {
        if (reviewScore.isPresent()) {
            return Float.valueOf(reviewScore.getText().trim());
        }

        return null;
    }

    public Float getPricing() {
        if (pricing.isPresent()) {
            // Using javascript due hidden element throws Exception with Selenium.
            String pricingValue = ((String)evaluateJavascript("return document.getElementById('priceValue').value")).trim();
            return Float.valueOf(pricingValue);
        }

        return null;
    }
}
