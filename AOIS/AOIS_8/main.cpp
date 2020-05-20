#include <iostream>
#include <vector>
#include <bitset>

using namespace std;


struct GL{
    bool g;
    bool l;
};

void outputGL(vector<bool> element){
    for(int j = 0; j < element.size(); j++){
        cout << element[j];
    }
    cout << endl;
}

void output(vector<vector<bool>> array){
    for(int i = 0; i < array.size(); i++){
        outputGL(array[i]);
    }
    cout << "\n\n";
}

vector<vector<bool>> createVocabulary(int size){
    vector<vector<bool>> result;
    for(int i = 0; i < size; i++){
        vector<bool> temp;
        for(int j = 0; j < size; j++){
            temp.push_back(rand() % 2);
        }
        result.push_back(temp);
    }
    return result;
}

vector<vector<bool>> diagonalizeArray(vector<vector<bool>> array){
    int counter = 0;
    vector<vector<bool>> diagonalized = array;
    for(int i = 0; i < array.size(); i++){
        for(int j = 0; j < array[i].size(); j++){
            diagonalized[j][i] = array[i][counter++];
            if(j == array.size() - 1) counter--;
            if(counter == array.size()) counter = 0;
        }
    }
    return diagonalized;
}

GL recurrentFunction(vector <bool> word, vector<bool> vocabulary){
    GL result;
    for(int i = 0; i < word.size(); i++){
        bool tempG , tempL;
        (i == 0) ? tempL = false : tempL = result.l;
        (i == 0) ? tempG = false : tempG = result.g;
        result.g = tempG ||(!word[i] && vocabulary[i] && !word[i] && !tempL);
        result.l = tempL ||(word[i] && !vocabulary[i] && word[i] && !tempG);
    }
    return result;
}

vector<vector<bool>> sort(vector<vector<bool>> greater){
    GL temp;
    for(int i  = 0; i < greater.size(); i++){
        for(int j = 0; j < 16; j++){
            temp = recurrentFunction(greater[i] , greater[j]);
            if(temp.l)
                swap(greater[i] , greater[j]);
        }
    }
    return greater;
}

void toF6andF9(vector<bool> firstWord , vector<bool> secondWord){
    vector<bool> result1 = firstWord, result2 = firstWord;
    for(int i = 0; i < firstWord.size(); i++){
        result1[i] = (!firstWord[i] && secondWord[i])||(firstWord[i] && !secondWord[i]);
        result2[i] = (result1[i] && secondWord[i])||(!result1[i] && !secondWord[i]);
    }
    outputGL(result1);
    outputGL(result2);
}

void toF4andF11(vector<bool> firstWord , vector<bool> secondWord){
    vector<bool> result1 = firstWord, result2 = firstWord;
    for(int i = 0; i < firstWord.size(); i++){
        result1[i] = (!firstWord[i] && secondWord[i]);
        result2[i] = (result1[i] || !secondWord[i]);
    }
    outputGL(result1);
    outputGL(result2);
}

string toString(vector<bool> word , int begin){
    string result;
    for(int i = begin; i < begin + 4; i++){
        (word[i]) ? result += '1' : result += '0';
    }
    return result;
}

void fieldAddition(vector<vector<bool>> vocabulary , vector<bool> key){
    cout << "Sum\n";
    for(int i = 0; i < vocabulary.size(); i++){
        if(key[0] == vocabulary[i][0] && key[2] == vocabulary[i][2] && key[1] == vocabulary[i][1] ){
            string result = bitset<32>(bitset<32>(toString(vocabulary[i],3)).to_ulong() + bitset<32>(toString(vocabulary[i],7)).to_ulong()).to_string();
            result.erase(0 , 26);
            for(int j = 10; j < vocabulary[i].size(); j++){
                (result[j - 10] == '0') ? vocabulary[i][j] = false : vocabulary[i][j] = true;
            }
        }
    }
    output(vocabulary);
}

int main(){
    cout << "output vocabulary :\n";
    vector<vector<bool>> vocabulary = createVocabulary(16);
    output(vocabulary);

    cout << "output diagonalize vocabulary :\n";
    vocabulary = diagonalizeArray(vocabulary);
    output(vocabulary);

    cout << "output sort vocabulary :\n";
    vector<vector<bool>> sortVocabulary = sort(vocabulary);
    output(sortVocabulary);

    cout << "output to F6 and F9 :\n";
    toF6andF9(sortVocabulary[0] , sortVocabulary[1]);
    cout << "output to F4 and F11 :\n";
    toF4andF11(sortVocabulary[0] , sortVocabulary[1]);

    cout << "\n\n\n";

    vector<bool> key = {true , true , true};
    fieldAddition(sortVocabulary , key);
    return 0;
}