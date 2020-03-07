# Team Bahen

 > _Note:_ This document is meant to be written during (or shortly after) your review meeting, which should happen fairly close to the due date.      
 >      
 > _Suggestion:_ Have your review meeting a day or two before the due date. This way you will have some time to go over (and edit) this document, and all team members should have a chance to make their contribution.


## Iteration XX - Review & Retrospect

 * When: March 7, 2020 (Meeting Minutes: 35 minutes)
 * Where: Online Meeting on Discord voice chat

## Process - Reflection


#### Q1. Decisions that turned out well

List **process-related** (i.e. team organization and how you work) decisions that, in retrospect, turned out to be successful.

 1. Use Trello to manage tasks  
 We divide the program into many different user stories and make a plan that which user stories should we implement each week on the Trello board. Then, every group member assign stories that they want and starts working. This process makes the entire progress organized. We are able to see which user stories are currently under progress, which user stories are finished, who is working on which user story and which user stories are requesting a pull. Also, it allows our partner to understand the current progress as well as input any useful clarification of a task.  
 
 2. Use Continuous Integration workflow with Pull Request  
We created different GitHub branches for each task so that once a member finished his or her job, he or she will push changes to his or her own branch. Before each merge to the master branch, we have to create a pull request and ask at one team member to review that. Only when the pull request been approved, the new modification can be merged. And the auto-test in CI will be triggered when every pull request made. The two process I mentioned above provide an opportunity for the whole team to review, automated testing, and improvement before the code merged into master banch, which ensures that every piece of code reaching master is usable and production ready.  

 3. Weekly meetings  
 We have a regualr meeting on Monday ourselves and a meeting with our partner on Saturday every week. Those weekly meetings make the team up to date with the current progress of the product, and we can receive timely feedback from our partner, which makes our progress more efficient. It also provides an opportunity for more skilled team members to educate other members on certain skills or languages.  
 
 
#### Q2. Decisions that did not turn out as well as we hoped
List **process-related** (i.e. team organization and how you work) decisions that, in retrospect, were not as successful as you thought they would be.

 1. Use Discord as a form of communication  
 Since one of out team member cannot use facebook, we decided to Discord as our communication platform. Although Discord allows us to display the important meeting information and have online group meetings, it is usually unknown when the message is seen and if they are seen. And it happened several times that some of us ignored the message and have no idea whether the command was received or not.
 
 2. Use uniform naming format and have no documentations  
 At first we thought by using uniform naming format, we won't meet problem when call functions writen by others. However, it turned out that there were some issues. We always need to spend a bunch of time to learn how to use functions, which is really time consuming.



#### Q3. Planned changes

List any **process-related** (i.e. team organization and how you work) changes you are planning to make (if there are any)

 1. Make documentation for each function.
 In each documentation, we planned to clarify when to use this function and how to use that. And for those elusive algorithm in function, we will briefly explain how it works.  
 
 2. Try another communication mode  
 In previous, we decided that if the teammate didn't reply the message sent to him, we will have a consult in person during next team meeting. Now, to assign some work to somebody or post a notification, we will mention the target team member and the mentioned team member needs to reply within 4 hours. If there is no response, we will send E-mail through utMail to notice that team member.  
 
 3. Improve code structure  
 With the development of our project, our package and function need code refactoring and our code structure need to be improve, which can make our structure more organized and easy to use. And the coding refactoring is also good for the future developing.


## Product - Review

#### Q4. How was your product demo?
 * How did you prepare your demo?  
 Every week, our partner can check what are we doing from Trello and we will send the APK of latest version to the partner. He can use this APK to test functions and provide feedback.   
 * What did you manage to demo to your partner?
 * Did your partner accept the features?  
 * Were there change requests?  
 Up to now, we already provided 3 demo to our partner.  
 In the first deno, we finished basic functions, including have both counting table and scroll bar, user can select denomination from scroll bar and show on conuting table with the minimum number of denomations, can swipe left/right/down to perform basic operation. Our partner accepted the demo and gave us some change suggestion. First, scale factor for currency denominations. On the scrollbar, all Pakistan notes should appear to be the same width. For display of notes and coins on the table, keep them identical in size to their appearance on the scrollbar.
 ![Sample_599989](https://user-images.githubusercontent.com/46569172/76153112-79ea1900-6095-11ea-8139-baad812e02e8.png)  
 In the second demo, we resized the denominations on counting table and adjest the arrangement and added a numeric mode, which user can input a written number, and the number automatically be displayed as currency in the counting area. And User can also select different currency. We had a more integrated gesture detection that user can long press the denomination on counting table to remove that. When the number of certain denomination is greater than 4, it will automatically folding and show the number over it. Our partner also accept this demo and the change request is, take screen size into consideration when offsetting denominations in the counting table, add a splash screen to let the user choose currency and fix the problem that the application crashed on small RAM smart phone.  
 In the latest demo, we built a splash screen to choose currence, add one more currency (Kenyan Shilling) to our application and resize the denomination in counting table again. And user can also use the memory mode to check the previous process he made. We also built the library for our project and can run javadoc as part of CI. Our partner also accepted the demo but have some bugs need to fix. And the change request this time is, larger the notes in both counting table and scroll bar, swith numeric mood by sweeping the scrool bar, fix the bug in memory mode and change the operation way in numeric mode.
 * What did you learn from the demo from either a process or product perspective?
 From the process of developing, we learned that sometimes what we understand can be different from what our partner want, which highlight the importance of weekly meeting with the partner. The development enviornment can also differ from the real enviornment, which means there can be many unexpected issue during the development. Like we never met the crash problem when we tested ourselves, but it crashed at the first time when we use the test device. And we also find the importance of teamwork and communication. Sometimes one team member picked certain task to finish but found it's hard to finish it by himself, then he can post his demand in the group then others can help him out.
 * *This section will be marked very leniently so keep it brief and just make sure the points are addressed*

