#include <iostream>
#include <math.h>
#include <vector>

using namespace std;

struct Flags{
    string key;
    string data;
    bool unavailable;
    bool delining;
    bool terminate;
    Flags *  next;
    Flags(){
        unavailable = false;
        delining = false;
        terminate = false;
        next = NULL;
    }
};

struct Hash{
    int size;
    int value;
    int hashCode;
    bool collision;
    vector <Flags> flags;
    Hash(){
        size = 0;
        value = -1;
        collision = false;
        flags.reserve(20);
    }
};

class HashTable{
    vector<Hash> hashTable;

    int charToInt(char symbol){
        int result;
        (symbol == ' ') ? result = 0 : (symbol > 'a' - 1) ? result = symbol - 'a' : result = symbol - 'A';
        return result;
    }

    int createValue(string key){
        int result = 0;
        for(int i = 0; i < 2; i++){
            result += charToInt(key[i]) * pow(33 , i);
        }
        return result;
    }
public:
    HashTable(){
        hashTable.reserve(675);

    }

    void addElement(string key , string data){
        int tempValue = createValue(key);
        int tempHashCode = tempValue % 20;
        if(hashTable[tempHashCode].flags.empty()){
            hashTable[tempHashCode].value = tempValue;
            hashTable[tempHashCode].hashCode = tempHashCode;
            Flags temp;
            temp.data = data;
            temp.key = key;
            temp.unavailable = true;
            temp.terminate = true;
            hashTable[tempHashCode].flags.push_back(temp);
            hashTable[tempHashCode].size = 1;
            return;
        }
        for(int i = 0; i < hashTable[tempValue].size; i++){
            if(hashTable[tempHashCode].flags[i].key == key){
                if(hashTable[tempHashCode].flags[i].unavailable == false){
                    hashTable[tempHashCode].flags[i].unavailable = true;
                    hashTable[tempHashCode].flags[i].delining = false;
                    if(!hashTable[tempHashCode].collision) hashTable[tempHashCode].collision = true;
                }else{
                    cout << "Element already exists!\n";
                }
                return;
            }
        }
        hashTable[tempHashCode].flags.push_back(*new Flags);
        hashTable[tempHashCode].size++;
        hashTable[tempHashCode].flags[hashTable[tempHashCode].size - 1].key = key;
        hashTable[tempHashCode].flags[hashTable[tempHashCode].size - 1].unavailable = true;
        hashTable[tempHashCode].flags[hashTable[tempHashCode].size - 1].data = data;
        hashTable[tempHashCode].flags[hashTable[tempHashCode].size - 2].next = &hashTable[tempHashCode].flags[hashTable[tempHashCode].size - 1];
        hashTable[tempHashCode].collision = true;
    }

    void search(string key){
        int tempValue = createValue(key);
        int tempHashCode = tempValue % 20;
        for(int i = 0; i < hashTable[tempHashCode].size; i++){
            if(hashTable[tempHashCode].flags[i].key == key) {
                cout << hashTable[tempHashCode].flags[i].data << '\n';
                return;
            }
        }
        cout << "Not found!\n";

    }

    void deleteElement(string key){
        int tempValue = createValue(key);
        int tempHashCode = tempValue % 20;
        for(int i = 0; i < hashTable[tempHashCode].size; i++){
            if(key == hashTable[tempHashCode].flags[i].key){
                if(hashTable[tempHashCode].flags[i].unavailable){
                    hashTable[tempHashCode].flags[i].unavailable = false;
                    hashTable[tempHashCode].flags[i].delining = true;
                    if(hashTable[tempHashCode].size == 2){
                        hashTable[tempHashCode].collision = false;
                    }
                    if(i == 0){
                        hashTable[tempHashCode].flags[i].next = NULL;
                    }else if(i == hashTable[tempHashCode].size - 1){
                        hashTable[tempHashCode].flags[i-1].next = NULL;
                    }else{
                        hashTable[tempHashCode].flags[i-1].next = hashTable[tempHashCode].flags[i].next;
                        hashTable[tempHashCode].flags[i].next = NULL;
                    }
                }else cout << "Element has already been deleted!\n";
                return;
            }
        }
        cout << "Element doesn't exist!\n";
    }
    void output(){
        cout << "id\t\tvalue\thash code\tcollision\tunavailable\tdelining\tterminate\t next\t\t\tdata\n";
        for(int i = 0; i < 675; i++){
            if(hashTable[i].value == -1) continue;
            if(hashTable[i].size > 1){
                //cout <<"★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★C O L L I S I O N★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n";
                cout <<"☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀С   ҉ Ḻ Ḻ I S I   ҈ N☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀\n";
            }
            for(int j = 0; j < hashTable[i].size; j++){
                cout << hashTable[i].flags[j].key << "\t" << hashTable[i].value << "\t\t" << hashTable[i].hashCode << "\t\t\t" << hashTable[i].collision << "\t\t\t" << hashTable[i].flags[j].unavailable << "\t\t\t" << hashTable[i].flags[j].delining << "\t\t\t" << hashTable[i].flags[j].terminate << "\t\t\t";
                (hashTable[i].flags[j].next == NULL) ? cout << "00000000000000" : cout << hashTable[i].flags[j].next;
                cout << "\t" << hashTable[i].flags[j].data << '\n';
            }
            //if(hashTable[i].size > 1)cout <<"★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n";
            if(hashTable[i].size > 1)cout <<"☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀☀\n";
        }
    }
};

int main() {
    HashTable table;

    return 0;
}


/*
 * table.addElement("Addition" ,"Arithmetic");
    table.addElement("Line" , "Geometry");
    table.addElement("Division" , "Arithmetic");
    table.addElement("Limit" , "Analysis");
    table.addElement("Set","Discrete");
    table.addElement("Equation","Algebra");
    table.addElement("Multiplication","Arithmetic");
    table.addElement("Combinatorics","Discrete");
    table.addElement("Graph","Discrete");
    table.addElement("Differencial","Analysis");
    table.addElement("Subtraction","Arithmetics");
    table.addElement("Dimension","Geometry");
    table.addElement("Tensor","Algebra");
    table.addElement("Function","Analysis");
    table.addElement("Ring","Algebra");
    table.addElement("Statement","Discrete");
    table.addElement("Symmetry","Geometry");
    table.addElement("Vector","Algebra");
    table.addElement("Logic","Discrete");
    table.addElement("Sequence" ,"Analysis");
    table.output();
    cout << "\n\n\n";
    table.deleteElement("Logic");
    table.output();
    table.search("Ring");


    table.addElement("Love" ,"Verb");
    table.addElement("Beautiful" , "Adjective");
    table.addElement("Five" , "Numeral");
    table.addElement("Sun" , "Noun");
    table.addElement("Or","Conjuction");
    table.addElement("Immidiatley","Adverb");
    table.addElement("Sunday","Noun");
    table.addElement("Chair","Noun");
    table.addElement("Bee","Noun");
    table.addElement("Immortal","Adjective");
    table.addElement("Want","Verb");
    table.addElement("And","Conjuction");
    table.addElement("Ant","Noun");
    table.addElement("Hardly","Adverb");
    table.addElement("Seven","Numeral");
    table.addElement("Green","Adjective");
    table.addElement("Support","Verb");
    table.addElement("Twenty","Numeral");
    table.addElement("But","Conjunction");
    table.addElement("Very" ,"Adverb");
    table.output();
    cout << "\n\n\n";
    table.deleteElement("But");
    table.output();
    table.search("Very");











 *
 */