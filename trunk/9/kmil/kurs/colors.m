clear all; clc;

PATH = '/home/zps/test.png'

image = imread( PATH );

red = image( :, :, 1 );
green = image( :, :, 2 );
blue = image( :, :, 3 );

red_s = uint32( zeros( 1, 256 ) );
for i = 1 : size( red, 1 )
    for j = 1: size( red, 2 )
        color = red( i, j );
        red_s( color + 1 ) = red_s( color + 1 ) + 1;
    end;
end;

green_s = uint32( zeros( 1, 256 ) );
for i = 1 : size( green, 1 )
    for j = 1: size( green, 2 )
        color = green( i, j );
        green_s( color + 1 ) = green_s( color + 1 ) + 1;
    end;
end;

blue_s = uint32( zeros( 1, 256 ) );
for i = 1 : size( blue, 1 )
    for j = 1: size( blue, 2 )
        color = blue( i, j );
        blue_s( color + 1 ) = blue_s( color + 1 ) + 1;
    end;
end;

figure(1);
subplot( 1, 1, 1 );
imshow( image );

figure(2);
subplot( 1, 1, 1 );
RRR = [red_s; green_s; blue_s]';
bar3(RRR, 'detached');
colormap([1 0 0;0 1 0;0 0 1]);

PATH = '/home/zps/test2.png'

image = imread( PATH );

red = image( :, :, 1 );
green = image( :, :, 2 );
blue = image( :, :, 3 );

red_s = uint32( zeros( 1, 256 ) );
for i = 1 : size( red, 1 )
    for j = 1: size( red, 2 )
        color = red( i, j );
        red_s( color + 1 ) = red_s( color + 1 ) + 1;
    end;
end;

green_s = uint32( zeros( 1, 256 ) );
for i = 1 : size( green, 1 )
    for j = 1: size( green, 2 )
        color = green( i, j );
        green_s( color + 1 ) = green_s( color + 1 ) + 1;
    end;
end;

blue_s = uint32( zeros( 1, 256 ) );
for i = 1 : size( blue, 1 )
    for j = 1: size( blue, 2 )
        color = blue( i, j );
        blue_s( color + 1 ) = blue_s( color + 1 ) + 1;
    end;
end;

figure(3);
subplot( 1, 1, 1 );
imshow( image );

figure(4);
subplot( 1, 1, 1 );
RRR = [red_s; green_s; blue_s]';
bar3(RRR, 'detached');
colormap([1 0 0;0 1 0;0 0 1]);

