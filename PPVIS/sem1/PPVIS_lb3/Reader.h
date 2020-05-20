//
// Created by sanchir on 14.12.19.
//

#ifndef PPVIS_LB3_READER_H
#define PPVIS_LB3_READER_H

#include <vector>
#include <fstream>
#include <iostream>
#include <string>

using namespace std;


class Reader {
public:
    vector<string> FILE;

    string cut(string* element , char symbol , bool inclusionCheck);

    string deleteSpace(string string1);

    vector<string> inputXML(string path);

    bool checkFile(string file_name);

    int checkTagForCounter(string string1 , int counter);

    bool inputCDATA(string elementCDATA);

    vector<string> splittingTag(string tag);

    Reader(string path);

};


#endif //PPVIS_LB3_READER_H
