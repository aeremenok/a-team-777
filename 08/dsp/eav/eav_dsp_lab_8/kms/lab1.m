 f1 = 10; % частота сигнала 1
 f2 = 20; % частота сигнала 2
 f3 = 30; % частота сигнала 3
 T = 128; % длина сигнала 
 t1=0:1/f1:T; 
 t2=0:1/f2:T;
 t3=0:1/f3:T;
 
 y1=sin(2*pi*f1*t)+randn(1,length(t)); %первый сигнал
 y2=sin(2*pi*f2*t)+randn(1,length(t)); %второй сигнал
 y3=sin(2*pi*f3*t)+randn(1,length(t)); %третий сигнал
 
 figure(1)
 plot(t,y1);
 grid on
 xlabel('Time');
 ylabel('Amplitude');
 
 figure(2)
 plot(t,y2);
 grid on
 xlabel('Time');
 ylabel('Amplitude');
 
 figure(3)
 plot(t,y3);
 grid on
 xlabel('Time');
 ylabel('Amplitude');
