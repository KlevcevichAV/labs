//
// Created by sanchir on 3.11.19.
//

#include "Psychology.h"
#include <iostream>
namespace science{
    Psychology::Psychology(){
        counterSuicide = 0;
        name = "Психология";
        definition = "наука, изучающая закономерности возникновения, развития и функционирования психики и психической деятельности человека и групп людей.";
    }

    void Psychology::outputInfo(){
        std::cout << name << "-" << definition << std::endl;
        std::cout << "К какой науке относятся :\n" << nameClass << std::endl;
        std::cout << "Количество суицидов из-за наук :" << counterSuicide << std::endl;
    }

}