#include <iostream>
#include <vector>
#include <string>
#include <cmath>

using namespace std;
class logic_formula{
    struct function_contents{
        bool x1;
        bool x2;
        bool x3;
        bool f;
    };
    function_contents table[8];
    string formula;
    string SDNF;
    string SKNF;
    string binary_SDNF;
    string binary_SKNF;
    string decimal_SDNF;
    string decimal_SKNF;
    int index;


    void table_initialization(){
        table[0].x1 = 0;
        table[0].x2 = 0;
        table[0].x3 = 0;

        table[1].x1 = 1;
        table[1].x2 = 0;
        table[1].x3 = 0;

        table[2].x1 = 0;
        table[2].x2 = 1;
        table[2].x3 = 0;

        table[3].x1 = 0;
        table[3].x2 = 0;
        table[3].x3 = 1;

        table[4].x1 = 1;
        table[4].x2 = 1;
        table[4].x3 = 0;

        table[5].x1 = 0;
        table[5].x2 = 1;
        table[5].x3 = 1;

        table[6].x1 = 1;
        table[6].x2 = 0;
        table[6].x3 = 1;

        table[7].x1 = 1;
        table[7].x2 = 1;
        table[7].x3 = 1;
    }

    bool truth_table_filling(function_contents f , string formula){
        if(formula.length() == 1){
            if(formula.length() == 1 && formula == "a") return f.x1;
            if(formula.length() == 1 && formula == "b") return f.x2;
            if(formula.length() == 1 && formula == "c") return f.x3;
        }

        if(formula[0] == '!' && formula.length() == 2 ){
            string temp =formula.substr(1,formula.length());
            return !(truth_table_filling(f , temp));

        }
        if(formula[0] == '!' && formula[1] == '(' ){
            int i = 2;
            int counter = 1;
            while (counter != 0){
                if('(' == formula[i]) counter++;
                if(')' == formula[i]) counter--;
                i++;
            }
            if(i == formula.length()){
                string temp =formula.substr(1,formula.length());
                return !(truth_table_filling(f , temp));
            }else{
                if(formula[i] == '*'){
                    return truth_table_filling(f , formula.substr(0,i)) && truth_table_filling(f , formula.substr(i + 1 , formula.length() - i));
                }else{
                    return truth_table_filling(f , formula.substr(0,i)) || truth_table_filling(f , formula.substr(i + 1 , formula.length() - i));
                }
            }

        }
        if(formula[0] == '('){
            int i = 1;
            int counter = 1;
            while (counter != 0){
                if('(' == formula[i]) counter++;
                if(')' == formula[i]) counter--;
                i++;
            }
            if(i == formula.length()){
                string temp =formula.substr(1,formula.length() - 2);
                return truth_table_filling(f , temp);
            }else{
                if(formula[i] == '+'){
                    return truth_table_filling(f , formula.substr(0,i)) || truth_table_filling(f , formula.substr(i + 1 , formula.length() - i));
                }else{
                    return truth_table_filling(f , formula.substr(0,i)) && truth_table_filling(f , formula.substr(i + 1 , formula.length() - i));
                }
            }
        }
        int ind_sign = formula.find("*");
        if(ind_sign == -1){
            ind_sign = formula.find("+");
            return truth_table_filling(f , formula.substr(0 , ind_sign)) || truth_table_filling(f , formula.substr(ind_sign + 1 , formula.length() - (ind_sign)));
        } else{
            return truth_table_filling(f , formula.substr(0 , ind_sign)) && truth_table_filling(f , formula.substr(ind_sign + 1 , formula.length() - (ind_sign)));
        }
    }

public:
    logic_formula(string formula){
        this->formula = formula;

        table_initialization();

        for(int i = 0; i < 8; i++){
            table[i].f = truth_table_filling(table[i] , formula);
        }

        for(int i = 0; i < 8; i++){
            if(table[i].f == 1){
                if(!table[i].x1) SDNF += '!';
                SDNF += "a*";

                if(!table[i].x2) SDNF += '!';
                SDNF += "b*";

                if(!table[i].x3) SDNF += '!';
                SDNF += "c";

                SDNF += '+';

            }
        }
        SDNF.erase(SDNF.length() -1 ,1);

        for(int i = 0; i < 8; i++){
            if(table[i].f == 0){
                SKNF += '(';
                if(table[i].x1) SKNF += '!';
                SKNF += "a+";

                if(table[i].x2) SKNF += '!';
                SKNF += "b+";

                if(table[i].x3) SKNF += '!';
                SKNF += "c";
                SKNF += ')';
                SKNF += '*';

            }
        }
        SKNF.erase(SKNF.length() -1 ,1);

        for(int i = 0; i < 8; i++){
            if(table[i].f == 1){
                if(!table[i].x1) binary_SDNF +='0'; else binary_SDNF +='1';
                if(!table[i].x2) binary_SDNF +='0'; else binary_SDNF +='1';
                if(!table[i].x3) binary_SDNF +='0'; else binary_SDNF +='1';
                binary_SDNF += '+';
            }
        }
        binary_SDNF.erase(binary_SDNF.length() - 1 ,1);

        for(int i = 0; i < 8; i++){
            if(!table[i].f){
                if(!table[i].x1) binary_SKNF +='0'; else binary_SKNF +='1';
                if(!table[i].x2) binary_SKNF +='0'; else binary_SKNF +='1';
                if(!table[i].x3) binary_SKNF +='0'; else binary_SKNF +='1';
                binary_SKNF += '*';
            }
        }
        binary_SKNF.erase(binary_SKNF.length() -1 ,1);

        int ans = 0;
        int f = 7;
        for(int i = 7; i >= 0; i--,f-=2){
            if(table[i].f){
                ans += pow(2,i-f);
            }
        }
        index = ans;

        for(int i = 0; i < 8; i++){
            int ans = 0;
            if(table[i].f){
                if(table[i].x1) ans += pow(2,2);
                if(table[i].x2) ans += pow(2,1);
                if(table[i].x3) ans += 1;
                decimal_SDNF += ans + '0';
                decimal_SDNF += ',';
            }else{
                if(table[i].x1) ans += pow(2,2);
                if(table[i].x2) ans += pow(2,1);
                if(table[i].x3) ans += 1;
                decimal_SKNF += ans + '0';
                decimal_SKNF += ',';
            }
        }
        decimal_SDNF.erase(decimal_SDNF.length() -1 , 1);
        decimal_SKNF.erase(decimal_SKNF.length() -1 , 1);

    }

    string print_table(){
        cout << "#" << '\t' << "1" << '\t' << "2" << '\t' << "3"
             << '\t' << "4" << '\t' << "5" << '\t' << "6" << '\t'
             << "7" << '\t' << "8";
        cout << endl << "___________________________________________________________________";
        cout << endl << "a" << '\t';
        for (int i = 0; i < 8; i++)
        {
            cout << table[i].x1 << "\t";
        }
        cout << endl << "b" << '\t';
        for (int i = 0; i < 8; i++)
        {
            cout << table[i].x2 << "\t";
        }
        cout << endl << "c" << '\t';
        for (int i = 0; i < 8; i++)
        {
            cout << table[i].x3 << "\t";
        }
        cout << endl << "___________________________________________________________________";
        cout << endl << "f" << '\t';
        for (int i = 0; i < 8; i++)
        {
            cout << table[i].f << "\t";
        }
        cout << endl;
    }

    void print_SDNF(){
        cout << "SDNF = " << SDNF << endl;
    }

    void print_SKNF(){
        cout << "SKNF = " << SKNF << endl;
    }

    void print_binary_SDNF(){
        cout << "Binary SDNF = " << binary_SDNF << endl;
    }

    void print_binary_SKNF(){
        cout << "Binary SKNF = " << binary_SKNF << endl;
    }

    void print_decimal_SDNF(){
        cout << "Decimal SDNF = ⋁(" << decimal_SDNF << ")\n";
    }

    void print_decinal_SKNF(){
        cout << "Decimal SKNF = ⋀(" << decimal_SKNF << ")\n";
    }

    void print_index(){
        cout << "Index = " << index << endl;
    }

};

int main()
{
    string formula;
    cout << "Enter the formula: ";
    cin >>  formula;
    logic_formula a(formula);

    a.print_table();
    a.print_SDNF();
    a.print_SKNF();
    a.print_binary_SDNF();
    a.print_binary_SKNF();
    a.print_decimal_SDNF();
    a.print_decinal_SKNF();
    a.print_index();
    return 0;
}

//!((!a+b)*!(b*!c))