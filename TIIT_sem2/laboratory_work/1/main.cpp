#include <fstream>
#include "fenwick_tree.h"
using namespace std;

ifstream cin("input3.txt");
ofstream cout("output.txt");

int main() {
    int n , m;
    cin >> n >> m;
    fenwick_tree t;
    t = init(n , t);
    for(int i = 0; i < m; i++){
        string s;
        cin >> s;
        if(s[0] == 'a'){
            int pos , d;
            cin >> pos >> d;
            add(pos , d , t);
        }else {
            int left , right;
            cin >> left >> right;
            cout << out_sum(left , right , t) << endl;
        }
    }
    return 0;
}