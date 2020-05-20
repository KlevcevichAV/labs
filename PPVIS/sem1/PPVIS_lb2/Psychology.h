//
// Created by sanchir on 3.11.19.
//

#ifndef PPVIS_LB2_PSYCHOLOGY_H
#define PPVIS_LB2_PSYCHOLOGY_H

#include "SocialScience.h"
#include "NaturalScience.h"


namespace science{
    class Psychology : private SocialScience , private NaturalScience{
        int counterSuicide;
    public:
        Psychology();
        void outputInfo();
    };

}
#endif //PPVIS_LB2_PSYCHOLOGY_H