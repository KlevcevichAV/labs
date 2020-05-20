#include <vector>
#include <iostream>

using namespace std;

struct abc{
    string a;
    string b;
    string c;
};

struct DK{
    string DF;
    string KF;

};

struct xx{
    string multiplier1;
    string multiplier2;
};

vector <string> splitElements(string SNF){
    vector <string> elements;
    string temp;
    char sign = '+';
    if(SNF[0] == '(') sign = '*';
    for(int i = 0; i < SNF.length(); i++){
        if(SNF[i] == sign){
            elements.push_back(temp);
            temp = "";
        }else temp += SNF[i];
    }
    elements.push_back(temp);
    return elements;
}

vector <abc> splitElementsVector(vector <string> elements){
    vector<abc> elementsInAbc;
    abc temp;
    for(int i = 0; i < elements.size(); i++){
        if(elements[i][0] == '(') elements[i].erase(0 , 1);
        if(elements[i][elements[i].length() - 1] == ')') elements[i].erase(elements[i].length() - 1 , 1);
    }
    for(int i = 0; i < elements.size(); i++){
        string tempString = "";
        temp.a = temp.b = temp.c = "";
        for(int j = 0; j < elements[i].length(); j++){
            if(elements[i][j] == '!')tempString += elements[i][j++];
            if(elements[i][j] == 'a'){
                temp.a = tempString + 'a';
                tempString = "";
            }else if(elements[i][j] == 'b'){
                temp.b = tempString + 'b';
                tempString = "";
            }else if(elements[i][j] == 'c'){
                temp.c = tempString + 'c';
                tempString = "";
            }
        }
        elementsInAbc.push_back(temp);
    }
    return elementsInAbc;
}

int comparison(abc element1 , abc element2){
    int counter = 0;
    int uk = 0;
    if(element1.a != element2.a){
        counter++;
        uk = 1;
    }
    if(element1.b != element2.b){
        counter++;
        uk = 2;
    }
    if(element1.c != element2.c){
        counter++;
        uk = 3;
    }
    if(counter == 1)return uk; else return -1;
}

string createDNF(vector<abc> elements){
    string DNF;
    for(int i = 0; i < elements.size(); i++){
        for(int j = i + 1; j < elements.size(); j++){
            int uk = comparison(elements[i] , elements[j]);
            if(uk != -1){
                switch (uk){
                    case 1:{
                        DNF += '+' + elements[i].b + '*' + elements[i].c;
                        break;
                    }
                    case 2:{
                        DNF += '+' + elements[i].a + '*' + elements[i].c;
                        break;
                    }
                    case 3:{
                        DNF += '+' + elements[i].a + '*' + elements[i].b;
                        break;
                    }
                }
            }
        }
    }
    DNF.erase(0,1);
    return DNF;
}

string createKNF(vector<abc> elements){
    string KNF;
    for(int i = 0; i < elements.size(); i++){
        for(int j = i + 1; j < elements.size(); j++){
            int uk = comparison(elements[i] , elements[j]);
            if(uk != -1){
                switch (uk){
                    case 1:{
                        KNF += "*(" + elements[i].b + '+' + elements[i].c + ')';
                        break;
                    }
                    case 2:{
                        KNF += "*(" + elements[i].a + '+' + elements[i].c + ')';
                        break;
                    }
                    case 3:{
                        KNF += "*(" + elements[i].a + '+' + elements[i].b + ')';
                        break;
                    }
                }
            }
        }
    }
    KNF.erase(0,1);
    return KNF;
}

string firstStepForSNF(string SNF){
    if(SNF[0] != '('){
        vector <string> elementsSDNF = splitElements(SNF);
        vector <abc> elementsD = splitElementsVector(elementsSDNF);
        string DNF = createDNF(elementsD);
        return DNF;
    }else{
        vector <string> elementsSKNF = splitElements(SNF);
        vector <abc> elementsK = splitElementsVector(elementsSKNF);
        string KNF = createKNF(elementsK);
        return KNF;
    }
}

xx to_xxDF (string composition){
    string temp;
    xx resultl;
    for(int i = 0; i < composition.length(); i++){
        if(composition[i] == '+' || composition[i] == '*'){
            resultl.multiplier1 = temp;
            temp = "";
        }else temp += composition[i];
    }
    resultl.multiplier2 = temp;
    return resultl;
}

xx to_xxKF (string composition){
    string temp;
    xx resultl;
    for(int i = 0; i < composition.length(); i++){
        if(composition[i] == '+' || composition[i] == '*'){
            resultl.multiplier1 = temp;
            temp = "";
        }else if(composition[i] != '(' && composition[i] != ')') temp += composition[i];
    }
    resultl.multiplier2 = temp;
    return resultl;
}

string selectExtraImplicantsForDNF(vector<string> elementsOfNF) {
    vector<xx> elements;
    string result;
    bool check = false;
    if(elementsOfNF[0][0] == '(') check =true;
    for(int i = 0; i < elementsOfNF.size(); i++){
        if(check)
            elements.push_back(to_xxKF(elementsOfNF[i]));
        else elements.push_back(to_xxDF(elementsOfNF[i]));
    }
    for(int i = 0; i < elements.size(); i++){
        string temp = "";
        for(int j = 0; j < elements.size(); j++){
            if(i != j){
                if(elements[i].multiplier1 == elements[j].multiplier1 || elements[i].multiplier2 == elements[j].multiplier1 ){
                    temp += elements[j].multiplier2;
                    continue;
                }
                if(elements[i].multiplier1 == elements[j].multiplier2|| elements[i].multiplier2 == elements[j].multiplier2){
                    temp += elements[j].multiplier1;
                    continue;
                }
                break;
            }
        }
        if(temp.length() != 3){
            if(check)result += '*' + elementsOfNF[i]; else result += '+' + elementsOfNF[i];
        }
    }
    result.erase(0 , 1);
    return result;
}

string secondStepForSDNF(string DNF){
    vector <string> elementsOfDNF = splitElements(DNF);
    string TDNF = selectExtraImplicantsForDNF(elementsOfDNF);
    return TDNF;
}

string secondStepForSKNF(string KNF){
    vector <string> elementsOfKNF = splitElements(KNF);
    string TKNF = selectExtraImplicantsForDNF(elementsOfKNF);
    return TKNF;
}

bool checkforbelonging(abc element1, abc element2){
    if(element1.a != ""){
        if(element1.a != element2.a) return false;
    }
    if(element1.b != ""){
        if(element1.b != element2.b) return false;
    }
    if(element1.c != ""){
        if(element1.c != element2.c) return false;
    }
    return true;
}

string secondStepInCalculationTableMethodForDNF(string DNF , string SDNF){
    string result;
    vector <string> elementsDNFString = splitElements(DNF);
    vector <abc> elementsDNF = splitElementsVector(elementsDNFString);
    vector <string> elementsSDNFString = splitElements(SDNF);
    vector <abc> elementsSDNF = splitElementsVector(splitElements(SDNF));
    vector<int> quantityTrue;
    for(int i = 0; i < elementsDNF.size(); i++){
        quantityTrue.push_back(0);
    }
    bool table[elementsDNF.size()][elementsSDNF.size()];
    for(int i = 0; i < elementsDNF.size(); i++){
        for(int j = 0; j < elementsSDNF.size(); j++){
            table[i][j] = false;
        }
    }
    for(int i = 0; i < elementsDNF.size(); i++){
        for(int j = 0; j < elementsSDNF.size(); j++){
            if(checkforbelonging(elementsDNF[i], elementsSDNF[j])){
                table[i][j] = true;
                quantityTrue[i]++;
            }
        }
    }
    cout << "\t";
    for(int i = 0; i < elementsSDNF.size(); i++ ){
        cout << elementsSDNFString[i] << "\t";
    }
    cout << endl;
    for(int i = 0; i < elementsDNF.size(); i++){
        cout << elementsDNFString[i] << "\t";
        for(int j = 0; j < elementsSDNF.size(); j++){
            if(table[i][j]) cout << 'X' << "\t\t";
            else cout << "\t\t";
        }
        cout << endl;
    }

    for (int i = 0; i < elementsDNF.size(); i++){
        bool check;
        int counter = 0;
        for(int j = 0; j < elementsSDNF.size(); j++){
            if(table[i][j]){
                check = false;
                for(int k = 0; k < elementsDNF.size(); k++){
                    if(i != k && table[k][j]){
                        check = true;
                        counter++;
                        break;
                    }
                }
                if(check)continue;
                break;
            }
        }
        if (counter != quantityTrue[i]) result += "+" + elementsDNFString[i];
    }
    result.erase(0 ,1);
    return result;
}

string secondStepInCalculationTableMethodForKNF(string KNF , string SKNF){
    string result;
    vector <string> elementsKNFString = splitElements(KNF);
    vector <abc> elementsKNF = splitElementsVector(elementsKNFString);
    vector <string> elementsSKNFString = splitElements(SKNF);
    vector <abc> elementsSKNF = splitElementsVector(splitElements(SKNF));
    vector<int> quantityTrue;
    for(int i = 0; i < elementsKNF.size(); i++){
        quantityTrue.push_back(0);
    }
    bool table[elementsKNF.size()][elementsSKNF.size()];
    for(int i = 0; i < elementsKNF.size(); i++){
        for(int j = 0; j < elementsSKNF.size(); j++){
            table[i][j] = false;
        }
    }
    for(int i = 0; i < elementsKNF.size(); i++){
        for(int j = 0; j < elementsSKNF.size(); j++){
            if(checkforbelonging(elementsKNF[i], elementsSKNF[j])){
                table[i][j] = true;
                quantityTrue[i]++;
            }
        }
    }
    cout << "\t\t";
    for(int i = 0; i < elementsSKNF.size(); i++ ){
        cout << elementsSKNFString[i] << " ";
    }
    cout << endl;
    for(int i = 0; i < elementsKNF.size(); i++){
        cout << elementsKNFString[i] << "\t";
        for(int j = 0; j < elementsSKNF.size(); j++){
            if(table[i][j]) cout << 'X' << "\t      ";
            else cout << "\t       ";
        }
        cout << endl;
    }

    for (int i = 0; i < elementsKNF.size(); i++){
        bool check;
        int counter = 0;
        for(int j = 0; j < elementsSKNF.size(); j++){
            if(table[i][j]){
                check = false;
                for(int k = 0; k < elementsKNF.size(); k++){
                    if(i != k && table[k][j]){
                        check = true;
                        counter++;
                        break;
                    }
                }
                if(check)continue;
                break;
            }
        }
        if (counter != quantityTrue[i]) result += "*" + elementsKNFString[i];
    }
    result.erase(0 ,1);
    return result;
}

vector<vector<bool>> fillTheMatrixDF(string SDNF){
    int pointerOnRow, pointerOnColumn;
    vector <vector<bool>> table;
    table.reserve(3);
    for(int i = 0; i < 3; i++){
        table[i].reserve(5);
    }
    vector<bool> te;
    for(int i = 0; i < 5; i++){
        te.push_back(false);
    }
    table.push_back(te);
    table.push_back(te);
    vector<string> f = splitElements(SDNF);
    vector <abc> elementsSDNF = splitElementsVector(f);
    for(int i = 0; i < elementsSDNF.size(); i++){
        if ( elementsSDNF[i].a[0] == '!') pointerOnRow = 0; else pointerOnRow = 1;
        if ( elementsSDNF[i].b[0] == '!') {
            if (elementsSDNF[i].c[0] == '!') pointerOnColumn = 0; else pointerOnColumn = 1;
        }else if ( elementsSDNF[i].c[0] == '!')pointerOnColumn = 3; else pointerOnColumn = 2;
        table[pointerOnRow][pointerOnColumn] = true;
    }
    return table;
};

vector<vector<bool>> fillTheMatrixKF(string SKNF){
    int pointerOnRow, pointerOnColumn;
    vector <vector<bool>> table;
    table.reserve(3);
    for(int i = 0; i < 3; i++){
        table[i].reserve(5);
    }
    vector<bool> te;
    for(int i = 0; i < 5; i++){
        te.push_back(false);
    }
    table.push_back(te);
    table.push_back(te);
    vector<string> f = splitElements(SKNF);
    vector <abc> elementsSKNF = splitElementsVector(f);
    for(int i = 0; i < elementsSKNF.size(); i++){
        if ( elementsSKNF[i].a[0] == '!') pointerOnRow = 1; else pointerOnRow = 0;
        if ( elementsSKNF[i].b[0] == '!') {
            if (elementsSKNF[i].c[0] == '!') pointerOnColumn = 2; else pointerOnColumn = 3;
        }else if ( elementsSKNF[i].c[0] == '!')pointerOnColumn = 1; else pointerOnColumn = 0;
        table[pointerOnRow][pointerOnColumn] = true;
    }
    return table;
};

string createGroupDF(vector<vector<bool>> table){
    string result;
    int counter = 0;
    for(int i = 0; i < 4; i++ ){
        if(table[0][i]) counter++;
    }
    if (counter == 4) {
        result += "+!a";
        for(int i = 0; i < 5 ; i++) table[0][i] == false;
    }
    counter = 0;
    for(int i = 0; i < 4; i++ ){
        if(table[1][i]) counter++;
    }
    if (counter == 4) {
        result += "+a";
        for(int i = 0; i < 5 ; i++) table[1][i] == false;
    }
    for(int i = 0; i < 3; i++){
        if(table[0][i] && table[0][i + 1] && table[1][i] && table[1][i + 1]) {
            if(i == 0) result += "+!b";
            if(i == 1) result += "+c";
            if(i == 2) result += "+b";
            table[0][i] = table[0][i + 1] = table[1][i] = table[1][i + 1] = false;
        }
    }
    if(table[0][0] && table[1][0] && table[3][0] && table[3][1]) {
        result += "+!c";
        table[0][0] = table[1][0] = table[3][0] = table[3][1] = false;
    }
    for(int i = 0; i < 4; i++){
        if(table[0][i] && table[1][i]) {
            if(i == 0) result += "+!b*!c";
            if(i == 1) result += "+!b*c";
            if(i == 2) result += "+b*c";
            if(i == 3) result += "+b*!c";
            table[0][i] = table[1][i] = false;
        }
    }
    for(int i = 0; i < 3; i++){
        if(table[0][i] && table[0][i+1]) {
            if(i == 0) result += "+!a*!b";
            if(i == 1) result += "+!a*c";
            if(i == 2) result += "+!a*b";
            table[0][i] = table[0][i+1] = false;
        }
        if(table[1][i] && table[1][i+1]) {
            if(i == 0) result += "+a*!b";
            if(i == 1) result += "+a*c";
            if(i == 2) result += "+a*b";
            table[1][i] = table[1][i+1] = false;
        }
    }
    if(table[0][0] && table[0][3]){
        result += "+!a*!c";
        table[0][0] = table[0][3] = false;
    }
    if(table[1][0] && table[1][3]){
        result += "+a*!c";
        table[1][0] = table[1][3] = false;
    }
    for(int i = 0; i < 2; i++){
        for(int j = 0; j < 4; j++){
            if(table[i][j]){
                if(i == 0) result +="+!a*";
                else result += "+a*";
                if(j == 0) result += "!b*!c";
                if(j == 1) result += "!b*c";
                if(j == 2) result += "b*c";
                if(j == 3) result += "b*!c";
                table[i][j] = false;
            }
        }
    }
    result.erase(0 , 1);
    return result;
}

string createGroupKF(vector<vector<bool>> table){
    string result;
    int counter = 0;
    for(int i = 0; i < 4; i++ ){
        if(table[0][i]) counter++;
    }
    if (counter == 4) {
        result += "*a";
        for(int i = 0; i < 5 ; i++) table[0][i] == false;
    }
    counter = 0;
    for(int i = 0; i < 4; i++ ){
        if(table[1][i]) counter++;
    }
    if (counter == 4) {
        result += "*!a";
        for(int i = 0; i < 5 ; i++) table[1][i] == false;
    }
    for(int i = 0; i < 3; i++){
        if(table[0][i] && table[0][i + 1] && table[1][i] && table[1][i + 1]) {
            if(i == 0) result += "*b";
            if(i == 1) result += "*!c";
            if(i == 2) result += "*!b";
            table[0][i] = table[0][i + 1] = table[1][i] = table[1][i + 1] = false;
        }
    }
    if(table[0][0] && table[1][0] && table[3][0] && table[3][1]) {
        result += "*c";
        table[0][0] = table[1][0] = table[3][0] = table[3][1] = false;
    }
    for(int i = 0; i < 4; i++){
        if(table[0][i] && table[1][i]) {
            if(i == 0) result += "*(b+c)";
            if(i == 1) result += "*(b+!c)";
            if(i == 2) result += "*(!b+!c)";
            if(i == 3) result += "*(!b+c)";
            table[0][i] = table[1][i] = false;
        }
    }
    for(int i = 0; i < 3; i++){
        if(table[0][i] && table[0][i+1]) {
            if(i == 0) result += "*(a+b)";
            if(i == 1) result += "*(a+!c)";
            if(i == 2) result += "*(a+!b)";
            table[0][i] = table[0][i+1] = false;
        }
        if(table[1][i] && table[1][i+1]) {
            if(i == 0) result += "*(!a+b)";
            if(i == 1) result += "*(!a+!c)";
            if(i == 2) result += "*(!a+!b)";
            table[1][i] = table[1][i+1] = false;
        }
    }
    if(table[0][0] && table[0][3]){
        result += "*(a+c)";
        table[0][0] = table[0][3] = false;
    }
    if(table[1][0] && table[1][3]){
        result += "*(!a+c)";
        table[1][0] = table[1][3] = false;
    }
    for(int i = 0; i < 2; i++){
        for(int j = 0; j < 4; j++){
            if(table[i][j]){
                if(i == 0) result +="*(a+";
                else result += "*(!a+";
                if(j == 0) result += "b+c)";
                if(j == 1) result += "b+!c)";
                if(j == 2) result += "!b+!c)";
                if(j == 3) result += "!b+c)";
                table[i][j] = false;
            }
        }
    }
    result.erase(0 , 1);
    return result;
}

DK calculationMethod(string SDNF , string SKNF){
    string DNF = firstStepForSNF(SDNF);
    string MDNF = secondStepForSDNF(DNF);
    string KNF = firstStepForSNF(SKNF);
    string MKNF = secondStepForSKNF(KNF);
    DK result;
    result.DF = MDNF;
    result.KF = MKNF;
    return result;
}

DK calculationTableMethod(string SDNF , string SKNF){
    string DNF = firstStepForSNF(SDNF);
    string KNF = firstStepForSNF(SKNF);
    DK result;
    result.DF = secondStepInCalculationTableMethodForDNF(DNF, SDNF);
    result.KF = secondStepInCalculationTableMethodForKNF(KNF, SKNF);

    return result;
}

DK tableMethod(string SDNF , string SKNF){
    DK result;
    vector <vector<bool>> table = fillTheMatrixDF(SDNF);

    result.DF = createGroupDF(table);
    result.KF = createGroupKF(table);

    cout << "\t00\t01\t11\t10\n";
    for(int i = 0; i < 2; i++){
        cout << i << "\t";
        for(int j = 0; j < 4; j++){
            if(table[i][j]) cout << "1\t"; else cout << "0\t";
        }
        cout << "\n";
    }

    return result;
}

/*
 *  Alina's example
 *  string SDNF = "!a*!b*!c+!a*b*!c+!a*!b*c+a*b*!c";
 *  string SKNF = "(!a+b+c)*(a+!b+!c)*(!a+b+!c)*(!a+!b+!c)";
 */
/*
 *  Sasha's example
 *  string SDNF = "a*!b*!c+!a*b*!c+a*b*!c+a*!b*c";
 *  string SKNF = "(a+b+c)*(a+b+!c)*(a+!b+!c)*(!a+!b+!c)";
 */

int main(void)
{
    string SDNF = "a*!b*!c+!a*b*!c+a*b*!c+a*!b*c";
    string SKNF = "(a+b+c)*(a+b+!c)*(a+!b+!c)*(!a+!b+!c)";

    cout << "SDNF = " << SDNF << endl;
    cout << "SKNF = " << SKNF << "\n\n";

    cout << "calculation method:\n";
    DK calculatedDK = calculationMethod(SDNF, SKNF);
    cout << "For SDNF:" << calculatedDK.DF << endl;
    cout << "For SKNF:" << calculatedDK.KF;

    cout << "\n\n";
    cout << "calculation-table method:\n";
    DK calculatedDKWithTables = calculationTableMethod(SDNF, SKNF);
    cout << "For SDNF:" << calculatedDKWithTables.DF << endl;
    cout << "For SKNF:" << calculatedDKWithTables.KF;

    cout << "\n\n";
    cout << "table method:\n";
    DK tableMathod = tableMethod(SDNF , SKNF);
    cout << "For SDNF:" << calculatedDKWithTables.DF << endl;
    cout << "For SKNF:" << calculatedDKWithTables.KF;
    return 0;
}