/************************************************
*       Euclid's Algorithm:						*
*		    Calculates the GCD between          *
*		    two numbers. The gcd of u and       *
*           v will be the gcd of u and          *
*           u - v if u > v                      *
************************************************/

#include <iostream>

int gcd(int u, int v) {
    int temp;

    // Convert negatives to positive
    if(u < 0)
        u *= -1;
    if(v < 0)
        v *= -1;

    while(u > 0) {
        // Swap u and v if u is less than v
        if(u < v) {
            temp = u;
            u = v;
            v = temp;
        }
        // Modulo shortcuts the u - v calculations
        u = u % v;
    }

    return v;
}

int main() {
    int x, y;

    // Get user input
    std::cout << "Enter the first number: ";
    std::cin >> x;
    std::cout << "Enter the second number: ";
    std::cin >> y;

    // Find the greatest common divisor and display it
    std::cout << "GCD: " << gcd(x, y) << "\n";
}