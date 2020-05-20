#include <fstream>
#include <vector>
using namespace std;

ifstream cin("input3.txt");
ofstream cout("output3.txt");


struct lots{
    string element;
    lots *elementss[20];
    int siz = 0;
};

lots * add_elements_to_set(string s , lots*p){
    lots*t = new lots;
    if(s[0] == '{' && s.length() != 2){
        string str;
        for(int i = 1; i < s.length(); i++){
            if(s[i] == '{' && s.length() != 2){
                int ans = 1;
                str +=s[i++];
                while(ans!=0){
                    if(s[i] == '{')ans++;
                    if(s[i] == '}')ans--;
                    str+= s[i++];
                }
                t->elementss[t->siz++] = add_elements_to_set(str , t->elementss[t->siz]);
                t->element = s;
                str ="";
            }else{
                if(s[i] == '<'){
                    int ans = 1;
                    str +=s[i++];
                    while(ans!=0){
                        if(s[i] == '<')ans++;
                        if(s[i] == '>')ans--;
                        str+= s[i++];
                    }
                    t->elementss[t->siz++] = add_elements_to_set(str , t->elementss[t->siz]);
                    t->element = s;
                    str = "";
                }else if(s[i] == ',' || i == s.length() - 1){
                    t->elementss[t->siz++] = add_elements_to_set(str , t->elementss[t->siz]);
                    t->element = s;
                    str = "";
                }else str += s[i];
            }
        }
    }else {
        t->element = s;
    }
    return t;
}

bool validation_check(string s){
    vector<int> stack;
    stack.reserve(s.length());
    char bracket1 = '{';
    char bracket2 = '<';
    char bracket3 = '}';
    char bracket4 = '>';
    int uk = 0;

    if(s[0] != bracket1) return false;

    for(int i = 0; i < s.length(); i++){
        if(s[i] == bracket1){
            stack.push_back(1);
            uk++;
        }else if(s[i] == bracket2){
            stack.push_back(2);
            uk++;
        }else if(s[i] == bracket3){
            uk--;
            if(stack[uk] == 1){
                stack.pop_back();
            }else return false;
        }else if(s[i] == bracket4){
            uk--;
            if(stack[uk] == 2){
                stack.pop_back();
            }else return false;
        }
    }

    if(stack.size() == 0) return true; else return false;
}

lots*input(lots *f){
    char c , d;
    cin >> c;
    string s;
    cin >> d;
    cin >> s;
    if(!validation_check(s)){
        input(f);
    }
    f = add_elements_to_set(s , f);
    f->element = c;
    return f;
}

bool Fine(lots Ob , lots a){
    if(a.siz == 0){
        for(int i = 0; i < Ob.siz; i++){
            if(Ob.elementss[i]->element == a.element) return false;
        }
        return true;
    }

    for(int i = 0; i < Ob.siz; i++){
        if(Ob.elementss[i]->siz == a.siz){
            for(int j = 0; j < a.siz; j++){
                if(!Fine(*Ob.elementss[i] , *a.elementss[j])) return false;
            }
            return true;
        }
    }
    return true;
}

void output(lots a){
    cout << a.element << '='<<'{';
    for(int i = 0; i < a.siz; i++){
        cout << a.elementss[i]->element;
        if(a.siz - 1 != i){
            cout<<',';
        }
    }
    cout << '}' << endl;
}

//void output(lots a){
//    cout << a.element << '='<<'{';
//    for(int i = 0; i < a.siz; i++){
//        if(a.elementss[i]->siz == 0){
//            cout << a.elementss[i]->element;
//            if(a.siz - 1 != i){
//                cout <<',';
//            }else cout << '}';
//        }else{
//            output(*a.elementss[i]);
//            if(i != a.siz - 1){
//                cout <<',';
//            }else cout << '}';
//        }
//    }
//}


int main() {

    int choice = 1;
    lots*a[20];
    int size = 0;

    while(choice == 1){
        cin >> choice;
        if(choice == 1){
            a[size++] = input(a[size]) ;

        }
    }

    lots Union;
    Union.element = "Union";

    for(int i = 0; i < size; i++){
        for(int j = 0; j < a[i]->siz; j++){
            if(Fine(Union , *a[i]->elementss[j])){
                Union.elementss[Union.siz++] = add_elements_to_set(a[i]->elementss[j]->element , &Union);
            } 
        }
    }

//    for(int i = 0; i < size; i++){
//        output(*a[i]);
//    }
    output(Union);
    return 0;
}
