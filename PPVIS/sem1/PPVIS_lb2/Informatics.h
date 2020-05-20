//
// Created by sanchir on 18.11.19.
//

#ifndef PPVIS_LB2_INFORMATICS_H
#define PPVIS_LB2_INFORMATICS_H

#include "TechnicalScience.h"
namespace science{
    class Informatics : private TechnicalScience{
        std::string objectOfStudying;
    public:
        Informatics();
        void outputInfo();
    };
}

#endif //PPVIS_LB2_INFORMATICS_H
