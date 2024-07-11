# Basketball Team Management System

## Overview 

The Basketball Team Management System is a robust application designed to empower basketball coaches, team managers, and players with tools for efficient team organization and performance enhancement. This comprehensive system offers a range of features tailored to simplify the management of basketball teams, enhance player development, and streamline team activities.


As a developer, this project resonates with my passion for both software development and basketball. The project holds personal significance as it addresses a gap I experienced in high school, where inadequate handling and planning hindered our team's performance. This endeavor allows me to combine my technical skills with a genuine interest in sports, creating a practical and user-friendly tool to positively impact basketball teams by improving organization and performance tracking.
## Key Features
Some key features of what the application can do includes:
- ***Player Management***: Keep your team roster up-to-date with player profiles, including positions, jersey number, and performance statistics. Make informed decisions when forming lineups.

- ***Team Overview***: Access a centralized dashboard for comprehensive lists of players, coaches, and upcoming games. Efficiently manage team members and enhance communication.

- ***Track Games***: Record game details easily, including opponents, dates, and venues. Analyze team performance to make strategic decisions for future games.

- ***Training Sessions***: Plan and conduct effective training sessions with detailed plans. Review session performance and tailor future sessions for improvement.

## Target Audience
This application is tailored for middle school, high school, and university basketball teams consisting of coaches, team managers, and players who are passionate about optimizing their team's performance. Coaches can efficiently manage their team, plan training sessions, and analyze game results. Players benefit from having a centralized platform for tracking their performance and staying informed about team activities.

## User Stories

- As a user, I want to seamlessly create a new basketball team based on an age group, add players to the team with their positions and jersey numbers.
- As a user, I want to view a list of players on a team and view stats based on jersey number. 
- As a user, I want to schedule and view games and record outcomes and update individual player statistics after each game.
- As a user, I want to plan and create training sessions, at a date when team has no games with specific players of the team.
- As a user, when I select the quit option, I want to be reminded to save the created team including player statistics, game schedule history, and training schedule, to a file and have the option to do so or not.
- As a user, when I start the application I want to have the option of reloading my created team and associated state and resume where it was left off last time. 

## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Player to a Team" by clicking add player on the home window and filling prompted details. 
- You can generate the second required action related to the user story "adding multiple Player to a Team" by clicking view player list on the home window.
- You can locate my visual component as soon as you start the application when you run the Start window class.
- You can save the state of the application when clicking quit on the home window and clicking yes on the prompt to save.
- You can reload the state of my application by clicking load existing team after the application loads to the main window.

## Phase 4: Task 2

Logged Events:
New team created: UBC
New player created: Dhruv
Jersey number set to 20
Player added to team: Dhruv
Position accessed for player: Dhruv
Jersey number accessed for player: Dhruv
Jersey number accessed for player: Dhruv
Player found by jersey number: 20
Performance stats accessed for player: Dhruv
Performance stats accessed for player: Dhruv
Performance stats accessed for player: Dhruv
Performance stats accessed for player: Dhruv
New game created with opponent: TSU
Game added to schedule: TSU
Performance stats accessed for player: Dhruv
Added 10 points.
Performance stats accessed for player: Dhruv
Added 10 assists.
Performance stats accessed for player: Dhruv
Added 10 rebounds.
Performance stats accessed for player: Dhruv
Added 10 blocks.
Home score set to 10
Opponent score set to 5
Game result: won
New training session created on 2024-05-19
Training session added.
Training session marked as completed.
Player Dhruv added to training session.

## Phase 4: Task 3

Upon reviewing the UML class diagram and considering potential refactorings, one approach could involve applying the Observer pattern to enhance the interaction between classes. The Observer pattern is particularly useful when there are dependencies between objects, allowing for a loosely coupled design where changes in one object trigger updates in dependent objects.

For example, in the context of the basketball team management system represented by the UML diagram, the Team class could act as the subject (or observable), while other classes such as Game and TrainingSession could act as observers. Whenever there is a change in the Team class, such as adding or removing players, scheduling games, or organizing training sessions, it would notify the registered observers, allowing them to react accordingly.

Additionally, the Composite pattern could be beneficial in organizing hierarchical structures of objects. In the context of the basketball team management system, this pattern could be applied to represent the hierarchy of team components. For instance, the Team class could be composed of sub-components such as players, games, and training sessions, each of which may further contain sub-components. This hierarchical structure simplifies the management of complex relationships between team elements and allows for uniform treatment of individual elements and groups of elements.

By incorporating the Observer and Composite patterns into the design, the basketball team management system can achieve better modularity, flexibility, and scalability. The Observer pattern facilitates communication and synchronization between objects, while the Composite pattern enables the representation of complex structures in a unified manner. Together, these patterns contribute to a more cohesive and maintainable design, better suited to evolving requirements and future enhancements.