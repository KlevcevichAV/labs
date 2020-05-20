//
// Created by sanchir on 13.12.19.
//

#include "XMLDocument.h"

XMLDocument::XMLDocument():Reader("/home/sanchir/CLionProjects/xml-example.xml"){
    parser(FILE);
}

XMLDocument::XMLDocument(string path):Reader(path){
    FILE = inputXML((checkFile(path)) ? (path) : ("/home/sanchir/CLionProjects/xml-example.xml"));
    parser(FILE);
}

Tag XMLDocument::command(string commandString){
    Command command;
    if(commandString == "/"){
//            outputTree();
        return getTag();
    }
    command.splitCommand(commandString);
    vector<Tag> temp;
    Tag * result = search(command , temp);
    command.free();
    if(result == NULL){
        result = new Tag;
    }
    result->nestedTag = temp;
    result->name = commandString;
    return *result;
}

void XMLDocument::outputTree(){
    Tag::output();
}