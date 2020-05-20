#include <iostream>
#include <vector>

using namespace std;

bool table[10][17];
vector<vector<vector<bool>>> table2;
string Gray[] = {"00" , "01" , "11" , "10"};
string rowChar[] = {"!a" , "b" , "a" , "!b"};
string columnChar[] = {"!c" , "d" , "c" , "!d"};


void assignment(int pointer , bool value1 , bool value2 , bool value3 , bool value4){
    table[0][pointer] = value1;
    table[1][pointer] = value2;
    table[2][pointer] = value3;
    table[3][pointer] = value4;
}

void assignment1(int pointer , bool value1 , bool value2 , bool value3){
    assignment(pointer , value1 , value2 , value3 , false);
    assignment(pointer + 1 , value1 , value2 , value3 , true);
}

void assignment2(){
    assignment1(0 , false , false , false);
    assignment1(2 , false , false , true);
    assignment1(4 , false , true , false);
    assignment1(6 , false , true , true);
    assignment1(8 , true , false , false);
    assignment1(10 , true , false , true);
    assignment1(12 , true , true , false);
    assignment1(14 , true , true , true);
}

void subtractor(int pointer){
    if(table[3][pointer]){
        table[0][pointer] ? table[4][pointer] = false : table[4][pointer] = true;
        table[1][pointer] ? table[5][pointer] = false : table[5][pointer] = true;
        table[2][pointer] ? table[6][pointer] = false : table[6][pointer] = true;
    }else{
        table[4][pointer] = table[4][pointer - 1];
        table[5][pointer] = table[5][pointer - 1];
        table[6][pointer] = table[6][pointer - 1];
    }
}

void subtractor1(){
    table[4][0] = false;
    table[5][0] = false;
    table[6][0] = false;
    for(int i = 1; i < 16; i++){
        subtractor(i);
    }
}

void exclusiveOR(){
    for (int j = 0; j < 16; j++) {
        for(int i = 0; i < 3; i++){
            (table[i][j]^table[i+4][j]) ? table[i+7][j]=true : table[i+7][j]=false;;
        }
    }
}

void tableInicialization(){
    for(int i = 0; i <10; i++){
        for(int j = 0; j < 17; j++){
            table[i][j]=false;
        }
    }
    assignment2();
    subtractor1();
    exclusiveOR();
}

string SDNF(int pointer){
    string result;
    for(int i = 0; i < 16; i++){
        if(table[pointer][i]){
            result += '+';
            table[0][i] ? result += "a" : result += "!a";
            table[1][i] ? result += "*b" : result += "*!b";
            table[2][i] ? result += "*c" : result += "*!c";
        }
    }
    result.erase(0, 1);
    return result;
}

void inicializationTable2(vector<vector<bool>> * table){
    vector <bool> temp;
    for(int i = 0; i < 4; i++){
        temp.push_back(false);
    }
    for(int i = 0; i < 4; i++){
        table->push_back(temp);
    }
}

void createTable(int pointer){
    vector<vector<bool>> tableTemp;
    inicializationTable2(&tableTemp);
    int pointerColumn , pointerRow;
    for(int i = 0; i < 16; i++){
        if(table[pointer][i]){
            if(table[0][i]){
                if(table[1][i]){
                    pointerRow = 2;
                }else pointerRow = 3;
            }else if(table[1][i]){
                pointerRow = 1;
            }else pointerRow = 0;
            if(table[2][i]){
                if(table[3][i]){
                    pointerColumn = 2;
                }else pointerColumn = 3;
            }else if(table[3][i]){
                pointerColumn = 1;
            } else pointerColumn = 0;
            tableTemp[pointerRow][pointerColumn] = true;
        }
    }
    table2.push_back(tableTemp);
}

string row(int pointer){
    string result;
    int counter = 0;
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
            if(table2[pointer][i][j]){
                counter++;
            }else{
                counter = 0;
                break;
            }
        }
        if(counter == 8){
            result += '+' + rowChar[--i];
            counter = 0;
        }
    }
    return result;
}

string column(int pointer){
    int counter = 0;
    string result;
    for(int i = 0;  i < 4; i++){
        for(int j = 0; j < 4; j++){
            if(table2[pointer][j][i]){
                counter++;
            }else{
                counter = 0;
                break;
            }
        }
        if(counter == 8){
            result += '+' + columnChar[--i];
            counter = 0;
        }
    }
    return result;
}

string create(int pointer){
    string result = row(pointer);
    result += column(pointer);
    if(!result.empty()) result.erase(0 , 1);
    return result;
}

string tableMethod(int pointer){
    createTable(pointer);
    return create(pointer - 7);
}

void outputTable(){
    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 4; j++){
            cout << "\t" << Gray[j];
        }
        cout << "\t";
    }
    cout << endl;
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 3; j++){
            cout << Gray[i] << "\t";
            for(int k = 0; k < 4; k++){
                cout << table2[j][i][k] << "\t";
            }
        }
        cout << endl;
    }
}

void output(){
    cout<<"\t\t\t\t\t\t\t\tVariant 2\n";
    tableInicialization();
    for(int i = 0; i < 3; i++){
        cout << 'q' << i + 1 << "*\t|\t";
        for(int j = 0; j < 16; j++){
            cout << table[i][j]<< "\t";
        }
        cout << endl;
    }
    cout << "_____________________________________________________________________" << endl;
    cout << "V\t|\t";
    for(int i = 0; i < 16; i++){
        if(i%2==0) cout << false<< "\t";
        else cout << true<< "\t";
    }
    cout << endl << "_____________________________________________________________________" << endl;
    for(int i = 0; i < 3; i++){
        cout << 'q' << i + 1 << "\t|\t";
        for(int j = 0; j < 16; j++){
            cout << table[i + 4][j]<< "\t";
        }
        cout << endl;
    }
    cout << "_____________________________________________________________________" << endl;
    for(int i = 0; i < 3; i++){
        cout << 'h' << i + 1 << "\t|\t";
        for(int j = 0; j < 16; j++){
            cout << table[i + 7][j]<< "\t";
        }
        cout << endl;
    }
    for(int i = 7; i <10; i++){
        cout << "SDNF h" << i - 6 << ' '<< SDNF(i) <<endl;
    }
    vector<string> sDNF;
    for(int i = 0; i < 3; i++){
        sDNF.push_back(tableMethod(i + 7));
    }
    outputTable();
    for(int i = 0; i < 3; i++){
        cout << 'h' << i + 1 << " : " << sDNF[i] << endl;
    }
}

int main(){
    output();
}