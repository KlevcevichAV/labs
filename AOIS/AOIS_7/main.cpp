#include <iostream>
#include <vector>
using namespace std;

struct GL{
    bool g;
    bool l;
    vector <bool> word;
    GL(){
        for(int i = 0; i < 8; i++){
            word.push_back(rand() % 2);
        }
    }
};

vector<GL> createVocabulary(int size){
    vector<GL> vocabulary;
    for(int i = 0; i < size; i++){
        GL temp;
        vocabulary.push_back(temp);
    }
    return vocabulary;
}

GL recurrentFunction(vector <bool> word, GL vocabulary){
    for(int i = 0; i < word.size(); i++){
        bool tempG , tempL;
        (i == 0) ? tempL = false : tempL = vocabulary.l;
        (i == 0) ? tempG = false : tempG = vocabulary.g;
        vocabulary.g = tempG ||(!word[i] && vocabulary.word[i] && !word[i] && !tempL);
        vocabulary.l = tempL ||(word[i] && !vocabulary.word[i] && word[i] && !tempG);
    }
    return vocabulary;
}

vector<bool> stringToVBool(string word){
    vector <bool> wordB;
    for(int i = 0; i < 8; i++){
        wordB.push_back(word[i] -'0');
    }
    return wordB;
}

vector<GL> searchGreaterWords(vector<GL> vocabulary){
    vector<GL> greater;
    for(int i = 0; i < vocabulary.size(); i++){
        if(vocabulary[i].g){
            greater.push_back(vocabulary[i]);
        }
    }
    return greater;
}

vector<GL> searchLesserWords(vector<GL> vocabulary){
    vector<GL> lesser;
    for(int i = 0; i < vocabulary.size(); i++){
        if(vocabulary[i].l){
            lesser.push_back(vocabulary[i]);
        }
    }
    return lesser;
}

vector <GL> function(vector <GL> vocabulary , string word){
    vector <bool> Word = stringToVBool(word);
    for(int i = 0; i < vocabulary.size(); i++){
        vocabulary[i] = recurrentFunction(Word , vocabulary[i]);
    }
    return vocabulary;
}

vector <GL> findMinNearWords(vector<GL> greater){
    int pointer1 = 0 , pointer2 = 1;
    GL temp = greater[0];
    while (greater.size() > pointer2 && (temp.g ^ temp.l)){
        temp = recurrentFunction(greater[pointer2].word , greater[pointer1]);
        (!temp.g) ? greater.erase(greater.begin() + pointer2) : greater.erase(greater.begin() + pointer1);
        if(pointer2 != greater.size() - 1 && !(temp.g ^ temp.l)) pointer2++;
    }
    return greater;
}

vector <GL> findMaxNearWords(vector<GL> greater){
    int pointer1 = 0 , pointer2 = 1;
    GL temp = greater[0];
    while (greater.size() > pointer2 && (temp.g ^ temp.l)){
        temp = recurrentFunction(greater[pointer2].word , greater[pointer1]);
        (temp.g) ? greater.erase(greater.begin() + pointer2) : greater.erase(greater.begin() + pointer1);
        if(pointer2 != greater.size() - 1 && !(temp.g ^ temp.l)) pointer2++;
    }
    return greater;
}

void outputGL(vector <GL> vocabulary , bool check){
    if(check) cout << "#\t word \t G L\n";
    for(int i = 0; i < vocabulary.size(); i++){
        if(check) cout << i + 1 << "\t";
        for(int j = 0; j < 8; j++){
            cout << vocabulary[i].word[j];
        }
        if(check)cout << ' ' << vocabulary[i].g << ' ' << vocabulary[i].l;
        cout << "\n";
    }
}

void searchWord(vector<GL> vocabulary){
    vector<GL> greater = searchGreaterWords(vocabulary);
    vector<GL> lesser = searchLesserWords(vocabulary);
    greater = findMinNearWords(greater);
    cout << "Nearest bottom :\n";
    outputGL(greater , false);
    lesser = findMaxNearWords(lesser);
    cout << "Nearest top :\n";
    outputGL(lesser , false);
}

int main(){
    vector<GL> vocabulary = createVocabulary(25);
    string word;
    cout << "Input word :";
    cin >> word;
    vocabulary = function(vocabulary , word);
    outputGL(vocabulary , true);
    searchWord(vocabulary);
    return 0;
}