% ������� ��������� �� �����������

% ������� ������
clear all; clc;

% ������� ���� � ��������
PATH_PREFIX = 'D:\PROJECTS\a-team-777\9\kmil\kurs\editor_vs_matlab\img\';

% ������
PIC1_PATH = [PATH_PREFIX, '1.jpg']; 

% ������
PIC2_PATH = [PATH_PREFIX, '2.jpg']; 

% ������
PIC3_PATH = [PATH_PREFIX, '3.jpg']; 

% ���������� ������� �����������
LEVELS = 256;

% ����������/����������
COLOR_CHANGE = 50;

% ������ ����� �����������
hist_block_size = uint8( 256 / LEVELS );    

% 1-�� �������� - ��������

    image1 = imread( PIC1_PATH );   % ������ ����������� �� �����

    figure( 1 );
    subplot( 3, 3, 1 );
    imshow( image1 );   % ���������� ��������

    full_spectrum1 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image1, 1 )
        for j = 1: size( image1, 2 )
            color = image1( i, j );
            full_spectrum1( color + 1 ) = full_spectrum1( color + 1 ) + 1;
        end;
    end;    % ������ ������ �����������

    figure( 1 );
    subplot( 3, 3, 2 );
    plot( full_spectrum1 ); % ������ ������ �����������

    short_spectrum1 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum1( i ) = mean( full_spectrum1( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % ������ ����������� �����������

    figure( 1 );
    subplot( 3, 3, 3 );
    bar( short_spectrum1 ); % ������ ����������� �����������

% 2-� �������� - ���������� ���������� +50

    image2 = imread( PIC2_PATH);    % ������ ����������� �� �����

    figure( 1 );
    subplot( 3, 3, 4 );
    imshow( image2 );   % ���������� ��������

    full_spectrum2 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image2, 1 )
        for j = 1: size( image2, 2 )
            color = image2( i, j );
            full_spectrum2( color + 1 ) = full_spectrum2( color + 1 ) + 1;
        end;
    end;    % ������ ������ �����������
    
    figure( 1 );
    subplot( 3, 3, 5 );
    plot( full_spectrum2 ); % ������ ������ �����������

    short_spectrum2 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum2( i ) = mean( full_spectrum2( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % ������ ����������� �����������

    figure( 1 );
    subplot( 3, 3, 6 );
    bar( short_spectrum2 ); % ������ ����������� �����������

% 3-� �������� - ������ ���������� + 50

    image3 = imread( PIC1_PATH);    % ������ ����������� �� �����

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
    end;    % ������ ������ �����������, ��������� �����
    
    imwrite( image3, PIC3_PATH ); % ������ ���������� �������� � ����

    figure( 1 );
    subplot( 3, 3, 7 );
    imshow( image3 );   % ���������� ���������� ��������
    
    figure( 1 );
    subplot( 3, 3, 8 );
    plot( full_spectrum3 ); % ������ ������ �����������

    short_spectrum3 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum3( i ) = mean( full_spectrum3( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % ������ ����������� �����������

    figure( 1 );
    subplot( 3, 3, 9 );
    bar( short_spectrum3 ); % ������ ����������� �����������