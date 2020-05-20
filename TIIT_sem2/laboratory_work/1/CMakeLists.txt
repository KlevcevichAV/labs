cmake_minimum_required(VERSION 3.13)
project(TIIT)

set(CMAKE_CXX_STANDARD 14)
set(SOURCE_LIB fenwick_tree.cpp)
add_executable(TIIT main.cpp)
add_library(fenwick_tree STATIC ${SOURCE_LIB})
target_link_libraries(TIIT fenwick_tree)
