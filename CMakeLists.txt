cmake_minimum_required(VERSION 3.15 FATAL_ERROR)
project(VirtualAssistant)
set(CMAKE_BUILD_TYPE Release)

# Create bin folder for compiled binaries
set(CMAKE_BINARY_DIR ${CMAKE_SOURCE_DIR}/bin)
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_BINARY_DIR})
set(LIBRARY_OUTPUT_PATH ${CMAKE_BINARY_DIR})

# Bring headers into project
include_directories(include)

# Include source files
file(GLOB SOURCES "src/*.cc")

# Include other files to read in 
#add_definitions("-DPATH_TO_JSON=\"${CMAKE_SOURCE_DIR}/include/data.json\"")

# Compiler flags
#target_link_libraries(VirtualAssistant PRIVATE -ljsoncpp)
set(CMAKE_CXX_FLAGS -ljsoncpp)

add_executable(VirtualAssistant ${SOURCES})