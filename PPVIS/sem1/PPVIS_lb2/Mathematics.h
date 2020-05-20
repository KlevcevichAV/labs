//
// Created by sanchir on 3.11.19. гради путч основы ооп
//

#ifndef PPVIS_LB2_MATHEMATICS_H
#define PPVIS_LB2_MATHEMATICS_H

#include "NaturalScience.h"

namespace science{
class Mathematics:  Science{
        std::string logicOperation;
    public:
    Mathematics(int q):Science(q){
        name = "Математика";
        definition = "наука об отношениях между объектами, о которых ничего не известно, кроме описывающих их некоторых свойств, — именно тех, которые в качестве аксиом положены в основание той или иной математической теории.";
        logicOperation = "<,>,=,!=,!,>=,<=";
    }
        void outputInfo();
    };
}

#endif //PPVIS_LB2_MATHEMATICS_H