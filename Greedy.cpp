#include <iostream>
#include <vector>
#include <chrono>
using namespace std;

bool contiene(vector<int> conjunto, int elemento) {
    for (int x : conjunto) {
        if (x == elemento) return true;
    }
    return false;
}

bool validarEntrada(vector<int> U, vector<vector<int>> subconjuntos) {
    for (auto S : subconjuntos) {
        for (int x : S) {
            if (!contiene(U, x)) {
                cout << "Error: el elemento " << x << " no pertenece a U" << endl;
                return false;
            }
        }
    }
    return true;
}

vector<int> hittingSetGreedy(vector<int> U, vector<vector<int>> subconjuntos) {
    vector<vector<int>> restantes = subconjuntos;
    vector<int> H;

    while (!restantes.empty()) {
        int mejorElemento = -1;
        int maxCobertura = 0;

        for (int elemento : U) {
            int cobertura = 0;

            for (auto S : restantes) {
                if (contiene(S, elemento)) {
                    cobertura++;
                }
            }

            if (cobertura > maxCobertura) {
                maxCobertura = cobertura;
                mejorElemento = elemento;
            }
        }

        if (mejorElemento == -1) return {};

        H.push_back(mejorElemento);

        vector<vector<int>> nuevos;

        for (auto S : restantes) {
            if (!contiene(S, mejorElemento)) {
                nuevos.push_back(S);
            }
        }

        restantes = nuevos;
    }

    return H;
}

void ejecutarCaso(vector<int> U, vector<vector<int>> S) {
    cout << "\n===== NUEVO CASO =====" << endl;

    if (validarEntrada(U, S)) {

        auto inicio = chrono::high_resolution_clock::now();

        vector<int> solucion = hittingSetGreedy(U, S);

        auto fin = chrono::high_resolution_clock::now();
        chrono::duration<double> tiempo = fin - inicio;

        cout << "Solucion greedy: ";
        for (int x : solucion) cout << x << " ";

        cout << "\nTamano: " << solucion.size() << endl;
        cout << "Tiempo ejecucion: " << tiempo.count() << " segundos" << endl;

    } else {
        cout << "Datos invalidos." << endl;
    }
}

int main() {

    vector<int> U1 = {1,2,3,4,5};
    vector<vector<int>> S1 = {
        {1,2},
        {2,3},
        {3,4},
        {4,5}
    };

    vector<int> U2 = {1,2,3,4,5,6,7,8};
    vector<vector<int>> S2 = {
        {1,2,3},
        {2,4,5},
        {3,6},
        {5,7},
        {6,8},
        {1,7},
        {2,8}
    };

    vector<int> U3 = {1,2,3,4,5,6,7,8,9,10,11,12};
    vector<vector<int>> S3 = {
        {1,2,3,4},
        {2,5,6},
        {3,7,8},
        {4,9},
        {5,10},
        {6,11},
        {7,9},
        {8,10},
        {1,11},
        {2,7},
        {3,5}
    };

    ejecutarCaso(U1, S1);
    ejecutarCaso(U2, S2);
    ejecutarCaso(U3, S3);

    return 0;
}