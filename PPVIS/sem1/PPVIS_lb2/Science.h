//
// Created by sanchir on 3.11.19.
//

#ifndef PPVIS_LB2_SCIENCE_H
#define PPVIS_LB2_SCIENCE_H

#include <iostream>

class Science{
protected:
    std::string definition;
    std::string name;
    std::string nameClass;
    virtual void outputInfo() = 0;

public:
    Science(){
        std::cout << "...";
    }
    Science(int f){
        std::cout << f;
    }
};

#endif //PPVIS_LB2_SCIENCE_H