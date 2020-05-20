#include <fstream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

ifstream cin("input.txt");
ofstream cout("output.txt");

int main() {
    for(int f = 0; f < 11; f++) {
        bool sad = true;
        int n;
        cin >> n;
        string name;
        cin >> name;
        string dices[n];
        for (int i = 0; i < n; i++) {
            cin >> dices[i];
        }

        int nV = 2 * n + 2;
        int s = nV - 2;
        int t = nV - 1;
        vector<vector<int>> graf(nV, vector<int>(nV, 0));
        vector<vector<int>> flow(nV, vector<int>(nV, 0));

        for (int i = 0; i < n; i++) {
            graf[s][i] = 1;
        }
        for (int i = 0; i < n; i++) {
            graf[n + i][t] = 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dices[i].find(name[j]) != string::npos) {
                    graf[i][n + j] = 1;
                }
            }
        }

        vector<bool> visit(nV);
        vector<int> from(nV);
        queue<int> q;

        for (int i = 0; i < name.length(); i++) {
            visit.assign(nV, false);
            q.push(s);
            visit[s] = true;
            while (!q.empty()) {
                int pol = q.front();
                q.pop();
                for (int j = 0; j < nV; j++) {
                    if (!visit[j]) {
                        if (flow[pol][j] < graf[pol][j]) {
                            q.push(j);
                            visit[j] = true;
                            from[j] = pol;
                        }
                    }
                }
            }

            if (!visit[t]) {
                cout << "NO" << endl;
                sad = false;
                break;
            }

            int pol = t;
            while (pol != s) {
                int prev = from[pol];
                flow[prev][pol]++;
                flow[pol][prev]--;
                pol = prev;
            }
        }
        if(sad){
            cout << "YES" << endl;
            for (int i = 0; i < name.length(); i++) {
                for (int j = 0; j < n; j++) {
                    if (flow[j][n + i] == 1) {
                        cout << j + 1 << ' ';
                    }
                }
            }
            cout << endl;
        }

    }
    return 0;
}