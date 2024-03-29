clear;
fs = 1024;
n1 = 1;		         % ����� ���������� ������� �������
n2 = 5;                 % ����� ���������� ������� ������� 
f1=  50;                     % ������� ��� ������� �������   
f2 = 150;                  % ������� ��� ������� ������� 
t1 = 0:1/fs:n1;          % ��� ������������� ��� ������� �������
t2 = 0:1/fs:n2;          % ��� ������������� ��� ������� �������
N = 1024;


% ������ ������� ������������������ - ������������� ������
y1=cos(2*pi*f1*t1); 
% ������ ������� ������������������ - ������������� ������
y2=cos(2*pi*f2*t2);

% ����� �� ����� ������� �������������� �������
xlim([0 0.1])
figure(1);
xlim([0 16]);
plot(t1,y1);
grid on;
xlabel('Time');
ylabel('Amplitude');
title('Harmonic signal');

% ����� �� ����� ������� �������������� �������
figure(2);
clf,plot(t2,y2);
grid on;
xlabel('Time');
ylabel('Amplitude');
title('Harmonic signal');

% ���������� ������� ������� �������
Fy1  = fft(y1,N);
figure(3);
clf, plot(abs(Fy1));
grid on;
ylabel('Power');
xlabel('Frequence');
title('Spectrum');

% ���������� ������� ������� �������
Fy2 = fft(y2,N);
figure(4);
clf, plot(abs(Fy2));
grid on;
ylabel('Power');
xlabel('Frequence');
title('Spectrum');

%������� ���� ��� �������
w_rect = window(@rectwin,n1);
w_tr=window(@triang,n1);
w_bl=window(@blackman,n1);
w_ch=window(@chebwin,n1,40);


% ������ �������� ������� � �������������� �������������� ����
Fy1  = fft(w_rect.*y1');
figure(5);
clf, plot(abs(Fy1));
grid on;
ylabel('Power');
xlabel('Frequence');
title('Spectrum');

% ������ �������� ������� � �������������� ������������ ����
Fy2  = fft(w_tr.*y1');
figure(6);
clf, plot(abs(Fy2));
grid on;
ylabel('Power');
xlabel('Frequence');
title('Spectrum');

% ������ �������� ������� � �������������� ���� ��������
Fy3  = fft(w_bl.*y1');
figure(7);
clf, plot(abs(Fy3));
grid on;
ylabel('Power');
xlabel('Frequence');
title('Spectrum');

% ������ �������� ������� � �������������� ��� ��������
Fy4  = fft(w_ch.*y1');
figure(8);
clf, plot(abs(Fy4));
grid on;
ylabel('Power');
xlabel('Frequence');
title('Spectrum');