clear;
clc;
td=0:15;                % ����� ��� ����������� �������
ta=0:0.1:16;            % ����� ��� ������� ����������� �������

T1=4;                   % ������ ������� ������� - 4 �������
T2=6;                   % ������ ������� ������� - 6 ��������

xld=cos(2*pi*td/T1);    % ���������� ������
xla=cos(2*pi*ta/T1);    % ���������� ������
y1=fft(xld);            % ������ ����������� �������
x2d=cos(2*pi*td/T2);
x2a=cos(2*pi*ta/T2);
y2=fft(x2d);
% ���������� ��������
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