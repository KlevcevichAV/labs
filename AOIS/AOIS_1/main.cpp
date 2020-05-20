#include <iostream>
#include <algorithm>
#include <cmath>


using namespace std;

bool check(string num_1 , string num_2 , int k){
    for(int i = k; i < num_1.length(); i++){
        if(num_1[i] != num_2[i] && num_1[i] == '1'){
            return true;
        }else if(num_1[i] != num_2[i] && num_2[i] == '1') return false;
    }
    return true;
}

string trans(int n ){
    string translated;
    for(int i=0;i<=9;i++) translated+='0';
    if(n<0) {
        translated[9]='1';
        n *= -1;
    }
    int i = 0;
    while(n>0){
        if(n%2!=0){
            translated[i]='1';
        }
        n/=2;
        i++;
    }
    reverse(translated.begin(),translated.end());
    // cout << translated << endl;
    return translated;
}

string add(string num1, string num2){
    string add;
    for(int i=num1.length()-1;i>=0;i--){
        if (num1[i] - '0' + num2[i] - '0' <= 1)add.push_back(num1[i] - '0' + num2[i] );
        else {
            if(num1[i] - '0' + num2[i] - '0' == 3){
                if(i != 0)num1[i - 1] += 1;
                add += '1';
            }
            else {
                num1[i - 1] += 1;
                add += '0';
            }
        }
    }
//    add += '0';
    reverse(add.begin(),add.end());
    //cout <<"adittion of the numbers-"<<add<<endl;
    return add;
}

string transobr(string trans){
    string transobr = trans;
    //  cout << "as ones complement - ";
    if(trans[0]=='0') {
        //    cout << trans << endl;
        return trans;
    }else{
        for(int i=1;i<trans.length();i++){
            if(trans[i]=='0'){
                trans[i]='1';
            }else trans[i]='0';
        }
    }
    transobr = trans;
    //   cout << transobr << endl;
    return transobr;
}

string diff(string num1, string num2){
    string diff;
    for(int i=num1.length() - 1;i>=0;i--) {
        if (num1[i] - '0' <= 0 && num2[i] - '0' == 1 || num1[i] == '/') {
            if(num1[i]=='0') num1[i] = '2'; else num1[i] = '1';
            if(i != 0)num1[i-1]--;
            diff.push_back(num1[i] - num2[i] + '0');
        } else {
            diff.push_back(num1[i] - num2[i] + '0');
        }
    }
    reverse(diff.begin(),diff.end());

    return diff;
}

string  transdop(string transobr){
    string transdop=transobr;
    //  cout << "as two's complement-";
    if(transobr[0]=='0'){
        //   cout << transobr << endl;
        return transobr;
    }else{transobr=add(transobr,"0000000001");
    }
    //cout << transobr << endl;
    return transobr;
}

string multipl(string num1, string num2){
    string multipl;
    int k=0;
    string a="0000000000";
    for(int i = 9; i > 0; i--) {
        string temp="0000000000";
        if(num2[i] == '1') temp = num1;
        temp.erase(0,k);
        for(int i=0;i<k;i++) temp+='0';
        k++;
        a=add(a,temp);

    }
    multipl=a;
    if(num1[0] != num2[0])multipl[0] = '1'; else multipl[0]='0';
    cout <<"multiplition of the numbers-"<< multipl << endl;
    return multipl;
}

void dev(string* num1, string num2, string * devision){
    if(devision->length()==0) {
        num1->erase(0, 1);
        num2.erase(0, 1);
        *devision = "0,";
        num1->push_back('0');
        num1->erase(0,1);
        if(check(*num1,num2,1)){
            *num1 = diff(*num1, num2);
            devision->push_back('1');
        }
        else devision->push_back('0');
    }else{
        if(!check(*num1,num2,1)) {
            num1->push_back('0');
            num1->erase(0,1);
        }
        if(check(*num1,num2,1)){
            *num1 = diff(*num1, num2);
            devision->push_back('1');
        }else devision->push_back('0');
    }
    if(devision->length()<7){
        dev(num1 , num2, devision);
        return;
    }else{
        return;
    }
}


string summa(string num_1 , string num_2){
    if(num_1[0] == num_2[0]){
        if(num_1[0] == '0'){
            return add(num_1 , num_2);
        }else{
            num_1[0] = num_2[0] = '0';
            string sum = add(num_1 , num_2);
            sum[0] = '1';
            return sum;
        }
    }else{
        bool check_znak = true;
        if(num_1[0] != '0') {
            check_znak = false;
            num_1[0] = '0';
        }else num_2[0] = '0';
        if(check(num_1 , num_2 , 1)){
            if(check_znak) {
                return diff(num_1 , num_2);
            }else {
                string sum = diff(num_1 , num_2);
                sum[0] = '1';
                return sum;
            }
        }else{
            if(check_znak){
                string sum = diff(num_2 , num_1);
                sum[0] = '1';
                return sum;
            }else return diff(num_2 ,num_1);
        }
    }
}

string razn(string num_1 , string num_2){
    bool check1 = check(num_1 , num_2 , 1);
    if(num_1[0] == num_2[0]){
        if(num_1[0] == '0'){
            if(check1){
                return diff(num_1 , num_2);
            }else{
                string raz = diff(num_2 , num_1);
                raz[0] = '1';
                return raz;
            }
        }else{
            if(check1){
                string raz = diff(num_1 , num_2);
                raz[0] = '1';
                return raz;
            }else return diff(num_2 , num_1);
        }
    }else{
        if(num_1[0] == '0'){
            return add(num_1 , num_2);
        }else{
            string raz = add(num_1 , num_2);
            raz[0] = '1';
            return raz;
        }
    }
}


string stright_to_binary(string num){
    for(int i=2;i<num.length()-1;i++){
        if(num[i] == '0')num.erase(i--,1); else break;
    }
    return num;
}

string mantissa(string num1, string num2, string man1, string man2){
    string newline, p;
    num1.erase(1,1);
    num2.erase(1,1);
//    newline=diff(num1,num2);
//    int k=0;
//    for(int i=newline.length()-1; i>=0;i--){
//        if(newline[i]=='1'){
//            k+=pow(2,newline.length()-1-i);
//        }
//    }
    if(check(num1,num2,1)){
//        for(int i=0;i<k;i++) {
//            man2.insert(1,"0",1);
//            man1+="0";
//        }
        p=num1;
    }else{
//        for(int i=0;i<k;i++){
//            man1.insert(1,"0",1);
//            man2+="0";
//        }
        p=num2;
    }
    p.insert(1,".");
    string mantissa, answer;
    mantissa=summa(man1,man2);
    mantissa.insert(1,".");
    mantissa = stright_to_binary(mantissa);
    answer=mantissa+"*10^"+p;
    return answer;
}

string check_znak(string num1, string num2){
    if (num1[0] != num2[0]) return "100000000";
    else return "000000000";
}



int main() {
    string number1, number2 , number1_obr , number2_obr , number1_dop , number2_dop;
    int n1 , n2;
    n1 = 13;
    n2 = 23;
    cout << "X1=" << n1 << endl;
    cout << "as a binary digit-" << trans(n1) << endl;
    cout << "as ones complements-" << transobr(trans(n1)) << endl;
    cout << "as two's complements-" << transdop(transobr(trans(n1))) << endl;
    cout << endl;

    cout << "X2=" << n2 << endl;
    cout << "as a binary digit-" << trans(n2) << endl;
    cout << "as ones complements-" << transobr(trans(n2)) << endl;
    cout << "as two's complements-" << transdop(transobr(trans(n2))) << endl;;
    cout << endl;

    cout << "-X1=" << -n1 << endl;
    cout << "as a binary digit-" << trans(-n1) << endl;
    cout << "as ones complements-" << transobr(trans(-n1)) << endl;
    cout << "as two's complements-" << transdop(transobr(trans(-n1))) << endl;
    cout << endl;

    cout << "-X2=" << -n2 << endl;
    cout << "as a binary digit-" << trans(-n2) << endl;
    cout << "as ones complements-" << transobr(trans(-n2)) << endl;
    cout << "as two's complements-" << transdop(transobr(trans(-n2))) << endl;
    cout << endl;

    cout << "X1 + X2\n";
    cout << "addition of the numbers-" << summa(trans(n1), trans(n2))<<endl;
    cout << "addition of the ones complements-" << add(transobr(trans(n1)) , transobr(trans(n2))) << endl;
    cout << "addition of the two's complements-" << add(transdop(transobr(trans(n1))) , transdop(transobr(trans(n2)))) << endl;
    cout << endl;

    cout << "-X1 + X2\n";
    cout << "addition of the numbers-" << summa(trans(-n1), trans(n2))<<endl;
    cout << "addition of the ones complements-" << add(transobr(trans(-n1)) , transobr(trans(n2))) << endl;
    cout << "addition of the two's complements-" << add(transdop(transobr(trans(-n1))) , transdop(transobr(trans(n2)))) << endl;
    cout << endl;

    cout << "-X1 + (-X2)\n";
    cout << "addition of the numbers-" << summa(trans(-n1), trans(-n2))<<endl;
    cout << "addition of the ones complements-" << add(transobr(trans(-n1)) , transobr(trans(-n2))) << endl;
    cout << "addition of the two's complements-" << add(transdop(transobr(trans(-n1))) , transdop(transobr(trans(-n2)))) << endl;
    cout << endl;

    cout << "X1 + (-X2)\n";
    cout << "addition of the numbers-" << summa(trans(n1), trans(-n2))<<endl;
    cout << "addition of the ones complements-" << add(transobr(trans(n1)) , transobr(trans(-n2))) << endl;
    cout << "addition of the two's complements-" << add(transdop(transobr(trans(n1))) , transdop(transobr(trans(-n2)))) << endl;
    cout << endl;

    cout << "X1 - X2\n";
    cout << "difference of the numbers-" << razn(trans(n1) , trans(n2)) << endl;
    cout << "difference of the ones numbers-" << diff(transobr(trans(n1)) , transobr(trans(n2))) << endl;
    cout << "difference of the two's numbers-" << diff(transdop(transobr(trans(n1))) , transdop(transobr(trans(n2)))) << endl;
    cout << endl;

    cout << "-X1 - X2\n";
    cout << "difference of the numbers-" << razn(trans(-n1) , trans(n2)) << endl;
    cout << "difference of the ones numbers-" << diff(transobr(trans(-n1)) , transobr(trans(n2))) << endl;
    cout << "difference of the two's numbers-" << diff(transdop(transobr(trans(-n1))) , transdop(transobr(trans(n2)))) << endl;
    cout << endl;

    cout << "-X1 - (-X2)\n";
    cout << "difference of the numbers-" << razn(trans(-n1) , trans(-n2)) << endl;
    cout << "difference of the ones numbers-" << diff(transobr(trans(-n1)) , transobr(trans(-n2))) << endl;
    cout << "difference of the two's numbers-" << diff(transdop(transobr(trans(-n1))) , transdop(transobr(trans(-n2)))) << endl;
    cout << endl;

    cout << "X1 - (-X2)\n";
    cout << "difference of the numbers-" << razn(trans(n1) , trans(-n2)) << endl;
    cout << "difference of the ones numbers-" << razn(transobr(trans(n1)) , transobr(trans(-n2))) << endl;
    cout << "difference of the two's numbers-" << razn(transdop(transobr(trans(n1))) , transdop(transobr(trans(-n2)))) << endl;
    cout << endl;

    cout << "X1*X2\n";
    multipl(trans(n1) , trans(n2));
    cout << endl;

    cout << "X1*(-X2)\n";
    multipl(trans(n1) , trans(-n2));
    cout << endl;

    cout << "(-X1)*(-X2)\n";
    multipl(trans(-n1) , trans(-n2));
    cout << endl;

    cout << "(-X1)*X2\n";
    multipl(trans(-n1) , trans(n2));
    cout << endl;

    cout << "X1/X2\n";
    string devision;
    string *num_1 = new string;
    *num_1 = trans(n1);
    dev(num_1 , trans(n2), &devision);
    cout << "devision of the numbers-" << check_znak(trans(n1) , trans(n2)) << devision << endl;
    delete num_1;
    cout << endl;

    cout << "(-X1)/X2\n";
    devision = "";
    string *num_2 = new string;
    *num_2 = trans(-n1);
    dev(num_2 , trans(n2), &devision);
    cout << "devision of the numbers-" << check_znak(trans(-n1) , trans(n2)) << devision << endl;
    delete num_2;
    cout << endl;

    cout << "(-X1)/(-X2)\n";
    devision = "";
    string *num_3 = new string;
    *num_3 = trans(-n1);
    dev(num_3 , trans(-n2), &devision);
    cout <<"devision of the numbers-" << check_znak(trans(-n1) , trans(-n2)) << devision  << endl;
    delete num_3;
    cout << endl;

    cout << "X1/(-X2)\n";
    devision = "";
    string *num_4 = new string;
    *num_4 = trans(n1);
    dev(num_4 , trans(-n2), &devision);
    cout <<"devision of the numbers-" << check_znak(trans(n1) , trans(-n2)) << devision << endl;
    delete num_4;
    cout << endl;

    string Mantissa;
    Mantissa=mantissa("0.101","0.100",trans(n1),trans(n2));
    cout<<"X1 + X2 (with floating point)" << endl << Mantissa;
    return 0;

}
//0100101011

//3    -   1  =  2   1 -   3  = -2 +
//3    - (-1) =  4   1 - (-3) =  4
//(-3) -   1  = -4  -1 -   3  = -4
//(-3) - (-1) = -2  -1 - (-3) =  2 +