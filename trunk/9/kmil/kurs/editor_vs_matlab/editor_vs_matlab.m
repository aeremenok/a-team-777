% влияние освещения на гистограмму

% очистка экрана
clear all; clc;

% префикс пути к картинке
PATH_PREFIX = 'D:\PROJECTS\a-team-777\9\kmil\kurs\editor_vs_matlab\img\';

% первая
PIC1_PATH = [PATH_PREFIX, '1.jpg']; 

% вторая
PIC2_PATH = [PATH_PREFIX, '2.jpg']; 

% третья
PIC3_PATH = [PATH_PREFIX, '3.jpg']; 

% количество уровней гистограммы
LEVELS = 256;

% осветление/затемнение
COLOR_CHANGE = 50;

% размер блока гистограммы
hist_block_size = uint8( 256 / LEVELS );    

% 1-ая картинка - оригинал

    image1 = imread( PIC1_PATH );   % чтение изображения из файла

    figure( 1 );
    subplot( 3, 3, 1 );
    imshow( image1 );   % показываем картинку

    full_spectrum1 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image1, 1 )
        for j = 1: size( image1, 2 )
            color = image1( i, j );
            full_spectrum1( color + 1 ) = full_spectrum1( color + 1 ) + 1;
        end;
    end;    % расчёт полной гистограммы

    figure( 1 );
    subplot( 3, 3, 2 );
    plot( full_spectrum1 ); % рисуем полную гистограмму

    short_spectrum1 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum1( i ) = mean( full_spectrum1( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % расчёт укороченной гистограммы

    figure( 1 );
    subplot( 3, 3, 3 );
    bar( short_spectrum1 ); % рисуем укороченную гистограмму

% 2-я картинка - осветление редактором +50

    image2 = imread( PIC2_PATH);    % чтение изображения из файла

    figure( 1 );
    subplot( 3, 3, 4 );
    imshow( image2 );   % показываем картинку

    full_spectrum2 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image2, 1 )
        for j = 1: size( image2, 2 )
            color = image2( i, j );
            full_spectrum2( color + 1 ) = full_spectrum2( color + 1 ) + 1;
        end;
    end;    % расчёт полной гистограммы
    
    figure( 1 );
    subplot( 3, 3, 5 );
    plot( full_spectrum2 ); % рисуем полную гистограмму

    short_spectrum2 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum2( i ) = mean( full_spectrum2( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % расчёт укороченной гистограммы

    figure( 1 );
    subplot( 3, 3, 6 );
    bar( short_spectrum2 ); % рисуем укороченную гистограмму

% 3-я картинка - ручное осветление + 50

    image3 = imread( PIC1_PATH);    % чтение изображения из файла

    full_spectrum3 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image3, 1 )
        for j = 1: size( image3, 2 )
            color = image3( i, j ) + COLOR_CHANGE;
            if color < 0
                color = 0;
            elseif color > 255
                color = 255;
            end;
            full_spectrum3( color + 1 ) = full_spectrum3( color + 1 ) + 1;
            image3( i, j ) = color;
        end;
    end;    % расчёт полной гистограммы, коррекция цвета
    
    imwrite( image3, PIC3_PATH ); % запись полученной картинки в файл

    figure( 1 );
    subplot( 3, 3, 7 );
    imshow( image3 );   % показываем полученную картинку
    
    figure( 1 );
    subplot( 3, 3, 8 );
    plot( full_spectrum3 ); % рисуем полную гистограмму

    short_spectrum3 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum3( i ) = mean( full_spectrum3( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % расчёт укороченной гистограммы

    figure( 1 );
    subplot( 3, 3, 9 );
    bar( short_spectrum3 ); % рисуем укороченную гистограмму