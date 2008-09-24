clc;
fs = 512;
T = 1;
t = 0:1/fs:T;
k = T * fs + 1;
f = 10;

y11 = sin(f*t);
y21 = sin(10*f*t);

y12 = ones(1,5);
y22 = 1:1:5;

y31 = myConv(y11,y21);
y41 = conv(y11,y21);

y32 = myConv(y12,y22);
y42 = conv(y12,y22);

figure(1); clf;
subplot(4,1,1); plot(y11);
title('y1');
subplot(4,1,2); plot(y21);
title('y2');
subplot(4,1,3); plot(y31);
title('myConv');
subplot(4,1,4); plot(y41);
title('conv');

figure(2); clf;
subplot(4,1,1); plot(y12);
title('y1');
subplot(4,1,2); plot(y22);
title('y2');
subplot(4,1,3); plot(y32);
title('myConv');
subplot(4,1,4); plot(y42);
title('conv');