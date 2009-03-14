#!/usr/bin/python

#
# buildpages.py - Builds static HTML pages from contents and templates.
#
# Copyright (C) 2009 The Algorithm Study Project
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# version 2.0 as published by the Free Software Foundation.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License version
# 2.0 along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
# or find it on the GNU website at:
#
#    http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
#


#
# Description:
#
#   This script employs a simple templating system where a standard templating
#   system is used to generate static HTML content.  The content of each page is
#   stored separately from the header, footer and menu; this script then
#   combines each content page with the standard header, footer and menu to
#   create a consistent website look and feel.
#
#
# File Types and Locations:
#
#   All files have extensions of '.html'.  The type of file is differentiated
#   by the directory in which they preside:
#
#      ../templates - template files
#      ../contents - page content files
#      ../web - where the generated pages are deposited
#
#   The template files are as follows:
#
#      header.html - the page header file
#      footer.html - the page footer file
#      left-menu.html - the menu on the left side of the page
#
#   The files in the contents directory can have any name but must be of the
#   form <NAME>.html where <NAME> is the name of the file.  Sub-directories
#   under contents is allowed.  Any sub-directory structure is maintained
#   when the static html pages are generated; i.e., the generated static file
#   for the content file in the first line that follows will end up in the
#   location in the second line:
#
#      ../contents/subdir/index.html
#      ../web/subdir/index.html
#
#
# Static Page Generation Process:
#
#   When this program is run, it does the following for each file under the
#   contents directory:
#
#      1. The content file is read in and parsed.  Any variable name/value pairs
#         are captured and the remaining content is buffered as the content
#         text.  The name of the file is noted for use as the target file name.
#
#      2. The header.html file is read in and parsed.  Variable and if/else
#         blocks are evaluated.  The resultant text is then written to the
#         target output file.
#
#      3. The left-menu.html file is read in and parsed.  Variable and if/else
#         blocks are evaluated.  The resultant text is then written to the
#         target output file.
#
#      4. The opening div tag for the contents are written to the target output
#         file.  The tag is of the following form:
#
#            <div id="contents">
#
#      5. The contents from the contents file are writen to the target
#         output file.
#
#      6. The closing div tag for the contents is written to the target
#         output file.  The tag is simply as follows:
#
#            </div>
#
#      7. The footer.html file is read in and parsed.  Variable and if/else
#         blocks are evaluated.  The resultant text is then written to the
#         target output file.
#
#
# File Syntax:
#
#   Nearly everything that is put into all the files (both the content files
#   and the template files) must be HTML.  The only exceptions are as follows:
#
#   1. Content files can contain variable name/value pairs.
#
#      The format for defining a variable is:
#
#         <%var Name="Value"%>
#
#      All variable name/value pairs must be at the top of a given content file.
#      The first line that is not blank and does not start with '<%' is
#      considered the start of the content.
#
#   2. Template files can contain if/else clauses and variable replacement.
#
#      The format for defining a variable replacement is:
#
#         <%= Name %>
#
#      The format for defining a an if/elsif/else block is:
#
#         <%if name="value" %>
#            HTML code
#         <%endif %>
#
#      or
#
#         <%if name="value" %>
#            HTML code
#         <%else %>
#            HTML code
#         <%endif %>
#
#      or
#
#         <%if name="value" %>
#            HTML code
#         <%elsif name="value" %>
#            HTML code
#         <%else %>
#            HTML code
#         <%endif %>
#
#      only the HTML code from an if, elsif or else block that evaluates to true
#      and for which a previous block in the if/elsif/else list has not
#      evaluated to true is kept in the final generated file.
#
# BuiltIn Variables:
#
#   ContentFileName
#
#      The name of the contents file minus the '.html'.  For example, 'index'
#      for 'index.html'.
#
#   ContentFileDir
#
#      The directory under contents in which the contents file is located.
#      Examples:
#
#         for './index.html', ContentFilterDir = './'.
#         for './dir/index.html', ContentFilterDir = './dir'.
#
#   WebFileDir
#
#      The directory where the generated .html files are deposited.
#

import os

def read_file(file_name, buffer) :
    '''Reads the contents of the specified file into the buffer.'''

    f = open(file_name)
    for line in f :
        buffer.append(line)

    f.close()


def write_file(dir, file_name, buffer) :
    '''Writes the contents of the buffer to the specified file.'''

    if (not os.path.exists(dir)) :
        os.makedirs(dir)

    f = open(os.path.join(dir, file_name), 'w')
    for line in buffer :
        f.write(line)

    f.close()


def evaluate_template(text_in, buffer, vars, template_name) :
    '''Evaluate the template text and place resultant text into the buffer.'''

    # Possible states:  save_txt, =, if, elsif, else, endif
    state = 'save_txt'
    state_stack = []

    save_text = True
    save_text_stack = []

    in_if_stmt = False
    in_if_stmt_stack = []

    evaled_true_already = False
    evaled_true_already_stack = []

    index = 0
    line_number = 0
    for line in text_in :
        line_number = line_number + 1

        while (True) :
            if ('save_txt' == state) :
                stmt_start = find_stmt_start(line, index)
                if (not stmt_start) :
                    if (save_text) :
                        buffer.append(line[index:])
                    break

                if (save_text) :
                    buffer.append(line[index:stmt_start[0]])

                index = stmt_start[0]
                state_stack.append(state)
                state = stmt_start[1]

                # Verify valid state transitions
                if ('=' == state) :
                    continue

                if (not in_if_stmt and \
                        ('elsif' == state or \
                            'else' == state or \
                            'endif' == state)) :
                    print 'Invalid statement location.  ' + state + ' must ' \
                            'come after an if statement.  line=[' \
                            + str(line_number) + '], template_file=[' \
                            + template_name + ']'
                    return False

            elif ('=' == state) :
                var_ret = variable_substitution(line, index, buffer, vars)
                if (-1 == var_ret[0]) :
                    print var_ret[1] + '  line=[' + str(line_number) \
                            + '], template_file=[' + template_name + ']'
                    return False

                index = var_ret[0]
                state = state_stack.pop()

            elif ('if' == state) :
                if_ret = evaluate_if(line, index, vars)
                if (if_ret[0] < 0) :
                    print if_ret[1] + '  line=[' + str(line_number) \
                            + '], template_file=[' + template_name + ']'
                    return False

                index = if_ret[0]

                # Is this a block which is being saved?
                save_text_stack.append(save_text)
                evaled_true_already_stack.append(evaled_true_already)
                if (save_text) :
                    save_text = if_ret[1]
                    evaled_true_already = if_ret[1]
                else :
                    save_text = False
                    evaled_true_already = True

                in_if_stmt_stack.append(in_if_stmt)
                in_if_stmt = True

                state = 'save_txt'

            elif ('elsif' == state) :
                elsif_ret = evaluate_elsif(line, index, vars)
                if (elsif_ret[0] < 0) :
                    print elsif_ret[1] + '  line=[' + str(line_number) \
                            + '], template_file=[' + template_name + ']'
                    return False

                index = elsif_ret[0]

                # Is this a block which is being saved?
                if (save_text_stack[-1]) :
                    if (evaled_true_already) :
                        save_text = False
                    else :
                        save_text = elsif_ret[1]
                        evaled_true_already = elsif_ret[1]
                else :
                    save_text = False
                    evaled_true_already = True

                state = 'save_txt'

            elif ('else' == state) :
                else_ret = evaluate_else(line, index)
                if (else_ret[0] < 0) :
                    print else_ret[1] + '  line=[' + str(line_number) \
                            + '], template_file=[' + template_name + ']'
                    return False

                index = else_ret[0]

                # Is this a block which is being saved?
                if (save_text_stack[-1]) :
                    if (evaled_true_already) :
                        save_text = False
                    else :
                        save_text = else_ret[1]
                        evaled_true_already = else_ret[1]
                else :
                    save_text = False
                    evaled_true_already = True

                state = 'save_txt'

            elif ('endif' == state) :
                endif_ret = evaluate_endif(line, index)
                if (endif_ret[0] < 0) :
                    print endif_ret[1] + '  line=[' + str(line_number) \
                            + '], template_file=[' + template_name + ']'
                    return False

                index = endif_ret[0]

                save_text = save_text_stack.pop()
                evaled_true_already = evaled_true_already_stack.pop()
                in_if_stmt = in_if_stmt_stack.pop()

                state = 'save_txt'

        # Prep for next line
        index = 0

    return True


def evaluate_endif(line, index) :
    '''Evaluates an endif statement.  A tuple is returned.  The first element is
    the index of the first character after the end of the endif statement or an
    error code that is less than zero if an error occured.  The second element
    is a message if an error occurred or True if no error occurred.'''

    # Get the position of the end of the statement
    stmt_end = line.find('%>', index + 7)
    if (0 > stmt_end) :
        return (-1, 'Syntax Error:  End of endif statement not found on this ' \
                'line.  endif statements can not span multiple lines.')

    line = line[index + 7:stmt_end].strip()
    if (0 != len(line)) :
        return (-1, 'Syntax Error:  endif statement can not contain any ' \
                + 'parameters.')

    return (stmt_end + 2, True)


def evaluate_else(line, index) :
    '''Evaluates an else statement.  A tuple is returned.  The first element is
    the index of the first character after the end of the else statement or an
    error code that is less than zero if an error occured.  The second element
    is a message if an error occurred or True if no error occurred.'''

    # Get the position of the end of the statement
    stmt_end = line.find('%>', index + 6)
    if (0 > stmt_end) :
        return (-1, 'Syntax Error:  End of else statement not found on this ' \
                'line.  else statements can not span multiple lines.')

    line = line[index + 6:stmt_end].strip()
    if (0 != len(line)) :
        return (-1, 'Syntax Error:  else statement can not contain any ' \
                + 'parameters.')

    return (stmt_end + 2, True)


def evaluate_elsif(line, index, vars) :
    '''Evaluates an elsif statement.  A tuple is returned.  The first element is
    the index of the first character after the end of the elsif statement or an
    error code that is less than zero if an error occured.  The second element
    is a message if an error occurred, True if the statement evaluates to true,
    or False if the statement evaluates to false.'''

    # Get the position of the end of the statement
    stmt_end = line.find('%>', index + 7)
    if (0 > stmt_end) :
        return (-1, 'Syntax Error:  End of elsif statement not found on this ' \
                'line.  elsif statements can not span multiple lines.')

    line = line[index + 7:stmt_end].strip()
    expr_eval = evaluate_if_elsif_expression(line, vars, 'elsif')
    if (len(expr_eval) == 1) :
        return (stmt_end + 2, expr_eval[0])

    return expr_eval


def evaluate_if(line, index, vars) :
    '''Evaluates an if statement.  A tuple is returned.  The first element is
    the index of the first character after the end of the if statement or an
    error code that is less than zero if an error occured.  The second element a    message if an error occurred, True if the statement evaluates to true, False
    if the statement evaluates to false.'''

    # Get the position of the end of the if statement
    stmt_end = line.find('%>', index + 4)
    if (0 > stmt_end) :
        return (-1, 'Syntax Error:  End of if statement not found on this ' \
                'line.  If statements can not span multiple lines.')

    line = line[index + 4:stmt_end].strip()
    expr_eval = evaluate_if_elsif_expression(line, vars, 'if')
    if (len(expr_eval) == 1) :
        return (stmt_end + 2, expr_eval[0])

    return expr_eval


def evaluate_if_elsif_expression(expression, vars, stmt_type) :
    '''Evaluate the expression in an if or an elsif statement. A tuple is
    returned.  If it is length 1, the first element is True or False and
    indicates a successful evaluation of the expression.  If it is length 2,
    the first element is an error code which is less than 0 and the second
    element is a message about the error.'''

    # Get the variable name
    index_equals = expression.find('=')
    if (1 > index_equals) :
        return (-1, 'Syntax Error:  No variable name found in ' + stmt_type \
                + ' statement.  A variable name/value pair is required.')

    name = expression[:index_equals].strip()

    # Get the value of the variable
    expression = expression[index_equals + 1:].strip()
    index_quote1 = expression.find('"')
    if (0 > index_quote1) :
        return (-1, 'Syntax Error:  Expected double quote to delineate value ' \
                'in an ' + stmt_type + ' statement.')

    index_quote2 = expression.find('"', index_quote1 + 1)
    if (index_quote1 >= index_quote2) :
        return (-1, 'Syntax Error:  Expected ending double quote to delineate' \
                ' value in an ' + stmt_type + ' statement.')

    value_to_test = expression[index_quote1 + 1: index_quote2]

    # Evaluate the value
    value_set = ''
    try :
        value_set = vars[name]
    except KeyError:
        print 'Warning:  Variable name "' + name + '" not found when ' \
                + 'processing content file ' \
                + os.path.join(vars['ContentFileDir'], vars['ContentFileName'])\
                + '.  Found in ' + stmt_type + ' statement.'
        return (False, )

    if (value_to_test == value_set) :
        return (True, )

    return (False, )


def variable_substitution(line, stmt_index, buffer, vars) :
    '''Substitutes the value of the indicated variable into the output buffer.
    If the variable expression is not closed, a tuple of (-1, Message) is
    returned where Message is an error message and -1 indicates a hard stop
    should be made to the processing of this content file.  If the indicated
    variable is not found, a warning is printed and the index of the character
    after the the end of the statement is returned.  If everything goes well,
    the character after the end of the statement is returned.'''

    close_index = line.find('%>', stmt_index)
    if (0 > close_index) :
        return (-1, 'Syntax Error:  Variable statement not closed.')

    name = line[stmt_index + 3:close_index].strip()
    try :
        val = vars[name]
        buffer.append(val)
    except KeyError:
        print 'Warning:  Variable name "' + name + '" not found when ' \
                + 'processing content file ' \
                + os.path.join(vars['ContentFileDir'], vars['ContentFileName'])

    return (close_index + 2, '')


def find_stmt_start(line, index) :
    '''Finds the start of a statement.  If no statement was found, False is
    returned.  If a statement is found, the index of the start of the statement
    and the type of statement are returned as a tuple.'''

    stmt_index = line.find('<%', index)
    if (0 > stmt_index) :
        return False

    if (line[stmt_index:stmt_index + 3] == '<%=') :
        return (stmt_index, '=')

    if (line[stmt_index:stmt_index + 4] == '<%if') :
        return (stmt_index, 'if')

    if (line[stmt_index:stmt_index + 7] == '<%elsif') :
        return (stmt_index, 'elsif')

    if (line[stmt_index:stmt_index + 6] == '<%else') :
        return (stmt_index, 'else')

    if (line[stmt_index:stmt_index + 7] == '<%endif') :
        return (stmt_index, 'endif')


def evaluate_contents(text_in, buffer, variable_map) :
    '''Evaluates the text read in from a content file, place the contents text
    into the buffer, and place any variable name/value pairs into the
    variable_map.'''

    vm = variable_map

    # Extract the variables from the top of the file
    count = 0
    errors = False
    for full_line in text_in :
        line = full_line.strip()
        if (len(line) == 0) :
            count = count + 1
            continue

        if (not line.startswith('<%')) :
            break

        pair = parse_variable(line)
        if (not pair) :
            print_invalid_content_statement( \
                    variable_map['ContentFileDir'], \
                    variable_map['ContentFileName'], \
                    (count + 1), full_line)
            errors = True
            continue

        vm[pair[0]] = pair[1]
        count = count + 1

    # Append the rest of the lines to the buffer
    text_in = text_in[count:]
    for line in text_in :
        buffer.append(line)


def parse_variable(line) :
    '''Parses a line of text expected to contain a variable name/value pair
    from the contents file.  If the line processes as valid, a tuple containing
    the (Name, Value) is returned.  Otherwise, False is returned. '''

    if (not line.startswith('<%var ')) :
        return False

    line = line[6:].lstrip()

    equals = line.find('=')
    if (0 > equals) :
        return False

    name = line[:equals]
    line = line[equals + 1:].lstrip()

    quote = line.find('"')
    if (0 > quote):
        return False

    quote2 = line.find('"', quote + 1)
    if (0 > quote2) :
        return False

    value = line[quote + 1: quote2]
    line = line[quote2 + 1:].lstrip()

    if ('%>' != line) :
        return False

    return (name, value)


def print_invalid_content_statement(file_dir, file_name, line_number, line) :
    print 'Invalid statement (content_file=[' \
            + os.path.join(file_dir, file_name) \
            + '], line_number=[' + line_number + '], statement=[' + line + ']'


def find_contents_files(base_dir, file_names) :
    '''Searches the directory tree rooted at base_dir for contents files and
    adds them to the file_names list.'''

    for (dir, subdirs, files) in os.walk(base_dir) :
        for file in files :
            if (file.endswith('.html')) :
                file_names.append((dir, file))


def main(g_vars) :
    contents_dir = g_vars['ContentsDir']
    templates_dir = g_vars['TemplatesDir']
    web_dir = g_vars['WebDir']

    # Read in header, footer, and left menu files
    header_lines = []
    footer_lines = []
    left_menu_lines = []
    read_file(os.path.join(templates_dir, 'header.html'), header_lines)
    read_file(os.path.join(templates_dir, 'footer.html'), footer_lines)
    read_file(os.path.join(templates_dir, 'left-menu.html'), left_menu_lines)

    # Get the list of content files
    content_files = []
    find_contents_files(contents_dir, content_files)

    # Process the content files
    for dir, filename in content_files :
        vars = { }
        vars['ContentFileName'] = filename[:-5]
        vars['ContentFileDir'] = './' + dir[len(contents_dir):]
        vars['WebFileDir'] = web_dir + dir[len(contents_dir):]

        # Load the file
        buf_raw_contents = []
        read_file(os.path.join(dir, filename), buf_raw_contents)

        #print 'read file: ' + vars['ContentFileDir'] + ' ... ' \
        #        + vars['ContentFileName']

        # Extract the variables and text
        buf_contents = []
        evaluate_contents(buf_raw_contents, buf_contents, vars)

        # Evaluate the templates for this page
        buf_out = []
        success = evaluate_template(header_lines, buf_out, vars, 'header')
        if (not success) :
            return

        success = evaluate_template(left_menu_lines, buf_out, vars, 'left-menu')
        if (not success) :
            return

        buf_out.append('<div id="contents">\n')

        for line in buf_contents :
            buf_out.append(line)

        buf_out.append('</div>\n')

        success = evaluate_template(footer_lines, buf_out, vars, 'footer')
        if (not success) :
            return

        # Write the file to its final location
        write_file(vars['WebFileDir'], vars['ContentFileName'] + '.html', \
                buf_out)

       # print ('-' * 60)
       # print 'The File:'
       # print ''.join(buf_raw_contents)
       # print '    ' + ('-' * 20)
       # print ''

       # print 'Just The Contents:'
       # print ''.join(buf_contents)
       # print '    ' + ('-' * 20)
       # print ''
       # for k, v in vars.iteritems():
       #     print k + '=[' + v + ']'
       # print ''


if __name__ == "__main__" :
    g_vars = { }
    g_vars['ContentsDir'] = '../contents/'
    g_vars['ResourcesDir'] = '../resources/'
    g_vars['TemplatesDir'] = '../templates/'
    g_vars['WebDir'] = '../webroot/'

    main(g_vars)


#vi set ts=4
#vi set shiftwidth=4
