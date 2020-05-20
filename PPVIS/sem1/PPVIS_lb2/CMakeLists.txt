cmake_minimum_required(VERSION 3.15)
project(PPVIS_lb2)

set(CMAKE_CXX_STANDARD 14)

add_executable(PPVIS_lb2 main.cpp Science.cpp Science.h SocialScience.cpp SocialScience.h TechnicalScience.cpp TechnicalScience.h NaturalScience.cpp NaturalScience.h Psychology.cpp Psychology.h Informatics.cpp Informatics.h Economics.cpp Economics.h Mathematics.cpp Mathematics.h)