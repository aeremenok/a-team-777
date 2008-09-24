A = [0,1,1,1,1,1,0];
B = [0,1,2,3,0,0,0];
D = [0,1,2,3,4,5,0];
E = [0,5,4,5,3,1,0];

z1 = CONV(A, A);
z2 = CONV(CONV(D,E),B);

figure(1)
plot(z1)

figure(2)
plot(z2)
