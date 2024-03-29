td = 0:15;%����� ��� ���������� ��������
T1 = 4; %������ ������� �������
T2 = 6;  %������ ������� �������
x1d = cos(2*pi*td/T1); %���������� ������ 
x2d = cos(2*pi*td/T2);
y1 = fft(x1d); %���
y2 = fft(x2d);
td_m = [[td-16, td, td+16]]
x1d_m = [x1d, x1d, x1d];%����������� ������� x1d
x2d_m = [x2d, x2d, x2d];%����������� ������� x2d

[h1,w1] = freqz(x1d,1,[],16,'whole');
[h2,w2] = freqz(x2d,1,[],16,'whole'); 

figure(1);
stem(td_m,x2d_m)%������ ������������� �������� x2d
hold on
plot(td-16, x2d, 'b--', td, x2d, 'b--', td+16, x2d, 'b--')
hold off

figure(2);
stem(td_m,x1d_m) %������ ������������� ������� x1d
hold on
plot(td-16, x1d, 'b--', td, x1d, 'b--', td+16, x1d, 'b--')
hold off;

figure(3)
stem(td, abs(y1)); %����� ������ ��� ������� x1d
hold on
plot(w1, abs(h1),'--'); %������ ���������� ������� x1d
hold off;

figure(4)
stem(td, abs(y2)); %������ ������ ��� ������� x2d
hold on
plot(w2, abs(h2),'--') ;%������ ���������� ������� x2d
hold on