#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct SDNF{
    string di;
    string bi;
};

struct BM{
    int counter;
    char value;
};

struct coordinates{
    vector <int> x;
    vector <int> y;
};

struct abc{
    string a;
    string b;
    string c;
};

struct abcd{
    string a;
    string b;
    string c;
    string d;
};

struct transistorsAndIns{
    int counter1;
    int counter2;
};

vector<vector<vector<BM>>> tableBM;
vector<vector<vector<char>>> tables;
vector<vector<coordinates>> unionCoordinate;

string gray[] = {"00" , "01" , "11" , "10"};
string column[] = {"!c*!d" , "!c*d" , "c*d" , "c*!d"};
string row[] = {"!a*!b" , "!a*b" , "a*b" , "a*!b"};
string rowChar[] = {"!a" , "b" , "a" , "!b"};
string columnChar[] = {"!c" , "d" , "c" , "!d"};

bool table1[6][9];
bool table2[9][16];

void adder(){
    for(int i = 0; i <8; i++){
        if(table1[0][i] && table1[1][i] && table1[2][i]){
            table1[3][i] = true;
            table1[4][i] = true;
        }else if((table1[0][i] && table1[1][i] && !table1[2][i]) || (table1[0][i] && !table1[1][i] && table1[2][i]) || (!table1[0][i] && table1[1][i] && table1[2][i])){
            table1[3][i] = false;
            table1[4][i] = true;
        }else if((table1[0][i] && !table1[1][i] && !table1[2][i]) || (!table1[0][i] && !table1[1][i] && table1[2][i]) || (!table1[0][i] && table1[1][i] && !table1[2][i])){
            table1[3][i] = true;
            table1[4][i] = false;
        }else {
            table1[3][i] = false;
            table1[4][i] = false;
        }
    }
}

void subtractor(){
    for(int i = 0; i < 8; i++){
        bool ans;
        if(table1[0][i] && !table1[1][i]) ans = true; else{
            if((!table1[0][i] && !table1[1][i]) || (table1[0][i] && table1[1][i])) ans = false;
            else {
                ans = true;
                table1[3][i] = true;
            }
        }
        if(ans && !table1[2][i]) table1[4][i] = true;
        else{
            if((!ans && !table1[2][i]) || (ans && table1[2][i])) table1[4][i] = false;
            else {
                table1[4][i] = true;
                table1[3][i] = true;
            }
        }
    }
}

void table1Inicialization(){
    table1[0][0] = false;
    table1[1][0] = false;
    table1[2][0] = false;

    table1[0][1] = false;
    table1[1][1] = false;
    table1[2][1] = true;

    table1[0][2] = false;
    table1[1][2] = true;
    table1[2][2] = false;

    table1[0][3] = true;
    table1[1][3] = false;
    table1[2][3] = false;

    table1[0][4] = false;
    table1[1][4] = true;
    table1[2][4] = true;

    table1[0][5] = true;
    table1[1][5] = false;
    table1[2][5] = true;

    table1[0][6] = true;
    table1[1][6] = true;
    table1[2][6] = false;

    table1[0][7] = true;
    table1[1][7] = true;
    table1[2][7] = true;

    adder();
//    subtractor();
}

SDNF transToSDNF(){
    string sdnf;
    for(int i = 0; i < 8; i++){
        if(table1[3][i] == 1){
            if(!table1[0][i]) sdnf += '!';
            sdnf += "a*";

            if(!table1[1][i]) sdnf += '!';
            sdnf += "b*";

            if(!table1[2][i]) sdnf += '!';
            sdnf += "c";

            sdnf += '+';
        }
    }
    sdnf.erase(sdnf.length() -1 ,1);
    SDNF ans;
    ans.di = sdnf;
    sdnf = "";
    for(int i = 0; i < 8; i++){
        if(table1[4][i] == 1){
            if(!table1[0][i]) sdnf += '!';
            sdnf += "a*";

            if(!table1[1][i]) sdnf += '!';
            sdnf += "b*";

            if(!table1[2][i]) sdnf += '!';
            sdnf += "c";

            sdnf += '+';
        }
    }
    sdnf.erase(sdnf.length() -1 ,1);
    ans.bi = sdnf;
    return ans;
}

void outputTable1(){
    cout << "#\t|\t";
    for(int i = 1; i < 9; i++){
        cout << i <<"\t";
    }
    cout << endl;
    cout << "______________________________________";
    cout << endl;
    cout << "x1\t|\t";
    for(int i = 0; i < 8; i++){
        cout << table1[0][i]<< "\t";
    }
    cout << endl;
    cout << "x2\t|\t";
    for(int i = 0; i < 8; i++){
        cout << table1[1][i]<< "\t";
    }
    cout << endl;
    cout << "x3\t|\t";
    for(int i = 0; i < 8; i++){
        cout << table1[2][i]<< "\t";
    }
    cout << endl;
    cout << "______________________________________";
    cout << endl;
    cout << "di\t|\t";
    for(int i = 0; i < 8; i++){
        cout << table1[3][i]<< "\t";
    }
    cout << endl;
    cout << "bi+1|\t";
    for(int i = 0; i < 8; i++){
        cout << table1[4][i]<< "\t";
    }
    cout << endl;
    cout << endl;
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

vector<vector<bool>> fillTheMatrixDF1(string SDNF){
    int pointerOnRow, pointerOnColumn;
    vector <vector<bool>> tableForMethod;
    tableForMethod.reserve(3);
    for(int i = 0; i < 3; i++){
        tableForMethod[i].reserve(5);
    }
    vector<bool> te;
    for(int i = 0; i < 5; i++){
        te.push_back(false);
    }
    tableForMethod.push_back(te);
    tableForMethod.push_back(te);
    vector<string> f = splitElements(SDNF);
    vector <abc> elementsSDNF = splitElementsVector(f);
    for(int i = 0; i < elementsSDNF.size(); i++){
        if ( elementsSDNF[i].a[0] == '!') pointerOnRow = 0; else pointerOnRow = 1;
        if ( elementsSDNF[i].b[0] == '!') {
            if (elementsSDNF[i].c[0] == '!') pointerOnColumn = 0; else pointerOnColumn = 1;
        }else if ( elementsSDNF[i].c[0] == '!')pointerOnColumn = 3; else pointerOnColumn = 2;
        tableForMethod[pointerOnRow][pointerOnColumn] = true;
    }
    return tableForMethod;
}

string createGroupDF(vector<vector<bool>> table) {
    string result;
    for(int i = 0; i < 4; i++){
        if(table[0][i] && table[1][i]) {
            if(i == 0) result += "+!b*!c";
            if(i == 1) result += "+!b*c";
            if(i == 2) result += "+b*c";
            if(i == 3) result += "+b*!c";
        }
    }
    for(int i = 0; i < 3; i++){
        if(table[0][i] && table[0][i+1]) {
            if(i == 0) result += "+!a*!b";
            if(i == 1) result += "+!a*c";
            if(i == 2) result += "+!a*b";
        }
        if(table[1][i] && table[1][i+1]) {
            if(i == 0) result += "+a*!b";
            if(i == 1) result += "+a*c";
            if(i == 2) result += "+a*b";
        }
    }
    if(table[0][0] && table[0][3]){
        result += "+!a*!c";
    }
    if(table[1][0] && table[1][3]){
        result += "+a*!c";
    }
    if(result != ""){
        result.erase(0 , 1);
        return result;
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

string tableMethod1(string SDNF){
    string result;
    vector <vector<bool>> table = fillTheMatrixDF1(SDNF);

    result = createGroupDF(table);

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

abc searchDenial(string SDNF){
    abc ans;
    for(int i = 0; i < SDNF.length(); i++){
        if(SDNF[i] == '!'){
            if(SDNF[i+1] == 'a') ans.a = '+';
            if(SDNF[i+1] == 'b') ans.b = '+';
            if(SDNF[i+1] == 'b') ans.b = '+';
        }
    }
    return ans;
}

int counterDenial(string diSDNF  , string biSDNF){
    abc ans1 , ans2;
    ans1 = searchDenial(diSDNF);
    ans2 = searchDenial(biSDNF);
    int counter = 0;
    if(!ans1.a.empty() || ans2.a.empty()) counter++;
    if(!ans1.b.empty() || ans2.b.empty()) counter++;
    if(!ans1.c.empty() || ans2.c.empty()) counter++;
    return counter;
}

transistorsAndIns countConjunctionTransistors(vector<string> implicants){
    transistorsAndIns ans;
    ans.counter2 = 0;
    for(int i = 0; i < implicants[0].length(); i++){
        if(implicants[0][i] == '*'){
            ans.counter2++;
        }
    }
    ans.counter2++;
    ans.counter1 = implicants.size();
    return ans;
}

void outputTransistors(string diTDNF, string biTDNF){
    transistorsAndIns denial;
    denial.counter1 = counterDenial(diTDNF , biTDNF);
    denial.counter2 = 1;
    transistorsAndIns addBi;
    transistorsAndIns addDi;
    addBi.counter1 = addDi.counter1 = 1;
    addBi.counter2 = splitElements(biTDNF).size();
    addDi.counter2 = splitElements(diTDNF).size();
    transistorsAndIns biConjunction;
    transistorsAndIns diConjunction;
    biConjunction = countConjunctionTransistors(splitElements(biTDNF));
    diConjunction = countConjunctionTransistors(splitElements(diTDNF));
    int totalTransistors = denial.counter2 * denial.counter1 + addBi.counter1 * addBi.counter2 + addDi.counter1 * addDi.counter2 + biConjunction.counter1 * biConjunction.counter2 + diConjunction.counter1 * diConjunction.counter2;

    cout << denial.counter1 << ' ' << denial.counter2 << "-in !\n";
    cout << addBi.counter1 << ' ' << addBi.counter2 << "-in +\n";
    cout << addDi.counter1 << ' ' << addDi.counter2 << "-in +\n";
    cout << biConjunction.counter1 << ' ' << biConjunction.counter2 << "-in *\n";
    cout << diConjunction.counter1 << ' ' << diConjunction.counter2 << "-in *\n";
    cout << "Total transistors: " << totalTransistors << endl;
}

void output1(){
    table1Inicialization();
    cout << "\t\t\t\tVariant 1" << endl;
    outputTable1();
    SDNF ans;
    ans = transToSDNF();
    cout << "bi+1 SDNF = " << ans.bi << endl;
    cout << "di SDNF = " << ans.di << endl;
    ans.bi = tableMethod1(ans.bi);
    cout << "bi+1 TDNF = " << ans.bi << endl;
    ans.di = tableMethod1(ans.di);
    cout << "di TDNF = " << ans.di << endl;
    outputTransistors(ans.di , ans.bi);

}

void table2Inicialization(int n){
    table2[0][0] = false;
    table2[1][0] = false;
    table2[2][0] = false;
    table2[3][0] = false;

    table2[0][1] = false;
    table2[1][1] = false;
    table2[2][1] = false;
    table2[3][1] = true;

    table2[0][2] = false;
    table2[1][2] = false;
    table2[2][2] = true;
    table2[3][2] = false;

    table2[0][3] = false;
    table2[1][3] = false;
    table2[2][3] = true;
    table2[3][3] = true;

    table2[0][4] = false;
    table2[1][4] = true;
    table2[2][4] = false;
    table2[3][4] = false;

    table2[0][5] = false;
    table2[1][5] = true;
    table2[2][5] = false;
    table2[3][5] = true;

    table2[0][6] = false;
    table2[1][6] = true;
    table2[2][6] = true;
    table2[3][6] = false;

    table2[0][7] = false;
    table2[1][7] = true;
    table2[2][7] = true;
    table2[3][7] = true;

    table2[0][8] = true;
    table2[1][8] = false;
    table2[2][8] = false;
    table2[3][8] = false;

    table2[0][9] = true;
    table2[1][9] = false;
    table2[2][9] = false;
    table2[3][9] = true;

    table2[0][10] = true;
    table2[1][10] = false;
    table2[2][10] = true;
    table2[3][10] = false;

    table2[0][11] = true;
    table2[1][11] = false;
    table2[2][11] = true;
    table2[3][11] = true;

    table2[0][12] = true;
    table2[1][12] = true;
    table2[2][12] = false;
    table2[3][12] = false;

    table2[0][13] = true;
    table2[1][13] = true;
    table2[2][13] = false;
    table2[3][13] = true;

    table2[0][14] = true;
    table2[1][14] = true;
    table2[2][14] = true;
    table2[3][14] = false;

    table2[0][15] = true;
    table2[1][15] = true;
    table2[2][15] = true;
    table2[3][15] = true;

    for(int i = 0; i < 4; i++){
        for(int j = n; j < 16; j++){
            table2[i + 4][j - n] = table2[i][j];
        }
    }
}

void outputTable2(){
    cout << "\n\t\t\t\t\t\tVariant g (n = 5)" << endl;
    cout << "J\t|\t";
    for(int i = 0; i < 16; i++){
        cout << i <<"\t";
    }
    cout << endl;
    cout << "______________________________________________________________________\n";
    for(int i = 0; i < 4; i++){
        cout << 'x' << 4 - i << "\t|\t";
        for(int j = 0; j < 16; j++){
            cout << table2[i][j] << "\t";
        }
        cout << endl;
    }
    cout << "______________________________________________________________________\n";
    for(int i = 0; i < 4; i++){
        cout << 'y' << 4 - i << "\t|\t";
        for(int j = 0; j < 10; j++){
            cout << table2[i + 4][j] << "\t";
        }
        for(int j = 0; j < 6; j++){
            cout << "-\t";
        }
        cout << endl;

    }

}

vector <string> createSDNF(int n){
    vector <string > ans;
    for(int i = 0; i < 4; i++){
        string temp = "";
        for(int j =  0; j < 10; j++){
            if(table2[i + 4][j]){
                if(table2[i][j]) temp += "+a"; else temp += "+!a";
                if(table2[i + 1][j]) temp += "*b"; else temp += "*!b";
                if(table2[i + 2][j]) temp += "*c"; else temp += "*!c";
                if(table2[i + 3][j]) temp += "*d"; else temp += "*!d";
            }
        }
        temp.erase(0,1);
        ans.push_back(temp);
    }
    return ans;
}

vector<vector<char>> fillTheMatrixDF2(int pointer){
    int pointerOnRow, pointerOnColumn;
    vector <vector<char>> tableForMethod;
    vector<char> temp;
    for(int i = 0; i < 4; i++){
        temp.push_back(' ');
    }
    for(int i = 0; i < 4; i++){
        tableForMethod.push_back(temp);
    }
    for(int i = 0; i < 10; i++){
        if(table2[3][i]){
            if(table2[2][i]){
                pointerOnRow = 2;
            }else pointerOnRow = 3;
        }else if(table2[2][i]) pointerOnRow = 1; else pointerOnRow = 0;
        if(table2[1][i]){
            if(table2[0][i]){
                pointerOnColumn = 2;
            }else pointerOnColumn = 3;
        }else if(table2[0][i]) pointerOnColumn = 1; else pointerOnColumn = 0;
        if(table2[pointer][i]){
            tableForMethod[pointerOnRow][pointerOnColumn] = '1';
        }else tableForMethod[pointerOnRow][pointerOnColumn] = '0';
    }
//    tables.push_back(tableForMethod);
    return tableForMethod;
}

void tablesInicialuzation(){
    for(int i = 4; i < 8; i++) {
        tables.push_back(fillTheMatrixDF2(i));
    }

}

void initializationTableBM(int size){
    for(int i = 0; i < size; i++){
        vector<vector<BM>> temp2;
        for(int j = 0; j < tables[i].size(); j++){
            vector<BM> temp1;
            for(int k = 0; k < tables[i][j].size(); k++){
                BM temp;
                temp.counter = 0;
                temp.value = tables[i][j][k];
                temp1.push_back(temp);
            }
            temp2.push_back(temp1);
        }
        tableBM.push_back(temp2);
    }
}

void outputTables(){
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
            cout << "\t"<< gray[j];
        }
        cout << "\t\t";
    }
    cout << endl;
    for(int i = 0; i < tables.size(); i++){
        for(int j = 0; j < tables[i].size(); j++){
            cout << gray[i] << "\t";
            for(int k = 0; k < tables[i][j].size(); k++){
                cout << tables[j][i][k] << "\t";
            }
            cout << "\t";
        }
        cout << endl;
    }
    cout << endl;
}

void row8(int pointer , vector<coordinates> * temp1){
    int counter = 0;
    int counter1 = 0;
    bool check = false;
    for(int i = 0; i < tables[pointer].size(); i++){
        for(int j = 0; j < tables[pointer][i].size(); j++){
            if(tables[pointer][i][j] == '1' || tables[pointer][i][j] == ' '){
                counter++;
                if(i == 0){
                    counter1++;
                }
                if(tables[pointer][i][j] == '1' && tableBM[pointer][i][j].counter == 0) check = true;
            }else{
                counter = 0;
                check = false;
                break;
            }
        }
        if(counter == 8 && check){
            coordinates temp;
            for(int j = i - 1; j <= i; j++){
                for(int k = 0; k < tables[pointer][j].size(); k++){
                    temp.x.push_back(j);
                    temp.y.push_back(k);
                    tableBM[pointer][j][k].counter++;
                }
            }
            i--;
            temp1->push_back(temp);
            counter = 0;
            check = false;
            //create group for 8
        }
    }
    if(counter1 == counter && counter == 4 && check){
        counter1 = counter = 0;
        coordinates temp;
        for(int i = 0; i < 4; i++){
            temp.x.push_back(3);
            temp.y.push_back(i);
            tableBM[pointer][3][i].counter++;
        }
        for(int i = 0; i < 4; i++){
            temp.x.push_back(0);
            temp.y.push_back(i);
            tableBM[pointer][0][i].counter++;
        }
        temp1->push_back(temp);
    }
}

void column8(int pointer , vector<coordinates> * temp1){
    int counter = 0;
    int counter1 = 0;
    bool check = false;
    for(int i = 0;  i < tables[pointer].size(); i++){
        for(int j = 0; j < tables[pointer][i].size(); j++){
            if(tables[pointer][j][i] == '1' || tables[pointer][j][i] == ' '){
                counter++;
                if(i == 0){
                    counter1++;
                }
                if(tables[pointer][j][i] == '1' && tableBM[pointer][j][i].counter == 0) check = true;
            }else{
                counter = 0;
                check = false;
                break;
            }
        }
        if(counter == 8 && check){
            coordinates temp;
            for(int j = i - 1; j <= i; j++){
                for(int k = 0; k < tables[pointer][j].size(); k++){
                    temp.x.push_back(k);
                    temp.y.push_back(j);
                    tableBM[pointer][k][j].counter++;
                }
            }
            i--;
            temp1->push_back(temp);
            counter = 0;
            check = false;
            //create group for 8
        }
    }
    if(counter1 == counter && counter == 4 && check){
        coordinates temp;
        for(int i = 0; i < tables[pointer][i].size(); i++){
            temp.x.push_back(i);
            temp.y.push_back(0);
            tableBM[pointer][i][0].counter++;
        }
        for(int i = 0; i < tables[pointer][i].size(); i++){
            temp.x.push_back(i);
            temp.y.push_back(3);
            tableBM[pointer][i][3].counter++;
        }
        temp1->push_back(temp);
    }
}

void groupFor8(int pointer , vector<coordinates> * temp){
    row8(pointer , temp);
    column8(pointer , temp);
}

void row4(int pointer , coordinates coordinate , vector<coordinates> * temp1){
    for(int i = 0; i < coordinate.x.size(); i++){
        tableBM[pointer][coordinate.x[i]][coordinate.y[i]].counter++;
    }
    temp1->push_back(coordinate);
}

bool groupingPossibility(int pointer , int x , int y){
    if(tables[pointer][x][y] == '1' || tables[pointer][x][y] == ' '){
        return true;
    }else return false;
}

coordinates addCoordinateForSquare(int x1, int x2 , int y1 , int y2){
    coordinates temp;
    temp.x.push_back(x1);
    temp.y.push_back(y1);
    temp.x.push_back(x1);
    temp.y.push_back(y2);
    temp.x.push_back(x2);
    temp.y.push_back(y1);
    temp.x.push_back(x2);
    temp.y.push_back(y2);
    return temp;
}

bool searchForMeaning(int pointer , int x , int y){
    if(tableBM[pointer][x][y].value == '1' && tableBM[pointer][x][y].counter == 0) return true; else return false;
}

void groupFor4(int pointer , vector<coordinates> * temp1){
    int counter = 0;
    for(int i = 0; i < tables[pointer].size(); i++){
        bool check = false;
        for(int j = 0; j < tables[pointer][i].size(); j++){
            if(tables[pointer][i][j] == '1' || tables[pointer][i][j] == ' '){
                counter++;
                if(tables[pointer][i][j] == '1' && tableBM[pointer][i][j].counter == 0) check = true;
            }else{
                counter = 0;
                break;
            }
        }
        if(counter == 4 && check){
            coordinates temp;
            for(int j = 0; j < tables[pointer][i].size(); j++){
                temp.x.push_back(i);
                temp.y.push_back(j);
            }
            for(int j = 0; j < tables[pointer][i].size(); j++){
                if(tableBM[pointer][i][j].counter == 0 && tableBM[pointer][i][j].value == '1'){
                    row4(pointer , temp , temp1);
                    break;
                }
            }
        }
        counter = 0;
    }
    counter = 0;
    for(int i = 0; i < tables[pointer].size(); i++){
        bool check = false;
        for(int j = 0; j < tables[pointer][i].size(); j++){
            if(tables[pointer][j][i] == '1' || tables[pointer][j][i] == ' '){
                counter++;
                if(tables[pointer][j][i] == '1' && tableBM[pointer][j][i].counter == 0) check = true;
            }else{
                counter = 0;
                break;
            }
        }
        if(counter == 4 && check){
            coordinates temp;
            for(int j = 0; j < tables[pointer][i].size(); j++){
                temp.x.push_back(j);
                temp.y.push_back(i);
            }
            for(int j = 0; j < tables[pointer][i].size(); j++){
                if(tableBM[pointer][i][j].counter == 0 && tableBM[pointer][i][j].value == '1'){
                    row4(pointer , temp , temp1);
                    break;
                }
            }
        }
        counter = 0;
    }
    for(int i = 0; i < tables[pointer].size() - 1; i++){
        for(int j = 0; j < tables[pointer][i].size() - 1; j++){
            if(groupingPossibility(pointer , i , j) && groupingPossibility(pointer , i , j + 1) && groupingPossibility(pointer , i + 1 , j) && groupingPossibility(pointer , i + 1 , j + 1)){
                if(searchForMeaning(pointer , i , j) || searchForMeaning(pointer , i , j + 1) || searchForMeaning(pointer , i + 1 , j) || searchForMeaning(pointer , i + 1 , j + 1)){
                    coordinates temp = addCoordinateForSquare(i ,i + 1 , j , j + 1);
                    row4(pointer , temp , temp1);
                }
            }
        }

        if(groupingPossibility(pointer , i , 0) && groupingPossibility(pointer , i , 3) && groupingPossibility(pointer , i + 1 , 0) && groupingPossibility(pointer , i + 1 , 3)){
            if(searchForMeaning(pointer , i , 0) || searchForMeaning(pointer , i , 3) || searchForMeaning(pointer , i + 1 , 0) || searchForMeaning(pointer , i + 1 , 3)){
                coordinates temp = addCoordinateForSquare(i , i + 1 , 0 , 3);
                row4(pointer , temp , temp1);
            }
        }

        if(groupingPossibility(pointer , 0 , i) && groupingPossibility(pointer , 3 , i) && groupingPossibility(pointer , 0 , i + 1) && groupingPossibility(pointer ,3, i + 1)){
            if(searchForMeaning(pointer , 0 , i) || searchForMeaning(pointer , 3 , i) || searchForMeaning(pointer , 0, i + 1) || searchForMeaning(pointer , 3 , i + 1)){
                coordinates temp = addCoordinateForSquare( 3 , 0 , i , i + 1);
                row4(pointer , temp , temp1);
            }
        }
    }
}

void row2(int pointer , coordinates coordinate){
    for(int i = 0; i < coordinate.x.size(); i++){
        tableBM[pointer][coordinate.x[i]][coordinate.y[i]].counter++;
    }
}

coordinates addCoordinatesFor2(int x1 , int x2 , int y1 , int y2){
    coordinates temp;
    temp.x.push_back(x1);
    temp.x.push_back(x2);
    temp.y.push_back(y1);
    temp.y.push_back(y2);
    return temp;
}

bool checkOfNeed(int pointer , int x , int y){
    if(tableBM[pointer][x][y].counter == 0 && tableBM[pointer][x][y].value == '1') return true; else return false;
}

void groupFor2(int pointer , vector<coordinates> * temp1){
    for(int i = 0; i < tables[pointer].size(); i++){
        for(int j = 0; j < tables[pointer][i].size() - 1; j++){
            if((tables[pointer][i][j] == '1' && tables[pointer][i][j + 1] == ' ') || (tables[pointer][i][j] == ' ' && tables[pointer][i][j + 1] == '1')){
                if(checkOfNeed(pointer , i , j) || checkOfNeed(pointer , i , j + 1)){
                    coordinates temp = addCoordinatesFor2(i , i , j , j + 1);
                    temp1->push_back(temp);
                    row2(pointer , temp);
                }
            }
        }
    }

    for(int i = 0; i < tables[pointer].size() - 1; i++){
        for(int j = 0; j < tables[pointer][i].size(); j++){
            if((tables[pointer][j][i] == '1' && tables[pointer][j][i + 1] == ' ') || (tables[pointer][j][i] == ' ' && tables[pointer][j][i + 1] == '1')){
                if(checkOfNeed(pointer , j , i) || checkOfNeed(pointer , j , i + 1)){
                    coordinates temp = addCoordinatesFor2(j , j , i , i + 1);
                    temp1->push_back(temp);
                    row2(pointer , temp);
                }
            }
        }
    }
    for(int i = 0; i < tables[pointer][0].size(); i++){
        if((tables[pointer][i][0] == '1' && tables[pointer][i][3] == ' ') || (tables[pointer][i][0] == ' ' && tables[pointer][i][3] == '1')){
            if(checkOfNeed(pointer , i , 0) || checkOfNeed(pointer , i , 3)){
                coordinates temp = addCoordinatesFor2(i , i , 3 , 0);
                temp1->push_back(temp);
                row2(pointer , temp);
            }
        }
    }
}

string create(int pointer){
    string result;
    for(int i = 0; i < unionCoordinate[pointer].size(); i++){
        bool check = false;
        for(int j = 0; j < unionCoordinate[pointer][i].x.size(); j++){
            if(tableBM[pointer][unionCoordinate[pointer][i].x[j]][unionCoordinate[pointer][i].y[j]].value == '1' && tableBM[pointer][unionCoordinate[pointer][i].x[j]][unionCoordinate[pointer][i].y[j]].counter == 1){
                check = true;
                break;
            }
        }
        if(!check){
            for(int j = 0; j < unionCoordinate[pointer][i].x.size(); j++){
                tableBM[pointer][unionCoordinate[pointer][i].x[j]][unionCoordinate[pointer][i].y[j]].counter--;
            }
//            unionCoordinate.erase(unionCoordinate.begin() + i);
        }else{
            if(unionCoordinate[pointer][i].x.size() == 8){
                if(unionCoordinate[pointer][i].x[unionCoordinate[pointer][i].x.size() - 1] - unionCoordinate[pointer][i].x[0] == 1 || (unionCoordinate[pointer][i].x[0] == 3 && unionCoordinate[pointer][i].x[3] == 0) || (unionCoordinate[pointer][i].x[0] == 3 && unionCoordinate[pointer][i].x[7] == 0)){
                    result += '+' + rowChar[unionCoordinate[pointer][i].x[0]];
                }else{
                    result += '+' + columnChar[unionCoordinate[pointer][i].y[0]];
                }
            }else{
                if(unionCoordinate[pointer][i].x.size() == 4){
                    if(unionCoordinate[pointer][i].x[unionCoordinate[pointer][i].x.size() - 1] - unionCoordinate[pointer][i].x[0] == 0){
                        result += '+' + row[unionCoordinate[pointer][i].x[0]];
                    }else{
                        if(unionCoordinate[pointer][i].y[unionCoordinate[pointer][i].y.size() - 1] - unionCoordinate[pointer][i].y[0] == 0){
                            result += '+' + column[unionCoordinate[pointer][i].y[0]]; ////
                        }else{
                            result += '+' + rowChar[unionCoordinate[pointer][i].x[0]] + '*'  + columnChar[unionCoordinate[pointer][i].y[0]];
                        }
                    }
                }else{
                    if(unionCoordinate[pointer][i].x[0] == unionCoordinate[pointer][i].x[1]){
                        result += '+' + row[unionCoordinate[pointer][i].x[0]] + '*' + columnChar[unionCoordinate[pointer][i].y[0]];
                    }else result += '+' + rowChar[unionCoordinate[pointer][i].y[0]] + '*' + column[unionCoordinate[pointer][i].x[0]] ;
                }
            }
        }
    }
    result.erase(0,1);
    return result;
}

void createGroup2(int pointer){
    vector<coordinates>  temp;
    groupFor8(pointer , &temp);
    groupFor4(pointer, &temp);
    groupFor2(pointer, &temp);
    unionCoordinate.push_back(temp);
}

string tableMethod2(int pointer){
    string result = create(pointer);
    return result;
}

void outputTableBM(){
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
            cout << "\t"<< gray[j];
        }
        cout << "\t\t";
    }
    cout << endl;
    for(int i = 0; i < tables.size(); i++){
        for(int j = 0; j < tables[i].size(); j++){
            cout << gray[i] << "\t";
            for(int k = 0; k < tables[i][j].size(); k++){
                if(tableBM[j][i][k].value != ' '){
                    cout << tableBM[j][i][k].value << "\t";
                }else{
                    if(tableBM[j][i][k].counter == 0){
                        cout << 0 << "\t";
                    }else cout << 1 << "\t";
                }
            }
            cout << "\t";
        }
        cout << endl;
    }
    cout << endl;
}

void output2(int n){
    table2Inicialization(n);
    tablesInicialuzation();
    initializationTableBM(4);
    outputTable2();
    vector <string> SDNFY = createSDNF(n);
    vector <string> sDNFY;
    cout << endl;
    for(int i = 0; i < SDNFY.size(); i++){
        cout << "SDNF y" << 4 - i << ": " << SDNFY[i] << endl;
    }
    cout << endl;
    outputTables();
    cout << endl;
    for(int i = 0; i < 4; i++) {
        createGroup2(i);
    }

    for(int i = 4; i < 8; i++){
        sDNFY.push_back(tableMethod2(i - 4));
    }
    outputTableBM();
    for(int i = 4; i < 8; i++){
        cout << "sDNF y" << 8 - i << ": " <<sDNFY[i - 4] << endl;
    }
}

int main(){
    output1();
    output2(5);
    return 0;
}

