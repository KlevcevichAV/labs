#include <iostream>
#include <vector>

using namespace std;

vector<double> initialize(int n) {
    vector<double> result;
    for (int i = 0; i < n; i++) {
        result.push_back(0);
    }
    return result;
}

vector<double> inputMatrix(int n) {
    vector<double> result;
    for (int i = 0; i < n; i++) {
        double value;
        cin >> value;
        result.push_back(value);
    }
    return result;
}

void outputMatrix(vector<double> matrix){
    for(int i = 0; i < matrix.size(); i++){
        cout << matrix[i] << " ";
    }
    cout << "\n";
}

void outputMatrix(vector<vector<double>> matrix){
    for(int i = 0; i < matrix.size(); i++){
        outputMatrix(matrix[i]);
    }
    cout << "\n";
}

void outputMatrix(vector<vector<vector<double>>> matrix){
    for(int i = 0; i < matrix.size(); i++){
        outputMatrix(matrix[i]);
    }
}

vector<vector<double>> inputMatrix(int n, int m) {
    vector<vector<double>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(inputMatrix(m));
    }
    return result;
}

vector<vector<vector<double>>> inputMatrix(int n, int m, int q) {
    vector<vector<vector<double>>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(inputMatrix(m,q));
    }
    return result;
}

vector<vector<double>> initialize(int n, int m) {
    vector<vector<double>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(initialize(n));
    }
    return result;
}

vector<vector<vector<double>>> initialize(int n, int m, int k) {
    vector<vector<vector<double>>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(initialize(n,m));
    }
    return result;
}

void inputSizeMatrix(int &P, int &M, int &Q) {
    cout << "Input P : ";
    cin >> P;
    cout << "Input M : ";
    cin >> M;
    cout << "Input Q : ";
    cin >> Q;

}

void outputSizeMatrix(int P, int M, int Q) {
    cout << P << " " << M << " " << Q << endl;
}

int main() {
    int P, M, Q;
    inputSizeMatrix(P, M, Q);
    vector<vector<double>> matrixA = initialize(P, M);
    vector<vector<double>> matrixB = initialize(M, Q);
    vector<double> matrixE = initialize(M);
    vector<vector<double>> matrixG = initialize(P, Q);
    vector<vector<vector<double>>> matrixD = initialize(P,M,Q);
    vector<vector<vector<double>>> matrixF = initialize(P,M,Q);
    vector<vector<double>> matrixC = initialize(P, Q);
    std::cout << "Hello, World!" << std::endl;
    outputMatrix(matrixE);
    cout << "\n";
    outputMatrix(matrixA);
    cout << "\n";
    outputMatrix(matrixD);
    return 0;
}
