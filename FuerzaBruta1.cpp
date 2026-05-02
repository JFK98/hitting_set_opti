#include <iostream>
#include <vector>
#include <chrono>
using namespace std;

bool validarEntrada(vector<int> U, vector<vector<int>> subconjuntos) {
    for (auto S : subconjuntos) {
        for (int x : S) {
            bool existe = false;

            for (int u : U) {
                if (x == u) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                cout << "Error: el elemento " << x << " no pertenece a U" << endl;
                return false;
            }
        }
    }
    return true;
}

bool esHittingSet(vector<int> H, vector<vector<int>> subconjuntos) {
    for (auto S : subconjuntos) {
        bool cumple = false;

        for (int x : H) {
            for (int y : S) {
                if (x == y) {
                    cumple = true;
                    break;
                }
            }
            if (cumple) break;
        }

        if (!cumple) return false;
    }

    return true;
}

vector<int> hittingSetFuerzaBruta(vector<int> U, vector<vector<int>> subconjuntos) {
    int n = U.size();
    vector<int> mejor;
    bool existeMejor = false;

    for (int i = 1; i < (1 << n); i++) {
        vector<int> H;

        for (int j = 0; j < n; j++) {
            if ((i >> j) & 1) {
                H.push_back(U[j]);
            }
        }

        if (esHittingSet(H, subconjuntos)) {
            if (!existeMejor || H.size() < mejor.size()) {
                mejor = H;
                existeMejor = true;
            }
        }
    }

    return mejor;
}

int main() {
    vector<int> U = {1,2,3,4,5,6,7,8,9,10};

    vector<vector<int>> subconjuntos = {
        {1, 2, 3},
        {2, 4, 6},
        {3, 5, 7},
        {8, 9},
        {10, 1}
    };

    if (validarEntrada(U, subconjuntos)) {

        auto inicio = chrono::high_resolution_clock::now();

        vector<int> solucion = hittingSetFuerzaBruta(U, subconjuntos);

        auto fin = chrono::high_resolution_clock::now();

        chrono::duration<double> tiempo = fin - inicio;

        cout << "Solucion fuerza bruta: ";
        for (int x : solucion) cout << x << " ";

        cout << "\nTamano: " << solucion.size() << endl;
        cout << "Tiempo ejecucion: " << tiempo.count() << " segundos" << endl;

    } else {
        cout << "Datos invalidos." << endl;
    }

    return 0;
}