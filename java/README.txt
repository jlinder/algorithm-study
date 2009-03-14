                      Algorithm Study - Java Implementation

      TABLE OF CONTENTS

   I. SUMMARY
  II. COMPILATION
 III. INTEGRATION WITH ECLIPSE


I.    SUMMARY

      This directory holds implementations of algorithms and data structures in
      Java.


II.   COMPILATION

      Maven 2 is used as the build management system for the Java
      implementations.  Prerequisites for building the Java code include:

         - A Java JDK version 1.5 or greater
         - Maven 2

      To compile the source code, run the tests and produce a jar file, run
      the command:

         mvn package

      The jar file will then be located in the target directory.

      To clean the project after building the code, run the command:

         mvn clean


III.  INTEGRATION WITH ECLIPSE

      Maven 2 makes it easy to use Eclipse to browse or write source code.  To
      start browsing the code of or start developing new code for the Algorithm
      Study project using Eclipse, follow these steps:

      1. In the java directory of your Algorithm Study checkout, run the
         command:

            mvn eclipse:eclipse

         This command will generate the necessary .project and .classfile files.

      2. Open the Algorithm Study sandbox directory in your Eclipse Workspace.

      3. Open the workspace's Preferences and:

         a. Navigate to "Java -> Build Path -> Classpath Variables"
         b. Click on "New..."
         c. Enter the Name as "M2_REPO" (without the quotes)
         d. Click on "Folder..."
         e. Choose the directory ~/.m2/repository
         f. Press OK
         g. Press OK

      4. Choose the menu File -> Import... and:

         a. Choose "General -> Existing Projects into Workspace"
         b. Press "Next >"
         c. Make sure the "Select root directory:" radio button is selected and
            click "Browse..."
         d. Choose the Algorithm Study directory and press "Choose"
         e. Select the projects you want to import and press "Finish"

      5. Start browsing / developing.


