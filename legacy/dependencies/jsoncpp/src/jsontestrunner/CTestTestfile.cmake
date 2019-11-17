# CMake generated Testfile for 
# Source directory: /home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner
# Build directory: /home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(jsoncpp_readerwriter "/usr/bin/python2" "-B" "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/../../test/runjsontests.py" "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/jsontestrunner_exe" "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/../../test/data")
set_tests_properties(jsoncpp_readerwriter PROPERTIES  WORKING_DIRECTORY "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/../../test/data" _BACKTRACE_TRIPLES "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/CMakeLists.txt;28;add_test;/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/CMakeLists.txt;0;")
add_test(jsoncpp_readerwriter_json_checker "/usr/bin/python2" "-B" "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/../../test/runjsontests.py" "--with-json-checker" "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/jsontestrunner_exe" "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/../../test/data")
set_tests_properties(jsoncpp_readerwriter_json_checker PROPERTIES  WORKING_DIRECTORY "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/../../test/data" _BACKTRACE_TRIPLES "/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/CMakeLists.txt;32;add_test;/home/drewvlaz/Downloads/jsoncpp-1.9.1/src/jsontestrunner/CMakeLists.txt;0;")
