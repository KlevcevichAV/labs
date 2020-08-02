#include <iostream>
#include <vector>
using namespace std;

struct grapf{
    vector<vector<int>> g;
};

// вывод множества графовв в столбик
void output_to_the_bar(vector<grapf> queue){
    for(int i = 0; i < queue.size(); i++){
        for(int j = 0; j < queue[i].g.size(); j++){
            for(int k = 0; k < queue[i].g[j].size(); k++){
                cout << queue[i].g[j][k] << ' ';
            }
            cout << endl;
        }
        cout << "\n" <<"\n";
    }
}

// вывод множества графовв в строку
void output_to_line(vector<grapf> queue){
    cout << "⎧";
    for(int i = 0; i < queue.size(); i++){
        for(int j = 0; j < queue[i].g.size(); j++){
            if(i == queue.size() - 1 && j == queue[i].g.size() - 1) cout << ' '; else cout << ' ' << ' ';
        }
    }
    cout << "⎫\n";

    for(int i = 0; i < queue[0].g.size(); i++){
        if(queue[0].g.size() / 2 == i)cout << "⎨"; else  cout <<"⎮";
        for(int j = 0; j < queue.size(); j++){
            for(int k = 0; k < queue[0].g.size(); k++){
                if(j == queue.size() - 1 && k == queue[0].g.size() - 1) cout << queue[j].g[i][k];
                else if(i == queue[j].g[i].size() - 1 && k == queue[0].g.size() - 1) cout << queue[j].g[i][k] << ',';
                else cout << queue[j].g[i][k] << ' ';
            }
        }
        if(queue[0].g.size() / 2 == i)cout << "⎬\n"; else cout <<"⎮\n";
    }

    cout << "⎩";
    for(int i = 0; i < queue.size(); i++){
        for(int j = 0; j < queue[i].g.size(); j++){
            if(i == queue.size() - 1 && j == queue[i].g.size() - 1) cout << ' '; else cout << ' ' << ' ';
        }
    }
    cout << "⎭\n";
}

// резервирование памяти для графа
void reserve_graf(grapf *gr , int n){
    gr->g.reserve(n);
    for(int i = 0; i < n; i++){
        gr->g[i].reserve(n);
    }
}

// ввод графа
void inp_graf(grapf *gr){
    for(int i = 0; i < gr->g.capacity(); i++){
        vector<int> ff;
        for(int j = 0; j < gr->g[i].capacity(); j++){
            int f;
            cin >> f;
            ff.push_back(f);
        }
        gr->g.push_back(ff);
    }
}

// формирование множества
vector<grapf> formation_graf(vector<grapf> queue , int uk){
    while (queue.size() != uk){
        grapf q;
        q = queue[uk];
        for(int i = 0; i < q.g.size(); i++){
            for(int j = 0; j < q.g[i].size(); j++){
                if(q.g[i][j] == 1){
                    q.g[i][j] = 0;
                    q.g[j][i] = 0;
                    bool pop = true;
                    for(int k = 0; k < queue.size(); k++){
                        int pop2 = 0;
                        for(int l = 0; l < q.g.size(); l++){
                            if(q.g[l] == queue[k].g[l])pop2++;
                        }
                        if(pop2 == q.g.size())pop = false;
                    }
                    if(pop){
                        queue.push_back(q);
                        queue.reserve(queue.size());
                    }
                    q.g[i][j] = 1;
                    q.g[j][i] = 1;
                }
            }
        }
        uk++;
    }
    return queue;
}

int main() {
    int n;
    cin >> n;
    grapf gr;
    reserve_graf(&gr , n);
    inp_graf(&gr);
    vector<grapf> queue;
    queue.push_back(gr);
    queue.reserve(queue.size());
    queue = formation_graf(queue , 0);
    cout << queue.size() << endl;
//    output_to_the_bar(queue);
    output_to_line(queue);
    return 0;
}

//⎧   ⎫
//⎮   ⎮
//⎨   ⎬
//⎮   ⎮
//⎩   ⎭

//2
//1 0
//0 1