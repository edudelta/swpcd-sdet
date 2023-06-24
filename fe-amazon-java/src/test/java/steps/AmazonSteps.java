package steps;

import net.thucydides.core.annotations.Step;
import enums.DepartmentEnum;
import enums.MenuEnum;
import pages.InitialPage;
import pages.ProductPage;
import pages.ResultsPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class AmazonSteps {
    InitialPage initialPage;
    ResultsPage resultsPage;
    ProductPage productPage;

    @Step
    public void open() {
        initialPage.open();
    }

    @Step
    public void openHamburgerMenu() {
        initialPage.openHamburgerMenu();
    }

    @Step
    public void clickMenu(String menu) {
        MenuEnum menuToClick = MenuEnum.fromString(menu);
        initialPage.clickMenu(menuToClick);
    }

    @Step
    public void clickDepartment(String department) {
        DepartmentEnum departmentToClick = DepartmentEnum.fromString(department);
        resultsPage.clickDepartment(departmentToClick);
    }

    @Step
    public void sorting(String sort) {
        resultsPage.selectSorting(sort);
    }

    @Step
    public void selectProduct(int position) {
        resultsPage.selectProductAtPosition(position);
    }

    @Step
    public void reviewScoreShallBeXOrHigher(float score) {
        Float reviewScore = productPage.getReviewScore();
        // Sometimes product has no review score
        if (reviewScore != null) {
            assertThat(reviewScore, greaterThanOrEqualTo(score));
        }
    }

    @Step
    public void confirmPricingUpTo(float pricing) {
        Float realPricing = productPage.getPricing();
        // Sometimes product has no pricing
        if (realPricing != null) {
            assertThat(realPricing, lessThanOrEqualTo(pricing));
        }
    }
}
