                        Algorithm Study - The Website

      TABLE OF CONTENTS

   I. WEBSITE CONTENTS
  II. MODIFYING THE WEBSITE
 III. GENERATING WEBSITE DOCUMENTS


I.    WEBSITE CONTENTS

      The website directory contains the contents of the Algorithm Study
      website in both its template form and generated form.  The contents of
      each subdirectory are:

         bin - binary files; contains the script to generated the full .html
               files
         contents - the contents of each page in the website
         templates - the templates for the header, footer and menus
         webroot - the full website in its generated form


II.   MODIFYING THE WEBSITE

      The contents of the website are stored in a broken-out template form.
      There is a template for the header, footer and left-menu.  These elements
      are split out into separate files to ease management of the website as
      content changes.

      To modify the contents of a page on the website, the corresponding content
      file under the contents directory should be modified.  To modify the
      header, footer, or left-menu of the website, the appropriate file should
      be modified in the templates directory.

      For documentation on how to use the templating system, see the script at
      bin/buildpages.py.  It includes information on file naming requirements,
      variable definition, variable usage, and if/elsif/else block definition.


III.  GENERATING WEBSITE DOCUMENTS

      As indicated in section II above, the contents of the website are
      maintained as template files for ease of maintenance.  To generate the
      full website, the script 'bin/buildpages.py' must be run from the bin
      directory.  Simply cd into the bin directory and run the command:

         ./buildpages.py


