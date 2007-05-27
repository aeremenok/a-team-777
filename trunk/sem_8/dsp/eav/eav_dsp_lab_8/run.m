clc;
fs = 1024;
T = 0.5;
t = 0:1/fs:T;
k = T * fs + 1;
% 1. ¬ыполнить генерацию сигналов
% —инусоида с мен€ющейс€ частотой от A до B по законам — и D
% 1
A1 = 5;
B1 = 100;
% задаем
a1 = -1000;
% задаем
b1 = (B1 - A1 - a1*T^2)/T;
f1 = zeros(k);
y1 = zeros(k);
for m = 1:k
    f1(m) = a1 * t(m) * t(m) + b1 * t(m) + A1;
    y1(m) = sin(2*pi*f1(m) * t(m));
end

figure(1); clf;
subplot(2,1,1); plot(t, f1);
title('freq');
subplot(2,1,2); plot(t, y1);
title('signal');

% 2
A2 = 10;
B2 = 120;
% задаем
a2 = 100;
b2 = 100;
% задаем
c2 = - (a2 * ( exp(b2*T) - 1 ) + A2 - B2 ) / T;
d2 = A2 - a2;
f2 = zeros(k);
y2 = zeros(k);
for m = 1:k
    f2(m) = a2 * exp(b2*t(m)) + c2*t(m) + d2;
    y2(m) = sin(2*pi*f2(m) * t(m));
end

figure(2); clf;
subplot(2,1,1); plot(t, f2);
title('freq');
subplot(2,1,2); plot(t, y2);
title('signal');

% 3
A3 = 1;
B3 = 10;
% задаем
a3 = 100;
b3 = 10;
c3 = 100;
% задаем
d3 = - (a3 * ( exp(b3*T) - 1 ) - c3*T*T + A3 - B3 ) / T;
e3 = A3 - a3;
f3 = zeros(k);
y3 = zeros(k);
for m = 1:k
    f3(m) = a3 * exp(b3*t(m)) + c3*t(m)*t(m) + d3*t(m) + e3;
    y3(m) = sin(2*pi*f3(m) * t(m));
end

figure(3); clf;
subplot(2,1,1); plot(t, f3);
title('freq');
subplot(2,1,2); plot(t, y3);
title('signal');