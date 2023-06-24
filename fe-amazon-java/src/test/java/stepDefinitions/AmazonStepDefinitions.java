package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import steps.AmazonSteps;

/**
 * Step definitions used by cucumber feature file
 */
public class AmazonStepDefinitions {
    @Steps
    AmazonSteps amazonSteps;

    @Given("user has opened the main page")
    public void userHasOpenedTheMainPage() {
        amazonSteps.open();
    }

    @Given("user clicks on hamburger menu")
    public void userClicksOnHamburgerMenu() {
        amazonSteps.openHamburgerMenu();
    }

    @When("user selects menu {string}")
    public void userSelectsMenu(String menu) {
        amazonSteps.clickMenu(menu);
    }

    @When("user selects department {string}")
    public void userSelectsDepartment(String department) {
        amazonSteps.clickDepartment(department);
    }

    @When("user sorts by {string}")
    public void userSortsBy(String sort) {
        amazonSteps.sorting(sort);
    }

    @When("user selects {int}th product")
    public void userSelectsThProduct(int position) {
        amazonSteps.selectProduct(position);
    }

    @Then("review score shall be {float} or higher")
    public void reviewScoreShallBeXOrHigher(float score) {
        amazonSteps.reviewScoreShallBeXOrHigher(score);
    }

    @Then("pricing shall not be more than {float} USD")
    public void pricingShallNotBeMoreThanUSD(float pricing) {
        amazonSteps.confirmPricingUpTo(pricing);
    }
}
