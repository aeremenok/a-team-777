% light.m
% ���������� ����

% �������
clear all; clc;

% ���������
DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % ���� � ����
NEW_DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp_test\s'; % ���� � ����� ����
K = 40;          % ���������� ������� �����������
Q = 10;          % ����� ���������� ����������� � ������
COLOR_CHANGE = 50;

% ���������� ���
for class = 1 : K
    for file = 1 : Q
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );

        if rem( file, 2 ) == 0 % ��������� ������ ������ �����������
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
            end;    % ������ ������ �����������, ��������� �����
        end;

        imwrite( image, [NEW_DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] ); % ������ ���������� �������� � ����
    end;
end;
