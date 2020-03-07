# Cash Calculator - Team Bahen

## Description 

The Cash Caclulator enables illiterate adults to work large numbers that they may otherwise be unable to deal with.
Illiterate people often have issues reading long multi-digit numbers, but they don't have an issue counting money.
Therefore, there are two key goals as part of this application. The first is that it should provide them an unlimited amount
of currency that they can use to do math with, and the second goal is that over time it will teach them to associate the numeral with
the cash value.

## Key Features
 * Use the app with Kenyan currency
 * Convert images of currency to a numeric representation
 * Clear the screen to start over
 * Perform addition
 * Perform subtraction
 * Perform multiplication
 * Use memory mode to review and modify your calculations
 * Convert a number into images of currency
 * Choose a different currency when the app is opened
 * Use the app with Pakistani currency

## Instructions

 * Use the app with Kenyan currency
   1. Open the app
   2. Tap the hand icon
   3. Kenyan currency is displayed in the scrollbar at the bottom of the screen
 
 * Convert images of currency to a numeric representation
   1. Tap on a denomination at the bottom of the screen
   2. Observe that the number in the top right corner is updated to reflect what is now on the counting table
   
 * Clear the screen to start over
   1. Tap on a denomination at the bottom of the screen
   2. Observe that the number in the top right corner is updated to reflect what is now on the counting table
   3. Tap the handshake icon at the bottom left to clear the screen and reset the number to 0
 
 * Perform addition
   1. Tap on a 500 KES note (number should show 500 in the top right)
   2. Swipe from the right side of the screen towards the left side
   3. Tap on a 100 KES note and a 50 KES note (number should show 150 in the top right)
   4. Tap the addition button at the top left of the screen
   5. The result should now equal KES 650
   
 * Perform subtraction
   1. Clear the screen
   2. Tap on a 500 KES note (number should show 500 in the top right)
   3. Swipe from the left side of the screen towards the right side of the screen
   4. Tap on a 100 KES note and a 50 KES note (number should show 150 in the top right)
   5. Tap the subtraction button at the top left of the screen
   6. The result should now equal KES 350
   
 * Perform multiplication
   1. Clear the screen
   2. Tap on a 500 KES note (number should show 500 in the top right)
   3. Swipe from the top of the screen towards the bottom of the screen
   4. Tap on a 1 KES coin twice
   5. Tap the multiplication button at the top left of the screen
   6. The result should now equal KES 1000
   
 * Use memory mode to review and modify your calculations
   1. Clear the screen
   2. Tap on a 500 KES note (number should show 500 in the top right)
   3. Swipe from the right side of the screen towards the left side
   4. Tap on a 100 KES note and a 50 KES note (number should show 150 in the top right)
   5. Swipe from the top of the screen towards the bottom of the screen
   6. Tap on a 1 KES coin twice
   7. Tap the multiplication button at the top left of the screen
   8. The result should now equal KES 800 (result of 500 + 150 * 2)
   9. Tap the highway icon at the bottom left of the scren
   10. Tap the bridge at the bottom right of the screen twice
   11. Tap the 1 KES coin once
   12. Tap the bridge at the bottom right of the screen once
   13. The result should now be recalculated and display KES 950 (result of 500 + 150 * 3)
   
 * Convert a number into images of currency
   1. Clear the screen
   2. Swipe from the bottom of the counting table (wooden background) to the top of the screen
   3. Tap the number `5` twice
   4. Tap the green checkmark
   5. Observe the currency representation of the numeral
   
 * Choose a different currency when the app is opened and use the app with Pakistani currency
   1. Close the app (if already opened)
   2. Open the app
   3. Tap the Kenyan flag
   4. Tap the Pakistani flag
   
 ## Development requirements
   1. Download and install Android Studio: https://developer.android.com/studio
   2. `git clone https://github.com/csc301-winter-2020/team-project-18-my-oral-village.git`
   3. Open the `android-app` directory of the repo in android studio
   4. When running the app inside an emulator for the first time, follow the walkthrough in Android Studio
 
 ## Deployment and Github Workflow

 Typical workflow to deliver on a task:
  1. Create a personal branch
  2. Develop whatever you need on this branch
  3. Push your branch to the repository
  4. Open a Pull Request (PR)
  5. Assign a reviewer
  6. Work with the reviewer until the PR is in a good state
  7. Merge the PR assuming that all checks have passed (One approved review, branch is up-to-date with master, the build/tests have passed)
  
  We use GitHub Actions as our CI system. We chose it because it is already built into GitHub and doesn't require a third-party service.
  We use GitHub Actions in three ways. The first scenario is to build and test PRs before they are merged. The second scenario is to
  build an APK of the app when there is a change in the master branch. The third scenario is to generate javadocs when there is a change
  in the master branch.

 ## Licenses 

We're still working with our partner to come to a decision on a license. The most likely decision will be MIT or BSD as it would be best to give people
freedom to do what they want with the code without restrictions.
