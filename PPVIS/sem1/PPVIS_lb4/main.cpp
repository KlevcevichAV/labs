#include <iostream>
#include <vector>
#include <algorithm>
#define outputBranch cout<<"\n\n"
using namespace std;

// tournament sort

template <typename type>
int sizeArray(type& array){
    return sizeof(array)/ sizeof(array[0]);
}


template <typename T>
struct Couple{
    T element1;
    T element2;
    bool check;
    Couple(T element1 , T element2 , bool check = false){
        this->element1 = element1;
        this->element2 = element2;
        this->check = check;
    }
};

template <typename type>
void gridInitialization(vector<Couple<type>>& grid , vector<type> array){
    for(int i = 0; i < array.size(); i+=2){
        if(i == array.size() - 1){
            grid.push_back(*new Couple<type>(array[i] , array[i] , true));
        } else{
            grid.push_back(*new Couple<type>(array[i] , array[i + 1]));
        }
    }
}

template <typename type>
void minimumSelection(vector<Couple<type>> grid , vector<type>& array){
    for(int i = 0; i < grid.size(); i++){
        if(grid[i].check){
            array.push_back(grid[i].element1);
            continue;
        }
        if(grid[i].element1 < grid[i].element2)array.push_back(grid[i].element1);
        else array.push_back(grid[i].element2);
    }
}
template <typename type1 , typename  type2>
void erase(type1& array , type2 element){
    type1 result;
    int counter = 0;
    bool check = true;
    for(int i = 0; i < sizeArray(array); i++){
        if(array[i] == element){
            if(check)check = false; else result[counter] = array[i];
        }else result[counter] = array[i];
    }
    array = result;
}

template <typename type1 , typename  type2>
void tournamentSort(type1& array1 , type2 t , int size){
    vector<type2> result;
    vector<type2> array(begin(array1) , end(array1));

    while(!array.empty()){
        vector<type2> tempArray = array;
        vector<Couple<type2>> tournamentGrid;
        gridInitialization(tournamentGrid , array);
        while (tempArray.size() != 1){
            vector<type2> temp;
            minimumSelection(tournamentGrid , temp);
            tournamentGrid.clear();
            if(temp.size() == 1){
                result.push_back(temp[0]);
                array.erase(find(array.begin() , array.end() , temp[0]));
                break;
            }
            gridInitialization(tournamentGrid , temp);
        }
        if(array.size() == 1){
            result.push_back(array[0]);
            break;
        }
    }
    for(int i = 0; i < size; i++){
        array1[i] = result[i];
    }
}


//combSort

int getNextGap(int gap)
{
    gap = (gap * 10) / 13;

    if (gap < 1)
        return 1;
    return gap;
}

template <typename type>
void combSort(type& a, int n)
{
    int gap = n;
    bool swapped = true;

    while (gap != 1 || swapped == true) {
        gap = getNextGap(gap);
        swapped = false;
        for (int i = 0; i < n - gap; i++) {
            if (a[i] > a[i + gap]) {
                swap(a[i], a[i + gap]);
                swapped = true;
            }
        }
    }
}


class MyClass{
public:
    MyClass(){
        letter = '1';
    }

    MyClass(char symbol){
        letter = symbol;
    }
    operator int(){
        return letterDefinition(letter);
    }

    bool operator>(const MyClass &second) { return letterDefinition(letter) > letterDefinition(second.letter); }
    bool operator<(const MyClass &second) { return letterDefinition(letter) < letterDefinition(second.letter); }
    bool operator==(const MyClass &second) { return letter == second.letter; }

    char letter;

    char alphabet[26] = {'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' , 'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 'X' , 'Y' , 'Z'};

    int letterDefinition(char symbol){
        for(int i = 0; i < 26; i++){
            if(alphabet[i] == symbol)return i + 1;
        }
        return 0;
    }

};
template <typename type>
void outputMyClass(type a, int n)
{
    for (int i = 0; i < n; i++) cout << a[i].letter << ' ';
    cout << endl;
}

template <typename type>
void output(type a, int n)
{
    for (int i = 0; i < n; i++) cout << a[i] << ' ';
    cout << endl;
}


int main()
{
    int array1_1[] = {1 , 2 , 5 , 40 , 12 , 6};
    double array2_1[] = {1.0 , 2.5 , 2.4 , 40.3 , 12 , 6};
    MyClass array3_1[] = {'A' , 'R' , 'W' , 'G' , 'B'};

    output(array1_1 , sizeArray(array1_1));
    tournamentSort(array1_1 , array1_1[0], sizeArray(array1_1));
//    combSort(array1_1 , sizeArray(array1_1));
    output(array1_1 , sizeArray(array1_1));

    outputBranch;

    output(array2_1 , sizeArray(array2_1));
    combSort(array2_1 , sizeArray(array2_1));
//    tournamentSort(array2_1 , array2_1[0], sizeArray(array2_1));
    output(array2_1 , sizeArray(array2_1));

    outputBranch;

    outputMyClass(array3_1 , sizeArray(array3_1));
    combSort(array3_1 , sizeArray(array3_1));
//    tournamentSort(array3_1 , array3_1[0], sizeArray(array3_1));
    outputMyClass(array3_1 , sizeArray(array3_1));

    outputBranch;

    vector<int> array1_2 = {1 , 2 , 5 , 40 , 12 , 6};
    vector<double> array2_2 = {1.0 , 2.5 , 2.4 , 40.3 , 12 , 6};
    vector<MyClass> array3_2 = {'A' , 'R' , 'W' , 'G' , 'B'};

    output(array1_2 , array1_2.size());
    tournamentSort(array1_2 , array1_2[0], array1_2.size());
//    combSort(array1_2 , array1_2.size());
    output(array1_2 , array1_2.size());

    outputBranch;

    output(array2_2 , array2_2.size());
    tournamentSort(array2_2 ,array2_2[0], 6);
//    combSort(array1_2 , array1_2.size());
    output(array2_2 , array2_2.size());

    outputBranch;

    outputMyClass(array3_2 , array3_2.size());
//    tournamentSort(array3_2 , array3_2[0] , array3_2.size());
    combSort(array3_2 , array3_2.size());
    outputMyClass(array3_2 , array3_2.size());


    return 0;
}