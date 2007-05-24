% пытаемся повторить опыт из л/р 6
fs = 1024;
f = 100;
% N = 10240;
T = 10;
t = 0:1/fs:T;
N = length(t);
s = sin(2*pi*f * t);
subplot(2,1,1); plot(t, s);
Nfft = 1024;
spf = abs( fft(s, Nfft) );
% for i = 1:length(sp)
%     re = real( spf(i) );
%     im = imag( spf(i) );
%     sp(i) = sqrt( re*re + im*im );
% end
% w = rectwin(N);
% sp = periodogram(s, w, N);
sp = spf;
sp = sp(1:length(sp)/2);

subplot(2,1,2); plot(sp);
disp(max(sp));