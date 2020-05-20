//
// Created by sanchir on 13.12.19.
//

#ifndef PPVIS_LB3_XMLDOCUMENT_H
#define PPVIS_LB3_XMLDOCUMENT_H

#include <fstream>
#include "Tag.h"
#include "Reader.h"

using namespace std;

class XMLDocument : protected Tag , protected Reader{

public:
    XMLDocument();

    XMLDocument(string path);

    Tag command(string commandString);

    void outputTree();

};




#endif //PPVIS_LB3_XMLDOCUMENT_H
