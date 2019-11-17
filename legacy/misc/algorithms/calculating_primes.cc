/************************************************
*       Calculating Primes:						*
*			Using a method known as the			*
*       	"sieve of Eratosthenes", this    	*
*			program will calculate primes		*
*			from 0 - 1000						*
************************************************/

#include <iostream>

const int g_Range = 1000;

int main() {
    int nums[g_Range + 1];

    // Initialize all to 1 (true)
    for(int i=2; i <= g_Range; i++) {
        nums[i] = 1;
    }

    // All products will be set to 0 (false) as they are not prime
    for(int i=2; i <= g_Range/2; i++) {
        // ? Why divide by i 
        // Ensures all factors are produced in given range
        // Also overlaps on factors - could be optimized but unneccessary
        for(int j=2; j <= g_Range/i; j++) {
            nums[i * j] = 0;
        }
    }

    // Display numbers that are prime
    for(int i=2; i <= g_Range; i++) {
        // 1 is true, 0 is false
        if(nums[i]) {
            std::cout << i << "\n";
        }
    }
}