# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.16

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /snap/clion/112/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /snap/clion/112/bin/cmake/linux/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/sanchir/CLionProjects/MRZVIS_2

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/MRZVIS_2.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/MRZVIS_2.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/MRZVIS_2.dir/flags.make

CMakeFiles/MRZVIS_2.dir/main.cpp.o: CMakeFiles/MRZVIS_2.dir/flags.make
CMakeFiles/MRZVIS_2.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/MRZVIS_2.dir/main.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/MRZVIS_2.dir/main.cpp.o -c /home/sanchir/CLionProjects/MRZVIS_2/main.cpp

CMakeFiles/MRZVIS_2.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/MRZVIS_2.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/sanchir/CLionProjects/MRZVIS_2/main.cpp > CMakeFiles/MRZVIS_2.dir/main.cpp.i

CMakeFiles/MRZVIS_2.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/MRZVIS_2.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/sanchir/CLionProjects/MRZVIS_2/main.cpp -o CMakeFiles/MRZVIS_2.dir/main.cpp.s

# Object files for target MRZVIS_2
MRZVIS_2_OBJECTS = \
"CMakeFiles/MRZVIS_2.dir/main.cpp.o"

# External object files for target MRZVIS_2
MRZVIS_2_EXTERNAL_OBJECTS =

MRZVIS_2: CMakeFiles/MRZVIS_2.dir/main.cpp.o
MRZVIS_2: CMakeFiles/MRZVIS_2.dir/build.make
MRZVIS_2: CMakeFiles/MRZVIS_2.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable MRZVIS_2"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/MRZVIS_2.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/MRZVIS_2.dir/build: MRZVIS_2

.PHONY : CMakeFiles/MRZVIS_2.dir/build

CMakeFiles/MRZVIS_2.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/MRZVIS_2.dir/cmake_clean.cmake
.PHONY : CMakeFiles/MRZVIS_2.dir/clean

CMakeFiles/MRZVIS_2.dir/depend:
	cd /home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/sanchir/CLionProjects/MRZVIS_2 /home/sanchir/CLionProjects/MRZVIS_2 /home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug /home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug /home/sanchir/CLionProjects/MRZVIS_2/cmake-build-debug/CMakeFiles/MRZVIS_2.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/MRZVIS_2.dir/depend

