clc;
fs = 125;
T1 = 1;
T2 = 10;
t = 0:1/fs:T1;
t2 = 0:1/fs:T2;

A = zeros(1, length(t2));
% задается
startA = 50;
% задается
A(startA) = 1;
A(startA+1) = 1;
A(startA+2) = 1;
A(startA+3) = 1;
A(startA+4) = 1;

B = zeros(1, length(t));
% задается
startB = 100;
% задается
B(startB) = 1;
B(startB+1) = 2;
B(startB+2) = 3;

D = zeros(1, length(t));
% задается
startD = 80;
% задается
D(startD) = 1;
D(startD+1) = 2;
D(startD+2) = 3;
D(startD+3) = 4;
D(startD+4) = 5;

E = zeros(1, length(t));
% задается
startE = 55;
% задается
E(startE) = 5;
E(startE+1) = 4;
E(startE+2) = 5;
E(startE+3) = 3;
E(startE+4) = 1;

F = sin(2*pi*t2)+0.1*randn(1,length(t2));

figure(1); clf;
subplot(5,1,1); plot(t2,A);
title('A');
subplot(5,1,2); plot(t,B);
title('B');
subplot(5,1,3); plot(t,D);
title('D');
subplot(5,1,4); plot(t,E);
title('E');
subplot(5,1,5); plot(t2,F);
title('F');

tmp = myConv(D, E);
conv1 = myConv(tmp, B);

tmp = myConv(E, B);
conv2 = myConv(D, tmp);

conv3 = myConv(F, A);

figure(2); clf;
subplot(3,1,1); plot(conv1);
title('(D*E)*B');
subplot(3,1,2); plot(conv2);
title('D*(E*B)');
subplot(3,1,3); plot(conv3);
title('F*A');
