#include <iostream.h>
#include <math.h>
#include "sampler.h"

// the vapor pressure of lead
void func(double &x, double &fx, double &dfx)
{
   const double a = 18.19f;
   const double b = -23180.0f;
   const double c = -0.8858f;
   const double logp = -4.60517;  // ln(.01)

   fx = a + b / x + c * log(x) - logp;
   dfx = (-b) / (x * x) + c / x;
} // func

void newton(double &x)
{
    const double tol = 1.0E-6;
    double dx, x1;
    double fx;
    double dfx;
    SAMPLE;//23
    do {
        SAMPLE;//25
        x1 = x;
        func( x, fx, dfx );
        SAMPLE;//28
        if ( abs(dfx) < tol )
        {
            SAMPLE;//31
            if ( dfx >= 0 )
            {
                SAMPLE;//34
                dfx = tol;
                SAMPLE;//36
            }
            else
            {
                SAMPLE;//40
                dfx = -tol;
                SAMPLE;//42
            }
        }
        SAMPLE;//45
        dx = fx / dfx;
        x = x1 - dx;
        cout << "x=" << x1 << ",fx=" << fx << ",dfx=" << dfx << "\n";
        SAMPLE;//49
    } while (!(abs(dx) <= abs(tol * x) ));
    SAMPLE;//51
} // newton

int main()
{
    SAMPLE;//56
    double x;
    x = 50;
    SAMPLE;//59
    newton(x);
    cout << "\n";
    cout << "The solution is " << x;
    cout << "\n";
    SAMPLE;//64
    return 0;
}
