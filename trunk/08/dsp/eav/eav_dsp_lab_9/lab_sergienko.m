clear;
clc;
td=0:15;                % время для дискретного сигнала
ta=0:0.1:16;            % время для условно аналогового сигнала

T1=4;                   % период первого сигнала - 4 отсчета
T2=6;                   % период первого сигнала - 6 отсчетов

xld=cos(2*pi*td/T1);    % дискретный сигнал
xla=cos(2*pi*ta/T1);    % аналоговый сигнал
y1=fft(xld);            % спектр дискретного сигнала
x2d=cos(2*pi*td/T2);
x2a=cos(2*pi*ta/T2);
y2=fft(x2d);
% построение графиков
subplot(221);
stem(td,xld);
hold on;
plot(ta,xla,'--');
hold off;
xlim([0 16]);

subplot(222);
stem(td,abs(y1));

subplot(223);
stem(td,x2d);
hold on;
plot(ta,x2a,'--');
hold off;
xlim([0 16]);

subplot(224);
stem(td,abs(y2));