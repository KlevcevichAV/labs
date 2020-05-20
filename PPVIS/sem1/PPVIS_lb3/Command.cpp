//
// Created by sanchir on 13.12.19.
//

#include "Command.h"

string Command::cut(string* element , char symbol , bool inclusionCheck){
    int pointer = element->find(symbol);
    string result = ((!inclusionCheck) ? element->substr(0 , pointer) : element->substr(0 , pointer + 1));
    (inclusionCheck) ? element->erase(0 , pointer + 1 ) : element->erase(0 , pointer);
    return result;
}

Command::Command(){
    nestedCommand = NULL;
}

void Command::splitCommand(string command){
    if(command[0] != '/'){
        return;
    }
    command.erase(0,1);
    nameTag = command.substr(0 , (command.find('/') != -1) ? command.find('/') : command.length());
    command.erase(0 , (command.find('/') != -1) ? command.find('/') : command.length());
    searchAttributeForCommand(nameTag);
    if(!parameters.empty()){
        nameTag.erase(nameTag.find('[') , nameTag.length() - nameTag.find('['));
    }
    nestedCommand = new Command;
    nestedCommand->splitCommand(command);
}

void Command::searchAttributeForCommand(string command){
    if(command.find('[') == -1){
        return;
    }
    command.erase(0 , command.find('[') + 1);
    command.erase(command.length() - 1 , 1);

    do{
        Parameter temp;
        temp.name = cut(&command , '(' , false);
        command.erase(0 , 2);
        if(temp.name[0] == '!'){
            temp.inverse = true;
            temp.name.erase(0 , 1);
        }
        if(temp.name == "attr"){
            temp.name = cut(&command , ',' , false);
            temp.name.erase(temp.name.length() - 1 , 1);
            temp.data = cut(&command , ')' , false);
            temp.data.erase(0 , 3);
            temp.data.erase(temp.data.length() - 1 , 1);
        }else{
            temp.data = cut(&command , ')' , false);
            temp.data.erase(temp.data.length() - 1 , 1);
        }
        command.erase(0 , 2);

        if(!command.empty()){
            temp.operation = command.substr(0 , 2);
            command.erase(0 , 3);
        }
        parameters.push_back(temp);
    }while (command.find(')') != -1);
    return;
}

void Command::free(){
    if(nestedCommand != NULL){
        nestedCommand->free();
    }
    delete nestedCommand;
}

void Command::output(){
    cout << nameTag << endl;
    if(!parameters.empty()){
        cout << "Parameters :\n";
        for(int i = 0; i < parameters.size(); i++){
            cout << parameters[i].name << ' ' << parameters[i].data << ' ' << parameters[i].operation << ' ';
        }
        cout << endl;
    }
    if(nestedCommand != NULL){
        nestedCommand->output();
    }
}