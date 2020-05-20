//
// Created by sanchir on 3.11.19.
//

#include "Mathematics.h"
#include <iostream>

namespace science{
    void Mathematics::outputInfo(){
        std::cout << name << "-" << definition << std::endl;
        std::cout << "К какой науке относятся :\n" << nameClass << std::endl;
        std::cout << "Логические операции математики : " << logicOperation << std::endl;
    }
}