#SaveableUndoableBreaout
This is a project from Fall 21 P532 Software Engineering. In groups, we had to take existing Atari Breakout clones from previous weeks' projects and add two key features: save/load and changeable layouts for control panel buttons. I was responsible for the save/load portion of this task. Skills I developed during this project include reading others' code, creating solutions based on the current implementation with a deadline in mind, and concurrent development using branches and merging. We utilized the composite pattern in our saving and loading as well as our changeable layout.




# P532 Assignment 3 Overview
## By: Abhishek, Isaiah and Snehal

Assignment 3 consists of building the same Breakout game as in Assignment 2. Additionally, we have to implement new features: Change Layout/Pause/Undo/Replay/Save/Load buttons.

Add the json-simple-1.1.1.jar from project dir in your classpath to compile the project

# Instructions To Run
Built using the Eclipse IDE version 2021-16. Written in Java8 and JavaFX.

1. For Eclipse 2021-16 to run properly install JDK/JRE 11 or higher.
    * A download can be found here: https://www.oracle.com/java/technologies/javase-jre11-downloads.html
2. For OpenJDK8 and JavaFX you need Azul's OpenJDK build. OpenJDK8 does not have native JavaFX support and we couldn't find a JavaFX build that worked with OpenJDK8. All JavaFX builds are created with compilers greater than what is compatible with OpenJDK8.
    * Download and install/unzip Zulu OpenJDK8 from here: https://www.azul.com/downloads/?package=jdk#download-openjdk
      * This build of OpenJDK8 does include JavaFX. (Praise the lord). 
3. In Eclipse we have to manually set up Zulu OpenJDK8. To do this go to Preferences->Java->Installed JREs. 
    1. Click the "Add" button. 
    2. Select "Standard VM." 
    3. Using the "JRE Home" field navigate to where you installed or unzipped Zulu OpenJDK8. 
    4. Enter an appropriate name for the JRE. "Zulu OpenJDK8'' is a solid name.
    5. Hit "Finish" to finish.
4. After adding the JRE, ensure it is the selected one if your list has more than one. Hit "Apply and Close" to finish.
   * This will default all projects to use this JRE. A user may not want this but since this is the requirement for this class it is a good idea.
5. Eclipse will warn us about compiler levels not being the same as the OpenJDK8 version. To remove this warning, go to Preferences->Java->Compilers. Change the "Compiler Compliance Level" to 1.8, this is the version needed for OpenJDK8. Although having a higher version shouldn't be an issue. We've tested the project with 16 set and it still compiled, ran, and functioned properly.
7. Lastly, you'll need to import this project into your workspace. We assume you at least know how to do that. =)

Since this build of OpenJDK8 includes JavaFX, no additional set up is needed to get it working. (Which is very nice =D)


