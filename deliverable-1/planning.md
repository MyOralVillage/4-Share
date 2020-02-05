# YOUR PRODUCT/TEAM NAME
> _Note:_ This document is meant to evolve throughout the planning phase of your project.   That is, it makes sense for you commit regularly to this file while working on the project (especially edits/additions/deletions to the _Highlights_ section). Most importantly, it is a reflection of all the planning you work you've done in the first iteration. 
 > **This document will serve as a master plan between your team, your partner and your TA.**

## Product Details
 
#### Q1: What are you planning to build?

The goal of this project is to write an calculator-like application, which the main screen will consist of two parts, example images area and working area, and  user inputs will be gestures and the app will support numerical digits and cash value translation, basic arithmetic operations (such as + - X ÷), auto-aggregate large quantities and simple fraction visualization. This application is designed to help those who can not read large numbers, but have to operate on money with large face value in daily life. Empowering them with the ability to read complex numerical digit and or perform simple arithmetic operations on them.  
<img width="601" alt="Screen Shot 2020-02-05 at 2 34 02 PM" src="https://user-images.githubusercontent.com/46569172/73876578-377ab580-4825-11ea-9dfa-e0664bf685e2.png">


#### Q2: Who are your target users?

This application is focusing on providing services to people in relatively poor areas in developing countries, where the currency there is heavily inflated. The main user groups are:
* Market Vendors – buyers and sellers  
As a market vendor, John is selling watermelon in a market in Kenya. He can not perform multi-digit arithmetic operations. However, each of his watermelon costs 760 Kenya Shilling, thus he had a hard time when making an exchange with his customers.
* Micro Entrepreneurs  
As a micro entrepreneurs, Kevin manages 8 people. He needs to calculate net profit everyday, which involves addition and subtraction. However, he really can not read numbers that are above 5 digits.
* Record Keepers – e.g. in Savings Groups, shoppers/buyers  
As a record keeper, Jean records every expense and income for her entire family. However, she really has a hard time with performing arithmetic operations on complex numbers.
* Software Developers  
As a software developer, Chi is developing an app targeted to one of the above groups of users. He wants to use an existing library to save development time on his own app.


Our users will typically share some common characteristics (First three groups):  
1. they receive less education so that they have a hard time reading multi-digits numbers,  
2. they can not perform basic arithmetic operations on multi-digits numbers,  
3. money transactions are highly involved in their daily work (heavily inflated currency will force them to deal with multi-digits numbers).  

Therefore, this inspired us to provide an external tool to assist them in daily lives.  

For Software Developers, one of our deliverables is a library that will allow mobile application developers to incorporate similar functionality into their own apps.



#### Q3: Why would your users choose your product? What are they using today to solve their problem/need?

In third world countries like Pakistan and northern Kenya, educational levels are limited in these countries and more than half are not well educated, therefore, neither calculating nor literacy is very strong. Besides, local inflation has made the denomination of the currency larger, which further increases the difficulty for local people to calculate. The ultimate goal of our product development is to make it easier for local people to handle large calculations. Therefore, our products can save users' computing time to the greatest extent and reduce the occurrence of errors to prevent the economic loss of users.  

Today, they use cash or stones to make calculations, they cannot use a calculator app because their illiteracy makes it difficult to comprehend large numbers in written form. Ideally, the app should also teach them how to read numbers, and they can transition to a traditional calculator application as they figure out how to read numbers.  

There are some calculator apps in the App Store which also can perform large amounts of calculations and exchange rate conversions. However, for those who do not have the opportunity to receive education, our app is the best choice, because the app we developed is simple and convenient to operate. And our app also can help them to learn how to read numbers and do simple calculations.


#### Q4: How will you build it?

We will build a native Android application using Java and Android Studio. We will also create a library that other developers may use in developing their own application. The application will be deployed to the Google Play Store. The library will be deployed to Maven Central.  We will use Java instead of Kotlin as the team has Java experience from previous UofT courses. The aim is for it to run well on low-end and old Android devices and to be as small in size as possible.  

To test the application, we will use a combination of strategies. First, we’ll use unit tests for testing functions or classes. For more complex scenarios, we may use espresso to do UI tests. Finally, we will have Pull Request reviews where the reviewer will be responsible to verify that the new changes work as intended, and that code quality is not compromised. As part of the pull request reviews, we will also run our automated tests on a CI system and block the ability to merge them should the tests fail.  
<img width="477" alt="Screen Shot 2020-02-05 at 2 50 29 PM" src="https://user-images.githubusercontent.com/46569172/73877589-f08dbf80-4826-11ea-8621-45273ee82544.png">  
No third party APIs will be used, this application should work completely offline. An Android application is built off of views and activities, they go hand in hand. The views are what the user sees on the screen, and a corresponding activity adds functionality to the view. Since one of the requirements is to deliver a library that developers can incorporate into their own app, the main view/activity will be developed as an external library to the application. The user of the library can specify which currency they would like to use, and the view/activity will behave accordingly. The application will import this library and in addition to the functionality this library provides, the application will also have a currency switcher view.  

This is similar in concept to an MVC architecture. Where models represent aspects of the application state, the view corresponds to the Android XML views, and the controllers are the Android Activities. The application state does not need to be persisted, as this is a purely offline application, and it has no user-specified settings that need to be saved between sessions.



#### Q5: What are the user stories that make up the MVP?
* As a record keeper/market vendor, I want to convert a visual depiction of cash into a number, so that I can keep tabs on my sales.
  * User can place images of bills and coins onto the screen
  * A numeric representation of the amount of currency is automatically displayed as cash is added/removed to the screen
 
* As a user, I should be able to input a number, and the number should automatically displayed as currency in the counting area, so that I can understand what value that number represents to me
  * User can input a number and it is automatically converted to a visual depiction of the currency equivalent
 
* As a micro entrepreneur, I want to count money by adding bills and coins into a common area, so that I can count without having physical access to currency
  * User can tap bills and coins located at the bottom of the screen to add them into the main counting area
  * No limit as to how much currency can be placed into the counting area
  * User can discard bill from the counting area by tapping on it
  * Number is updated to reflect the amount of currency in the counting area
 
* As a record keeper, I want to be able to perform addition, so that I can keep better track of my sales
  * User can swipe right to select addition operation
  * User can swipe left to change their mind and undo the operation
  * If user undoes their operation, then the previous state should be kept and they can see the same bills they placed previously
  * The displayed number automatically reflects the current result
  * Addition is chainable with itself and other arithmetic operations

* As a record keeper, I want to be able to perform subtraction, so that I can keep better track of my sales
  * User can swipe left to select subtraction operation
  * User can swipe right to change their mind and undo the operation
  * If user undoes their operation, then the previous state should be kept and they can see the same bills they placed previously
  * The displayed number automatically reflects the current results
  * Subtraction is chainable with itself and other arithmetic operations

* As a record keeper, I want to be able to perform multiplication, so that I can keep better track of my sales
  * User can swipe up to perform multiplication
  *User can swipe down to change their mind and undo the operation
  *If user undoes their operation, then the previous state should be kept and they can see the same bills they placed previously
  * The displayed number automatically reflects the current results
  *Multiplication is chainable with itself and other arithmetic operations

* As a record keeper, I want to be able to perform division, so that I can keep better track of my sales
  * User can swipe down to perform division
  * User can swipe up to change their mind and undo the operation
  * If user undoes their operation, then the previous state should be kept and they can see the same bills they placed previously
  * The displayed number automatically reflects the current results
  * Division is chainable with itself and other arithmetic operations

* As a user, I want to be able to select a currency, so I can use the cash calculator with any available currency I wish to work with
  * User can click the settings button to bring up a view with a selection of currency
  * User can click on a currency
  * Cash calculator should reset to the default state with the newly selected currency at the bottom of the screen

* As a user, the cash calculator should default to the currency of my country and if it is not available then fallback to the USD, so that the default experience is personalized towards me
  * If available, the currency in the cash calculator should default to the phone’s locale
  * If the currency for the user’s locale is not available, then default to the USD

* As a software developer, I want to be able to include the cash calculator as a component in my own mobile application, to give illiterate users a way to deal with money
  * Ability for developer to programmatically specify which currency should be used
  * Ability for developer to programmatically retrieve the current result of the calculation

* As a software developer, I want to be able to include the counting table as an independent widget, so that I can display a number as monetary value in my application
  * Ability for developer to programmatically specify which currency should be displayed
  * Ability for programmer to programmatically specify the number that should be displayed in the form of currency
  * Ability for programmer to programmatically specify the background image of the counting table

* As a software developer, I want to be able to include the scroll bar as an independent widget, so that I can use it for non-calculation purposes
  * Ability for programmer to programmatically specify which currency should be displayed in the scrollbar
  * The scroll bar should dispatch events indicating which type of currency was tapped by the user
  * Ability for programmer to specify the background image of the scrollbar

* As a user, upon starting the application for the first time a short video should play, so that I understand how to use the spatial navigation feature to make calculations
  * Upon starting the application for the first time a video should play
  * Upon subsequent startups the video should not play

* As a user, I want to multiply using stones, so I can quickly multiply on the main screen without using spatial navigation
  * Should multiply when the toggle is set to the left position
  * Amount of cash on the screen is multiplied by the number of black stones specified
  * User can add black stones by dragging up from the one black stone at the bottom of the stack
  * User should be able to multiply by 1, 2, 3, 4, 5, 6, 7, 8, 9, or 10

* As a user, I want to divide using stones, so I can quickly divide on the main screen without using spatial navigation
  * Should divide when the toggle is set to the right position
  * Amount of cash on the screen is divided by the number of black stones specified
  * User can add black stones by dragging up from the one black stone at the bottom of the stack
  * User should be able to divide by 1, 2, 3, 4, 5, 6, 7, 8, 9, or 10

* As a user, I should be able to switch scrollbars, to input digits instead of adding currency to the counting area
  * User can swipe horizontally on the scrollbar to switch input method to digits

* As a user, I should be able to switch scrollbars, to input currency instead of inputting digits
  * User can swipe horizontally on the scrollbar to switch input method to currency

* As a user, I should be able to work with the Kenyan Shilling, so that I can use it to count and do math
  * Kenyan currency is displayed in the scrollbar

* As a user, I should be able to work with the US Dollar, so that I can use it to count and do math
  * US Dollar is displayed in the scrollbar


----

## Process Details

#### Q6: What are the roles & responsibilities on the team?

Describe the different roles on the team and the responsibilities associated with each role. 
 * Roles should reflect the structure of your team and be appropriate for your project. Not necessarily one role to one team member.

List each team member and:
 * A description of their role(s) and responsibilities including the components they'll work on and non-software related work
 * 3 technical strengths and weaknesses each (e.g. languages, frameworks, libraries, development methodologies, etc.)

#### Q7: What operational events will you have as a team?

Describe meetings (and other events) you are planning to have. 
 * When and where? Recurring or ad hoc? In-person or online?
 * What's the purpose of each meeting?
 * Other events could be coding sessions, code reviews, quick weekly sync meeting online, etc.
 * You must have at least 2 meetings with your project partner (if you have one). Describe them here:
   * What did you discuss during the meetings?
   * What were the outcomes of each meeting?
   * You must provide meeting minutes.
   * You must have a regular meeting schedule established by the second meeting.  
  
#### Q8: What artifacts will you use to self-organize?

List/describe the artifacts you will produce in order to organize your team.       

 * Artifacts can be To-Do lists, Task boards, schedule(s), meeting minutes, etc.
 * We want to understand:
   * How do you keep track of what needs to get done?
   * How do you prioritize tasks?
   * How do tasks get assigned to team members?
   * How do you determine the status of work from inception to completion?

#### Q9: What are the rules regarding how your team works?

Describe your team's working culture.

**Communications:**
 * What is the expected frequency? What methods/channels are appropriate? 
 * If you have a partner project, what is your process (in detail) for communicating with your partner?
 
**Meetings:**
 * How are people held accountable for attending meetings, completing action items? Is there a moderator or process?
 
**Conflict Resolution:**
 * List at least three team scenarios/conflicts you discussed in lecture and how you decided you will resolve them. Indecisions? Non-responsive team members? Any other scenarios you can think of?




----
### Highlights

Specify 3 - 5 key decisions and/or insights that came up during your meetings
and/or collaborative process.

 * Short (5 min' read max)
 * Decisions can be related to the product and/or the team process.
    * Mention which alternatives you were considering.
    * Present the arguments for each alternative.
    * Explain why the option you decided on makes the most sense for your team/product/users.
 * Essentially, we want to understand how (and why) you ended up with your current product and process plan.
 * This section is useful for important information regarding your decision making process that may not necessarily fit in other sections. 
