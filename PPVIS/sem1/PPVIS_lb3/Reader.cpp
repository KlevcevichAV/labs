//
// Created by sanchir on 14.12.19.
//

#include "Reader.h"

string Reader::cut(string* element , char symbol , bool inclusionCheck){
    int pointer = element->find(symbol);
    string result = ((!inclusionCheck) ? element->substr(0 , pointer) : element->substr(0 , pointer + 1));
    (inclusionCheck) ? element->erase(0 , pointer + 1 ) : element->erase(0 , pointer);
    return result;
}

string Reader::deleteSpace(string string1){
    if(string1[string1.length() - 1] == '\r' || string1[string1.length() - 1] == '\n') string1.erase(string1.length() - 1 ,1);
    while (string1[0] == '\t'){
        string1.erase(0 , 1);
    }
    return string1;
}

vector<string> Reader::inputXML(string path){
    ifstream fin(path);
    string string1;
    vector<string> result;


    int counter = 0;
    while (counter == 0){
        vector<string> xml;
        getline(fin , string1);
        string1 = deleteSpace(string1);
        if(string1.find("<[CDATA[") != -1) {
            while (!inputCDATA(string1)){
                result.push_back(string1);
                getline(fin , string1);
                string1 = deleteSpace(string1);
            }
        }
        xml = splittingTag(string1);
        for(int i = 0; i < xml.size(); i++) if(xml[i] != "" && xml[i] != " ") result.push_back(xml[i]);
        counter = checkTagForCounter(string1 , counter);
    }

    while (counter != 0){
        getline(fin , string1);
        vector<string> xml;
        string1 = deleteSpace(string1);
        if(string1.find("<[CDATA[") != -1) {
            while (!inputCDATA(string1)){
                result.push_back(string1);
                getline(fin , string1);
                string1 = deleteSpace(string1);
            }
        }
        xml = splittingTag(string1);
        for(int i = 0; i < xml.size(); i++)
            if(xml[i] != "" && xml[i] != " "){
                result.push_back(xml[i]);
                counter = checkTagForCounter(xml[i] , counter);
            }
        if(fin.eof()){
            cout << "FILE!!!!!!!!!!";
            exit(1);
        }
    }
    fin.close();
    return result;
}

bool Reader::checkFile(string file_name) {
    ifstream file;
    file.open(file_name);
    if(!file){
        cout << "\nCant find this file : " << file_name << endl;
        return false;
    }
    return true;
}

int Reader::checkTagForCounter(string string1 , int counter){
    if(string1[0] != '<') return counter;
    if(string1[string1.length() - 2] == '/') return counter;
    if(string1[1] == '?' || string1[1] == '!') return counter;
    if(string1[1] == '/')counter--; else counter++;
    return counter;
}

bool Reader::inputCDATA(string elementCDATA){
    if(elementCDATA.find("]]>") == -1) return false; else return true;
}

vector<string> Reader::splittingTag(string tag) {
    vector<string> result;
    int pointer = tag.find('>');
    if (pointer == tag.length() - 1) {
        result.push_back(tag);
        return result;
    }
    while (tag.length() != 0) {
        if (tag[0] == '<') {
            result.push_back(cut(&tag, '>', true));
        } else result.push_back(cut(&tag, '<', false));
    }
    return result;
}

Reader::Reader(string path){
    FILE = inputXML(path);
}