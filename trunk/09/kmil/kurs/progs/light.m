% light.m
% осветление базы

% очистка
clear all; clc;

% параметры
DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % путь к базе
NEW_DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp_test\s'; % путь к новой базе
K = 40;          % количество классов изображений
Q = 10;          % общее количество изображений в классе
COLOR_CHANGE = 50;

% построение баз
for class = 1 : K
    for file = 1 : Q
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );

        if rem( file, 2 ) == 0 % осветляем только чётные изображения
            for i = 1 : size( image, 1 )
                for j = 1: size( image, 2 )
                    color = image( i, j ) + COLOR_CHANGE;
                    if color < 0
                        color = 0;
                    elseif color > 255
                        color = 255;
                    end;
                    image( i, j ) = color;
                end;
            end;    % расчёт полной гистограммы, коррекция цвета
        end;

        imwrite( image, [NEW_DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] ); % запись полученной картинки в файл
    end;
end;
