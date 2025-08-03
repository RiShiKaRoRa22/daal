#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
using namespace std;

struct data {
    string Transactionid;
    string cname;
    float amt;
    string timestamp;
};

int quickComparisons = 0;
int mergeComparisons = 0;
int quickPasses = 0;
int mergePasses = 0;

void printPartial(const vector<data>& transactions, string label) {
    cout << label << " - Pass " << ((label == "Quick Sort") ? quickPasses : mergePasses) << ":\n";
    for (const auto& txn : transactions) {
        cout << txn.Transactionid << ", " << txn.cname << ", ₹" << txn.amt << ", " << txn.timestamp << endl;
    }
    cout << "-----------------------------------------\n";
}


int divideSort(vector<data>& arr, int start, int end) {
    float pivot = arr[end].amt;
    int i = start - 1;
    for (int j = start; j < end; j++) {
        quickComparisons++;
        if (arr[j].amt < pivot) {
            i++;
            swap(arr[i], arr[j]);
        }
    }
    swap(arr[i + 1], arr[end]);

    if (quickPasses < 5) {
        quickPasses++;
        printPartial(arr, "Quick Sort");
    }

    return i + 1;
}

void quicksort(vector<data>& arr, int start, int end) {
    if (start < end) {
        int pi = divideSort(arr, start, end);
        quicksort(arr, start, pi - 1);
        quicksort(arr, pi + 1, end);
    }
}


void merge(vector<data>& arr, int left, int mid, int right) {
    int n1 = mid - left + 1;
    int n2 = right - mid;

    vector<data> L(n1), R(n2);
    for (int i = 0; i < n1; i++) L[i] = arr[left + i];
    for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

    int i = 0, j = 0, k = left;
    while (i < n1 && j < n2) {
        mergeComparisons++;
        if (L[i].amt <= R[j].amt)
            arr[k++] = L[i++];
        else
            arr[k++] = R[j++];
    }
    while (i < n1) arr[k++] = L[i++];
    while (j < n2) arr[k++] = R[j++];
}

void mergesort(vector<data>& arr, int left, int right) {
    if (left < right) {
        int mid = (left + right) / 2;
        mergesort(arr, left, mid);
        mergesort(arr, mid + 1, right);
        merge(arr, left, mid, right);

        if (mergePasses < 5) {
            mergePasses++;
            printPartial(arr, "Merge Sort");
        }
    }
}


void printTransactions(const vector<data>& transactions) {
    for (const auto& txn : transactions) {
        cout << txn.Transactionid << ", " << txn.cname << ", ₹" << txn.amt << ", " << txn.timestamp << endl;
    }
}

int main() {
    int n;
    cout << "Enter number of transactions to record: ";
    cin >> n;
    cin.ignore(); 

    ofstream writefile("doc.txt");
    if (!writefile) {
        cout << "Couldn't open file for writing.";
        return -1;
    }

    cout << "Enter transaction details in format:\nTransactionID CustomerName Amount Timestamp\n\n";

    for (int i = 0; i < n; ++i) {
        string tid, cname, timestamp;
        float amt;

        cout << "Transaction " << i + 1 << ":\n";
        cout << "Transaction ID: ";
        getline(cin, tid);

        cout << "Customer Name: ";
        getline(cin, cname);

        cout << "Amount: ";
        cin >> amt;
        cin.ignore();

        cout << "Timestamp: ";
        getline(cin, timestamp);

        
        writefile << tid << '\t' << cname << '\t' << amt << '\t' << timestamp << '\t' << endl;
    }

    writefile.close();

    
    ifstream readfile("doc.txt");
    if (!readfile) {
        cout << "Couldn't open file for reading.";
        return -1;
    }

    vector<data> transactions;
    string line;
    while (getline(readfile, line)) {
        stringstream ss(line);
        string tid, tname, amount, tstamp;

        getline(ss, tid, '\t');
        getline(ss, tname, '\t');
        getline(ss, amount, '\t');
        getline(ss, tstamp, '\t');

        data d;
        d.Transactionid = tid;
        d.cname = tname;
        d.amt = stof(amount);
        d.timestamp = tstamp;

        transactions.push_back(d);
    }
    readfile.close();
    int choice;
    cout << "\nChoose sorting algorithm:\n1. Quick Sort\n2. Merge Sort\nEnter your choice: ";
    cin >> choice;

    switch (choice) {
        case 1: {
            quicksort(transactions, 0, transactions.size() - 1);
            cout << "\nSorted using Quick Sort:\n";
            printTransactions(transactions);
            cout << "\nTotal Comparisons: " << quickComparisons << endl;
            break;
        }
        case 2: {
            mergesort(transactions, 0, transactions.size() - 1);
            cout << "\nSorted using Merge Sort:\n";
            printTransactions(transactions);
            cout << "\nTotal Comparisons: " << mergeComparisons << endl;
            break;
        }
        default:
            cout << "Invalid choice.\n";
    }

    return 0;
}
