@product
Feature: Product Rating

  Background:
    Given user has opened the main page

  Scenario: Check product
    When user clicks on hamburger menu
    And user selects menu "Arts & Craft"
    And user selects menu "Beading & Jewelry Making"
    And user selects department "Engraving Machines & Tools"
    # Note: must expensive products have no review. You may use following filter, instead pricing
    #And user sorts by "Avg. Customer Review"
    And user sorts by "Price: High to Low"
    And user selects 3th product
    Then review score shall be 4 or higher
    Then pricing shall not be more than 4000 USD


