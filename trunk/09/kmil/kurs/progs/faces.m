figure(1);

for i = 1: 10
    image = imread( ['D:\LETI\9\kmil\bases\orl_bmp_test\s1\', num2str(i),'.bmp'] );
    subplot( 2, 5, i );
    imshow( image );
end;
