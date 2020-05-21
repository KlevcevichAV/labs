#include <iostream>
#include <vector>
#include <math.h>

using namespace std;

vector<double> initialize(int n) {
    vector<double> result(n, 0);
    return result;
}

vector<vector<double>> initialize(int n, int m) {
    vector<vector<double>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(initialize(m));
    }
    return result;
}

vector<vector<vector<double>>> initialize(int n, int m, int q) {
    vector<vector<vector<double>>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(initialize(m, q));
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

vector<vector<double>> inputMatrix(int n, int m) {
    vector<vector<double>> result;
    for (int i = 0; i < n; i++) {
        result.push_back(inputMatrix(m));
    }
    return result;
}

void outputMatrix(vector<double> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
        cout << matrix[i] << " ";
    }
    cout << "\n";
}

void outputMatrix(vector<vector<double>> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
        outputMatrix(matrix[i]);
    }
    cout << "\n";
}

void outputMatrix(vector<vector<vector<double>>> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
        outputMatrix(matrix[i]);
    }
    cout << "\n";
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

double firstExtendedFunctionByTaskVariant(vector<double> matrix) {
    double result = 1;
    for(int i = 0; i < matrix.size(); i++){
        result *= matrix[i];
    }
    return result;
}

double secondExtendedFunctionByTaskVariant(vector<double> matrix) {
    double result = 1;
    for(int i = 0; i < matrix.size(); i++){
        result *= (1 - matrix[i]);
    }
    result += -1;
    return result;
}

double thirdExtendedFunctionByTaskVariant(vector<double> matrix1, vector<double> matrix2) {
    double result = firstExtendedFunctionByTaskVariant(matrix1) * secondExtendedFunctionByTaskVariant(matrix2);
    return result;
}

double fourthExtendedFunctionByTaskVariant(double value1, double value2) {
    double result = value1 * (value2 - 1) + 1;
    return result;
}

double fifthExtendedFunctionByTaskVariant(double value1, double value2) {
    return fourthExtendedFunctionByTaskVariant(value2, value1);
}

double sixthExtendedFunctionByTaskVariant(double value1, double value2) {
    return value1 * value2;
}

vector<vector<vector<double>>> createMatrixD(vector<vector<double>> matrix1, vector<vector<double>> matrix2){
    vector<vector<vector<double>>> result = initialize(matrix1.size(), matrix2.size(), matrix1[0].size());
    for(int i = 0; i < matrix1.size(); i++){
        for(int j = 0; j < matrix2.size(); j++){
            for(int k = 0; k < matrix1[0].size(); k++){
                result[i][j][k] = sixthExtendedFunctionByTaskVariant(matrix1[i][k], matrix2[k][j]);
            }
        }
    }
    return result;
}

vector<vector<vector<double>>> createMatrixF(vector<vector<double>> matrix1, vector<vector<double>> matrix2, vector<double> matrix3){
    vector<vector<vector<double>>> result = initialize(matrix1.size(), matrix2.size(), matrix1[0].size());
    for(int i = 0; i < matrix1.size(); i++){
        for(int j = 0; j < matrix2.size(); j++){
            for(int k = 0; k < matrix1[0].size(); k++){
                result[i][j][k] = fourthExtendedFunctionByTaskVariant(matrix1[i][j],matrix2[k][j]) * (2 * matrix3[k] - 1) *
                        matrix3[k] + fifthExtendedFunctionByTaskVariant(matrix1[i][j],matrix2[k][j]) *
                        (1 + (4 * fourthExtendedFunctionByTaskVariant(matrix1[i][j],matrix2[k][j]) - 2) * matrix3[k]) * (1 - matrix3[k]);
            }
        }
    }
    return result;
}

vector<vector<double>> createMatrixC(vector<vector<vector<double>>> f , vector<vector<vector<double>>> d, vector<vector<double>> g){
    vector<vector<double>> c = initialize(d.size(), d[0].size());
    for(int i = 0; i < d.size(); i++){
        for(int j = 0; j < d[i].size(); j++){
            c[i][j] = firstExtendedFunctionByTaskVariant(f[i][j]) * (3 * g[i][j] - 2) * g[i][j] +
                    (secondExtendedFunctionByTaskVariant(d[i][j]) +
                    (4 * thirdExtendedFunctionByTaskVariant(f[i][j], d[i][j]) -
                    3 * secondExtendedFunctionByTaskVariant(d[i][j]) * g[i][j])) * (1 - g[i][j]);
        }
    }
    return c;
}

//vector<vector<double>> createMatrixC(vector<vector<double>> g, vector<vector<vector<double>>> d, vector<vector<vector<double>>>f){
//    vector<vector<double>> result = initialize(d.size(), d[0].size());
//    for(int i = 0; i < d.size(); i++){
//        for(int j = 0; j < d[0].size(); j++){
//            result[i][j] = ;
//        }
//    }
//    return result;
//}

int main() {
    int P, M, Q;
    inputSizeMatrix(P, M, Q);
    vector<vector<double>> matrixA = inputMatrix(P, M);
    vector<vector<double>> matrixB = inputMatrix(M, Q);
    vector<double> matrixE = inputMatrix(M);
    vector<vector<double>> matrixG = inputMatrix(P, Q);
    vector<vector<vector<double>>> matrixD = createMatrixD(matrixA, matrixB);
    vector<vector<vector<double>>> matrixF = createMatrixF(matrixA, matrixB, matrixE);
    vector<vector<double>> matrixC = createMatrixC(matrixF, matrixD, matrixG);
//    outputMatrix(matrixE);
//    cout << "\n";
//    outputMatrix(matrixA);
    cout << "\n";
    outputMatrix(matrixC);
//    outputMatrix(matrixD);
    return 0;
}
