% ������� ��������� �� �����������

% ������� ������
clear all; clc;

% ������
PIC1_PATH = 'D:\_KMIL_TEMP\1.bmp'; 

% ������
PIC2_PATH = 'D:\_KMIL_TEMP\2.bmp'; 

% ���������� ������� �����������
LEVELS = 16;    

% ������ ����� �����������
hist_block_size = uint8( 256 / LEVELS );    

% 1-�� ��������

    image1 = imread( PIC1_PATH );   % ������ ����������� �� �����

    figure( 1 );
    subplot( 2, 3, 1 );
    imshow( image1 );   % ���������� ��������

    full_spectrum1 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image1, 1 )
        for j = 1: size( image1, 2 )
            color = image1( i, j );
            full_spectrum1( color + 1 ) = full_spectrum1( color + 1 ) + 1;
        end;
    end;    % ������ ������ �����������

    figure( 1 );
    subplot( 2, 3, 2 );
    plot( full_spectrum1 ); % ������ ������ �����������

    short_spectrum1 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum1( i ) = mean( full_spectrum1( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % ������ ����������� �����������

    figure( 1 );
    subplot( 2, 3, 3 );
    bar( short_spectrum1 ); % ������ ����������� �����������

% 2-� ��������

    image2 = imread( PIC2_PATH);    % ������ ����������� �� �����

    figure( 1 );
    subplot( 2, 3, 4 );
    imshow( image2 );   % ���������� ��������

    full_spectrum2 = uint8( zeros( 1, 256 ) );
    for i = 1 : size( image2, 1 )
        for j = 1: size( image2, 2 )
            color = image2( i, j );
            full_spectrum2( color + 1 ) = full_spectrum2( color + 1 ) + 1;
        end;
    end;    % ������ ������ �����������
    
    figure( 1 );
    subplot( 2, 3, 5 );
    plot( full_spectrum2 ); % ������ ������ �����������

    short_spectrum2 = uint8( zeros( 1, LEVELS ) );
    for i = 1 : LEVELS
        short_spectrum2( i ) = mean( full_spectrum2( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
    end;    % ������ ����������� �����������

    figure( 1 );
    subplot( 2, 3, 6 );
    bar( short_spectrum2 ); % ������ ����������� �����������
