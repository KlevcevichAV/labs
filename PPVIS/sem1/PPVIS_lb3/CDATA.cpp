//
// Created by sanchir on 13.12.19.
//

#include "CDATA.h"

int CDATA::checkingForEqualNumberOfBrackets(string String , char symbol1 , char symbol2 , int result){
    for(int i = 0; i < String.length(); i++){
        if(String[i] == symbol1) result++;
        if(String[i] == symbol2) result--;
    }
    return result;
}

int CDATA::addCDATA(string beginCDATA , vector<string> tempCDATA , int pointer){
    string result = beginCDATA;
    int counter = checkingForEqualNumberOfBrackets(beginCDATA , '[' , ']');
    while(counter != 0){
        counter = checkingForEqualNumberOfBrackets(tempCDATA[pointer] , '[' , ']' , counter);
        result += '\n' + tempCDATA[pointer++];
    }
    cdata += result + '\n';
    return pointer;
}