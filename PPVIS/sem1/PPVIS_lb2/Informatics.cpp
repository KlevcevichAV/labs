//
// Created by sanchir on 3.11.19.
//

#include "Informatics.h"
#include <iostream>


namespace science{
    Informatics::Informatics(){
        objectOfStudying = "программами";
        name = "Информатика";
        definition = "наука о методах и процессах сбора, хранения, обработки, передачи, анализа и оценки информации с применением компьютерных технологий, обеспечивающих возможность её использования для принятия решений.";
    }
    void Informatics::outputInfo(){
        std::cout << name << "-" << definition << std::endl;
        std::cout << "К какой науке относятся :\n" << nameClass;
        std::cout << name << " работает с  " << objectOfStudying << std::endl;
    }
}