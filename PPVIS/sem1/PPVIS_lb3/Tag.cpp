//
// Created by sanchir on 13.12.19.
//

#include "Tag.h"

bool Tag::checkOr(Command command , vector<bool> check){
    while(command.parameters.size() != 1){
        if(command.parameters[command.parameters.size() - 2].operation != "||")return true;
        if(check[check.size() - 2])return false;
        command.parameters.pop_back();
        check.pop_back();
    }
    return true;
}

bool Tag::checkText(Parameter command){
    bool result = false;
    if(command.name == "text"){
        if(data == command.data){
            result = true;
        }
        if(command.inverse) result = !result;
    }
    return result;
}

bool Tag::checkTag(Parameter command , Attribute attribute){
    bool result = false;
    if(attribute.name == command.name){
        if(attribute.data == command.data){
            result = true;
        }
        if(command.inverse) result = !result;
    }
    return result;
}

bool Tag::checkAttribute(Command command){
    if(command.parameters.size() == 0)return false;
    vector<bool> check;
    for(int i = 0; i < command.parameters.size(); i++){
        for(int j = 0; j < attribute.size(); j++){
            if(checkTag(command.parameters[i] , attribute[j])){
                check.push_back(true);
                break;
            }
            if(checkText(command.parameters[i])){
                check.push_back(true);
                break;
            }
        }
        if(check.size() != i + 1)check.push_back(false);
        if(!check.back()){
            if(command.parameters[i].operation != "||")
                if(i == 0)return true; else{
                    if(checkOr(command , check)) return true;
                }
        }
    }
    return false;
}

string Tag::cut(string* element , char symbol , bool inclusionCheck){
    int pointer = element->find(symbol);
    string result = ((!inclusionCheck) ? element->substr(0 , pointer) : element->substr(0 , pointer + 1));
    (inclusionCheck) ? element->erase(0 , pointer + 1 ) : element->erase(0 , pointer);
    return result;
}

int Tag::tagDefinition(string String){
    if(String[0] != '<') return -1;
    if(String[String.length() - 2] == '/') return 0;
    if(String.find("<[CDATA[") != -1) return 1;
    if(String[1] == '!') return 2;
    if(String[1] == '?') return 3;
    if(String[1] == '/') return 4;
    return 5;
}

void Tag::searchAttributeForTag(string tag){
    if(tag.find(' ') == -1){
        return;
    }
    cut(&tag , ' ' , true);
    do{
        Attribute temp;
        temp.name = cut(&tag , '=' , false);
        tag.erase(0 , 1);
        if(tag.find(' ') == -1){
            temp.data = cut(&tag , '>' , false);
        }else{
            temp.data = cut(&tag , ' ' , false);
        }
        temp.data.erase(0 , 1);
        temp.data.erase(temp.data.length() - 1 , 1);
        attribute.push_back(temp);
    }while (tag.find(' ') != -1);
}

int Tag::parser(vector<string> tags , int pointer){
    while (true){
        int check(tagDefinition(tags[pointer]));
        switch (check){
            case -1:{
                data += tags[pointer++];
                break;
            }
            case 0:{
                createEmptyTag(tags[pointer++]);
                break;
            }
            case 1:{
                pointer = addCDATA(tags[pointer] , tags , pointer + 1);
                pointer++;
                break;
            }
            case 2:{
                addComment(tags[pointer++]);
                break;
            }
            case 3:{
                setVersion(tags[pointer++]);
                break;
            }
            case 4:{
                string check = tags[pointer];
                check.erase(0 , 2);
                check.erase(check.length() - 1 , 1);

                if(check != this->name){
                    cout << "FILE !!!!";
                    exit(1);
                }
                return pointer;
            }
            case 5:{
                if(name == ""){
                    name = tags[pointer].substr(1 , (tags[pointer].find(' ') == -1) ? tags[pointer].find('>') - 1 : tags[pointer].find(' ') - 1);
                    searchAttributeForTag(tags[pointer]);
                }else{
                    Tag temp;
                    pointer = temp.parser(tags , pointer);
                    nestedTag.push_back(temp);
                }
                pointer++;
                break;
            }
        }
    }
}

Tag Tag::getTag(){
    return *this;
}

Tag* Tag::search(Command command , vector<Tag>& res){
    Tag * result = new Tag;
    if(name == command.nameTag){
        if(checkAttribute(command)) return NULL;
        if(command.nestedCommand->nameTag == "") return this;
        for(int i = 0; i < nestedTag.size(); i++){
            Tag * temp = nestedTag[i].search(*command.nestedCommand , res);
            if(command.nestedCommand->nestedCommand->nameTag == ""){
                if(temp != NULL){
                    res.push_back(*temp);
                    result->nestedTag.push_back(*temp);
                }
            }else{
                if(temp != NULL){
                    if(command.nestedCommand->nestedCommand->parameters.size() == 0){
                        result = temp;
                    }else result->nestedTag.push_back(*temp);
                }
            }
        }
        if(result->nestedTag.size() == 0) return NULL; else return result;
    }else return NULL;
}

void Tag::createEmptyTag(string tag){
    Tag result;
    result.name = tag.substr(1 , tag.find(' ') - 1);
    nestedTag.push_back(result);
}

void Tag::output(int counter){
    for(int i = 0; i < counter; i++){
        cout << '\t';
    }
    cout << name << ' ' << data << endl;
    if(!nestedTag.empty()){
        for(int i = 0; i < nestedTag.size(); i++){
            nestedTag[i].output(counter + 1);
        }
    }
}