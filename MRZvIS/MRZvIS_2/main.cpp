#include <iostream>
#include <ctime>
#include <vector>

using namespace std;
int T1, Tn;

double generation() {
    double result = 0;
    for (int i = 0; i < 4; i++) {
        int temp = rand() % 10;
        result = (result + temp) / 10.0;
    }
    return result;
}


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
        double value = generation();
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
        cout << matrix[i] << "\t";
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
        for (int j = 0; j < matrix[i].size(); j++) {
            for (int k = 0; k < matrix[i][j].size(); k++) {
                cout << matrix[i][j][k] << endl;
            }
        }
    }
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
    for (int i = 0; i < matrix.size(); i++) {
        result *= matrix[i];
    }
    return result;
}

double secondExtendedFunctionByTaskVariant(vector<double> matrix) {
    double result = 1;
    for (int i = 0; i < matrix.size(); i++) {
        result *= (1 - matrix[i]);
    }
    result = 1 - result;
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

vector<double> createElementsD(vector<vector<double>> a, vector<vector<double>> b, int i, int j) {
    vector<double> d;
    for (int k = 0; k < b.size(); k++) {
        d.push_back(sixthExtendedFunctionByTaskVariant(a[i][k], b[k][j]));
        T1++;
    }
    return d;
}

vector<double> createElementsF(vector<vector<double>> a, vector<vector<double>> b, vector<double> e, int i, int j) {
    vector<double> d;
    for (int k = 0; k < b.size(); k++) {
        double tempResult = fourthExtendedFunctionByTaskVariant(a[i][k], b[k][j]) * (2 * e[k] - 1) *
                            e[k] + fifthExtendedFunctionByTaskVariant(a[i][k], b[k][j]) *
                                   (1 + (4 * fourthExtendedFunctionByTaskVariant(a[i][k], b[k][j]) - 2) * e[k]) *
                                   (1 - e[k]);
        T1 += 21;
        Tn++;
        d.push_back(tempResult);

    }
    return d;
}

vector<vector<vector<double>>> createMatrixD(vector<vector<double>> matrix1, vector<vector<double>> matrix2) {
    vector<vector<vector<double>>> result = initialize(matrix1.size(), matrix2.size(), matrix1[0].size());
    for (int i = 0; i < matrix1.size(); i++) {
        for (int j = 0; j < matrix2.size(); j++) {
            for (int k = 0; k < matrix1[0].size(); k++) {
                result[i][j] = createElementsD(matrix1, matrix2, i, j);
            }
        }
    }
    return result;
}

vector<vector<vector<double>>>
createMatrixF(vector<vector<double>> matrix1, vector<vector<double>> matrix2, vector<double> matrix3) {
    vector<vector<vector<double>>> result = initialize(matrix1.size(), matrix2.size(), matrix1[0].size());
    for (int i = 0; i < matrix1.size(); i++) {
        for (int j = 0; j < matrix2.size(); j++) {
            for (int k = 0; k < matrix1[0].size(); k++) {
                result[i][j] = createElementsF(matrix1, matrix2, matrix3, i, j);
            }
        }
    }
    return result;
}

// CHECK SIZE!!!!!!!!!!!!
vector<vector<double>>
createMatrixC(vector<vector<double>> a, vector<vector<double>> b, vector<double> e, vector<vector<double>> g) {
    vector<vector<double>> c = initialize(a.size(), b[0].size());
    for (int i = 0; i < a.size(); i++) {
        for (int j = 0; j < b[i].size(); j++) {
            vector<double> dk = createElementsD(a, b, i, j);
            vector<double> fk = createElementsF(a, b, e, i, j);
            c[i][j] = firstExtendedFunctionByTaskVariant(fk) * (3 * g[i][j] - 2) * g[i][j] +
                      (secondExtendedFunctionByTaskVariant(dk) +
                       (4 * thirdExtendedFunctionByTaskVariant(fk,
                                                               dk) -
                        3 * secondExtendedFunctionByTaskVariant(dk) * g[i][j])) *
                      (1 - g[i][j]);
            if(b.size() > 1)
                T1 +=  27 + 8 * (b.size() - 2);
            else T1 += 18;
            Tn++;
        }
    }
    return c;
}

void outputMatrix(vector<vector<double>> a, vector<vector<double>> b, vector<double> e, vector<vector<double>> g,
                  vector<vector<double>> c) {
    cout << "Matrix A :\n";
    outputMatrix(a);
    cout << "Matrix B :\n";
    outputMatrix(b);
    cout << "Matrix E :\n";
    outputMatrix(e);
    cout << endl;
    cout << "Matrix G :\n";
    outputMatrix(g);
    cout << "Matrix C :\n";
    outputMatrix(c);
}


int main() {
    srand(time(NULL));
    int P, M, Q;
    inputSizeMatrix(P, M, Q);
    vector<vector<double>>
    matrixA = inputMatrix(P, M);
    vector<vector<double>> matrixB = inputMatrix(M, Q);
    vector<double> matrixE = inputMatrix(M);
    vector<vector<double>> matrixG = inputMatrix(P, Q);
    vector<vector<double>> matrixC = createMatrixC(matrixA, matrixB, matrixE, matrixG);
    outputMatrix(matrixA, matrixB, matrixE, matrixG, matrixC);
    cout << "T1 = " << T1 << endl;
    cout << "Tn = " << Tn;
    return 0;
}