//
// Created by sanchir on 13.12.19.
//

#ifndef PPVIS_LB3_CDATA_H
#define PPVIS_LB3_CDATA_H

#include <iostream>
#include <vector>

using namespace std;

class CDATA{
    int checkingForEqualNumberOfBrackets(string String , char symbol1 , char symbol2 , int result = 0);

public:
    string cdata;

    int addCDATA(string beginCDATA , vector<string> tempCDATA , int pointer);
};




#endif //PPVIS_LB3_CDATA_H
