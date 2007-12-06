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
    int flag = 1;
    SAMPLE;//24
    while (flag) {
        SAMPLE;//26
        x1 = x;
        func( x, fx, dfx );
        SAMPLE;//29
        if ( abs(dfx) < tol )
        {
            SAMPLE;//32
            if ( dfx >= 0 )
            {
                SAMPLE;//35
                dfx = tol;
                SAMPLE;//37
            }
            else
            {
                SAMPLE;//41
                dfx = -tol;
                SAMPLE;//43
            }
        }
        SAMPLE;//46
        dx = fx / dfx;
        x = x1 - dx;
        flag = !(abs(dx) <= abs(tol * x) );
        SAMPLE;//50
    }
    SAMPLE;//52
} // newton

int main()
{
    for(int i=0; i<100; ++i) {SAMPLE;//57
    double x;
    x = 100;
    SAMPLE;//60
    newton(x);
    SAMPLE;}//62
    return 0;
}
