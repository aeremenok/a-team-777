clc;
fs = 1024;                 % частота дискретизации
n1 = 100;				   % длина реализации сигнала 1
n2 = 1000;                 % длина реализации сигнала 2
f1 = 50;                   % частота сигнала 1 
f2 = 100;                  % частота сигнала 2
t1 = (0:n1-1)/fs;          % шаг дискретизации сигнала 1
t2 = (0:n2-1)/fs;          % шаг дискретизации сигнала 2

% 2) Исследование периодических сигналов
%	1. Выполнить генерацию сигналов в соответствии с заданием при различных частотах и 
%	длине реализации
y11 = sin (2*pi*f1*t1);
y12 = sin (2*pi*f1*t2);
y21 = sin (2*pi*f2*t1);
y22 = sin (2*pi*f2*t2);

% вывод на экран результатов
figure(1); clf;
subplot(4,1,1); plot(t1,y11);
xlabel('Time');
ylabel('Amp');
title('y11');
subplot(4,1,2); plot(t2,y12);
xlabel('Time');
ylabel('Amp');
title('y12');
subplot(4,1,3); plot(t1,y21);
xlabel('Time');
ylabel('Amp');
title('y21');
subplot(4,1,4); plot(t2,y22);
xlabel('Time');
ylabel('Amp');
title('y22');

% 	2. Разработать программу для получения спектра мощности сигнала.
% 	3. Получить спектр мощности сигнала при различных Nfft
N1 = 64;                    % длина ПФ 1
N2 = 1024;                  % длина ПФ 2

F111 = abs(fft(y11, N1));
F112 = abs(fft(y11, N2));
F121 = abs(fft(y12, N1));
F122 = abs(fft(y12, N2));
F211 = abs(fft(y21, N1));
F212 = abs(fft(y21, N2));
F221 = abs(fft(y22, N1));
F222 = abs(fft(y22, N2));

% параметризуем
Fouriers = [F111, F112, F121, F122, F211, F212, F221, F222];
Lengths = [length(F111), length(F112), length(F121), length(F122), length(F211), length(F212), length(F221), length(F222)]

% вывод на экран результатов
figure(2); clf;
ind = 0;
for i = 1:8
    disp(ind);
    ff = Fouriers(ind+1:ind+Lengths(i));
    ind = ind + Lengths(i);
    ff = ff(1:length(ff)/2);
    subplot(4,2,i); plot(ff);
    xlabel('Freq');
    ylabel('Power');
end

% 4)	Исследование спектра сигналов, ограниченных во времени.
% 	1. Выполнить генерацию сигналов в соответствии с заданием при различных
%   частотах. Частота дискретизации 1024 Гц.
% - используем y11 и y21, сгенерированные ранее
signals = [y11, y21];
n = n1;

%   2. Выполнить генерацию окон
Nw1 = 10;
Nw2 = 20;

wRect1 = rectwin(Nw1);
wTriang1 = triang(Nw1);
wHann1 = hann(Nw1);
wHamming1 = hamming(Nw1);
wBH1 = blackmanharris(Nw1);

wRect2 = rectwin(Nw2);
wTriang2 = triang(Nw2);
wHann2 = hann(Nw2);
wHamming2 = hamming(Nw2);
wBH2 = blackmanharris(Nw2);

% параметризуем
winds1 = [wRect1, wTriang1, wHann1, wHamming1, wBH1];
winds2 = [wRect2, wTriang2, wHann2, wHamming2, wBH2];
rectN = 'Rectangular';
trianN = 'Triangular';
hannN = 'Hann';
hammN = 'Hamming';
bhN = 'Blackman-Harris';
names = [rectN, trianN, hannN, hammN, bhN];
nameLens = [length(rectN), length(trianN), length(hannN), length(hammN), length(bhN)]

% 	3. Найти спектр мощности сигнала с различными окнами имеющими различную
% 	длину. Сравнить полученный результат с теоретическим. 

for j = 1:2
    figure(2+j); clf;
    ind = 0;
    for i = 1:5
        y = signals( (j-1)*n+1 : j*n);
        w = winds1(i:i*Nw1);
        ps = abs( fft(conv(y,w), N2) );
        ps = ps(1:length(ps)/2);
        
        subplot(5,1,i); plot(ps);
        title(names( ind+1 : ind+nameLens(i) ));
        ind = ind+nameLens(i);
    end
    figure(4+j); clf;
    ind = 0;
    for i = 1:5
        y = signals( (j-1)*n+1 : j*n);
        w = winds2(i:i*Nw2);
        ps = abs( fft(conv(y,w), N2) );
        ps = ps(1:length(ps)/2);
        
        subplot(5,1,i); plot(ps);
        title(names( ind+1 : ind+nameLens(i) ));
        ind = ind+nameLens(i);
    end
end

% 5) Исследовать эффект подмены частот. Частота дискретизации 512 Гц. 
fs1 = 512;
n3 = 2048;
t11 = (0:n3-1)/fs1;
f0 = 50;
f11 = fs1 / 2 - f0;
f12 = fs1 / 2 + f0;
f13 = fs1 + f0;
f14 = 2*fs1 + f0;
freqs = [f11, f12, f13, f14];
figure(7); clf;
for i = 1:4
    s = sin(2*pi* freqs(i) * t11);
    specter = abs( fft(s, fs1) );
    oy = specter(1:length(specter)/2);
    subplot(4,1,i); plot( oy);
    freq = sprintf('freq = %d', freqs(i));
    title( freq );
end
