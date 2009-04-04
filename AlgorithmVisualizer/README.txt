                              Algorithm Visualizer

      TABLE OF CONTENTS

   I. PROJECT SUMMARY
  II. PREPARING YOUR SYSTEM
 III. RUNNING
  IV. COMPILING
   V. INTEGRATION WITH ECLIPSE


I.    PROJECT SUMMARY

      The Algorithm Visualizer is software that shows a variety of algorithms
      in a visual representation as they do their work.  The hope is that these
      visual representations provide users with a better sense and deeper
      understanding of how the algorithms work.

      Algorithm Visualizer is written using GWT, the Google Web Toolkit.

      The latest release is accessible online at:

         http://www.algorithmstudy.com/visualizer


II.   PREPARING YOUR SYSTEM

      As the Algorithm Visualizer uses GWT, a recent version of GWT must be
      downloaded and installed.  In this document, the installation path used
      is an OS X path:

          /Users/YourUserName/java/gwt-mac-1.5.3

       A corresponding path for a windows installation could be:

          C:\java\gwt-windows-1.5.3

       and for Linux could be:

          /home/YourUserName/java/gwt-linux-1.5.3

       Once installed, the environment variable GWT_HOME must be set.  On Linux
       or OS X, this can be done in your ~/.bashrc, ~/.bash_profile or
       ~/.profile file (or the corresponding file for the shell you are using)
       like so:

          export GWT_HOME=/Users/YourUserName/java/gwt-mac-1.5.3

        On Windows, the environment variable would be set by:

           1. Open a Windows Explorer window
           2. Right click on My Computer
           3. Choose 'Properties'
           4. Select the 'Advanced' tab
           5. Click on 'Environment Variables'
           6. Create a new variable
           7. Set the name of the variable to:  GWT_HOME
           8. Set the value to the path to the directory where GWT is installed


III.  RUNNING

      On the command line:

         1. Navigate to the algstudy/AlgorithmVisualizer directory
         2. Run the command:  ./AlgorithmVisualizer-shell
         3. The Algorithm Visualizer program should start up in the hosted mode
            browser.


IV.   COMPILING

      On the command line:

         1. Navigate to the algstudy/AlgorithmVisualizer directory
         2. Run the command:  ./AlgorithmVisualizer-compile
         3. The resultant html, js, css and other files will be deposited in the
            directory:  ./www/com.algorithmstudy.visualizer.AlgorithmVisualizer/
         4. With a web browser, navigate to that directory and open the file
            "AlgorithmVisualizer.html".


V.    INTEGRATION WITH ECLIPSE

      On the command line:

         1. Navigate to the algstudy/AlgorithmVisualizer directory
         2. Run the command:

              $GWT_HOME/projectCreator -eclipse AlgorithmVisualizer

            A directory (test) and two files (.project and .classpath) will be
            created.
         3. Run the command:

              $GWT_HOME/projectCreator -eclipse AlgorithmVisualizer -ignore \
                    com.algorithmstudy.visualizer.client.AlgorithmVisualizer

            One file will be created (AlgorithmVisualizer.launch) and many other
            files will be skipped.
         4. Open Eclipse with the workspace as the algstudy directory.
         5. Choose the menu option 'File -> Import...'
         6. Select the option 'General -> Existing Projects in Workspace' and
            click 'Next'
         7. Click the 'Browse' button to the right of 'Select root directory:'
         8. Browse the same algstudy directory and click 'Choose'
         9. AlgorithmVisualizer should now be listed in the 'Projects:' box with
            the checkbox next to it checked.  Press 'Finish'.

      The AlgorithmVisualizer project should now be listed in the 'Project
      Explorer'.  You can now edit the project files just like any other
      project.  Additionally, choosing to run the project will start the project
      just as it would be started as described in section 'III. Running' above.
      In fact, you can also choose to debug the project and use Eclipse's
      debugger to step through the code just like any other project.


