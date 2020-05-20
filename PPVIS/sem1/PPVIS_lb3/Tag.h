//
// Created by sanchir on 13.12.19.
//

#ifndef PPVIS_LB3_TAG_H
#define PPVIS_LB3_TAG_H

#include <iostream>
#include <vector>
#include "Command.h"
#include "Version.h"
#include "Comments.h"
#include "CDATA.h"

using namespace std;

struct Attribute{
    string name;
    string data;
};

class Tag : public Comments , public CDATA , public Version{
    bool checkOr(Command command , vector<bool> check);

    bool checkText(Parameter command);

    bool checkTag(Parameter command , Attribute attribute);

    bool checkAttribute(Command command);

    string cut(string* element , char symbol , bool inclusionCheck);

    int tagDefinition(string String);

    void searchAttributeForTag(string tag);

public:
    vector<Tag> nestedTag;
    vector<Attribute> attribute;

    int parser(vector<string> tags , int pointer = 0);

    string name;
    string data;

    Tag getTag();

    Tag* search(Command command , vector<Tag>& res);

    void createEmptyTag(string tag);

    void output(int counter = 0);

};




#endif //PPVIS_LB3_TAG_H
