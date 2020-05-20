//
// Created by sanchir on 13.12.19.
//

#ifndef PPVIS_LB3_COMMAND_H
#define PPVIS_LB3_COMMAND_H

#include <iostream>
#include <vector>

using namespace std;



struct Parameter{
    string name;
    string data;
    string operation;
    bool inverse;
    Parameter(){
        inverse = false;
    }
};

class Command{
public:
    string nameTag;
    Command* nestedCommand;
    vector<Parameter> parameters;

    string cut(string* element , char symbol , bool inclusionCheck);

    Command();

    void splitCommand(string command);

    void searchAttributeForCommand(string command);

    void free();

    void output();
};




#endif //PPVIS_LB3_COMMAND_H
