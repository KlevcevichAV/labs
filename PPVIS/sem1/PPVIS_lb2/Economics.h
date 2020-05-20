//
// Created by sanchir on 3.11.19.
//

#ifndef PPVIS_LB2_ECONOMICS_H
#define PPVIS_LB2_ECONOMICS_H

#include "SocialScience.h"
namespace science{

    class Economics : private SocialScience{
        double amountOfMoney;
    public:
        Economics();
        void outputInfo();
    };
}

#endif //PPVIS_LB2_ECONOMICS_H