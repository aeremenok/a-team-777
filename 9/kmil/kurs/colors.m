clear all; clc;

PATH = '/path/to/image/image.png'

image = imread( PATH );

red = image( :, :, 1 );
green = image( :, :, 2 );
blue = image( :, :, 3 );

red_s = uint8( zeros( 1, 256 ) );
for i = 1 : size( red, 1 )
    for j = 1: size( red, 2 )
        color = red( i, j );
        red_s( color + 1 ) = red_s( color + 1 ) + 1;
    end;
end;

green_s = uint8( zeros( 1, 256 ) );
for i = 1 : size( green, 1 )
    for j = 1: size( green, 2 )
        color = green( i, j );
        green_s( color + 1 ) = green_s( color + 1 ) + 1;
    end;
end;

blue_s = uint8( zeros( 1, 256 ) );
for i = 1 : size( blue, 1 )
    for j = 1: size( blue, 2 )
        color = blue( i, j );
        blue_s( color + 1 ) = blue_s( color + 1 ) + 1;
    end;
end;

subplot( 1, 4, 1 );
imshow( image );

subplot( 1, 4, 2 );
bar( red_s );

subplot( 1, 4, 3 );
bar( green_s );

subplot( 1, 4, 4 );
bar( blue_s );
