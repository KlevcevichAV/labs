cmake_minimum_required(VERSION 3.15)
project(PPVIS_lb3)

set(CMAKE_CXX_STANDARD 14)

add_executable(PPVIS_lb3 main.cpp Command.cpp Command.h Comments.cpp Comments.h CDATA.cpp CDATA.h Tag.cpp Tag.h Version.cpp Version.h Tags.cpp Tags.h XMLDocument.cpp XMLDocument.h Reader.cpp Reader.h)