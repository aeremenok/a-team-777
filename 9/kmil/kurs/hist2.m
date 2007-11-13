% ����������� �������������� ������

% �������
clear all; clc;

% ���������
DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % ���� � ����
LEVELS = 64;    % ���������� ������� � �����������
K = 5;          % ���������� ������� �����������
L = 3;          % ���������� ����������� � ����
Q = 6;          % ����� ���������� ����������� � ������

% �������������
rec_rate = 0;                               % ���������� ������������ ����� �����������
rec_percent = 0;                            % ������� ������������ ����� �����������
takt = 0;                                   % ����� ����
hist_block_size = uint8( 256 / LEVELS );    % ������ ���� � �����������
image_db = [];                              % ���� �����������
size_db = [];                               % ���� �������� �����������
full_spectrum_db = [];                      % ���� ������ ����������
short_spectrum_db = [];                     % ���� �������� ����������

% ���������� ���
for class = 1 : K
    for file = 1 : L
        % ������ �� �����
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );

        % ���������� �����������
        image_db = [image_db image( : )];
        
        % ���������� �������
        vec = size( image );
        size_db = [size_db vec'];
        
        % ������ �������� �����������
        figure( 1 );
        subplot( 1, 3, 1 );
        imshow( image );
        
        % ������� ������ �����������
        full_spectrum = uint8( zeros( 1, 256 ) );
        for i = 1 : size( image, 1 )
            for j = 1: size( image, 2 )
                color = image( i, j );
                full_spectrum( color + 1 ) = full_spectrum( color + 1 ) + 1;
            end;
        end;
        
        % ��������� ������ ����������� � ����
        full_spectrum_db = [full_spectrum_db full_spectrum( : )];
        
        % ������ ������ �����������
        figure( 1 );
        subplot( 1, 3, 2 );
        plot( full_spectrum );
        
        % ������� �������� �����������
        short_spectrum = uint8( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
        end;
        
        % ��������� �������� ����������� � ����
        short_spectrum_db = [short_spectrum_db short_spectrum( : )];
        
        % ������ �������� �����������
        figure( 1 );
        subplot( 1, 3, 3 );
        bar( short_spectrum );

        % ����
        pause( 0.04 );
    end;
end;

% ���������� �������������
for class = 1 : K
    for file = L + 1 : Q
        % ��������� ���
        takt = takt + 1;
        
        % ������ ����������� �� �����
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );
        
        % ������ �������� �����������
        figure( 2 );
        subplot( 2, 3, 1 );
        imshow( image );

        % ������� ������ �����������
        full_spectrum = uint8( zeros( 1, 256 ) );
        for i = 1 : size( image, 1 )
            for j = 1: size( image, 2 )
                color = image( i, j );
                full_spectrum( color + 1 ) = full_spectrum( color + 1 ) + 1;
            end;
        end;

        % ������ ������ �����������
        figure( 2 );
        subplot( 2, 3, 2 );
        plot( full_spectrum );

        % ������� �������� �����������
        short_spectrum = uint8( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
        end;
        
        % ������ �������� �����������
        figure( 2 );
        subplot( 2, 3, 3 );
        bar( short_spectrum );

        % ����������� �������� �����������

        % �����
        for tr = 0 : 511
            vector = short_spectrum( : );
            
            new_vector = [ uint8( zeros( 256, 1 ) ); vector; uint8( zeros( 256, 1 ) ); ];

            % ���-��
            
            % ��������� ������ �� ������� ����
            QF = kron( vector, uint8( ones( 1, K * L ) ) );

            % ������������ � ��������� ���������� �� ������� L2
            %delta = sum( ( short_spectrum_db - QF ).^2 );
        end;
        
        % 
        [ ss, index ] = sort( delta );
        
        % ������ ������������ ����������� � ��� ����������� ���� ��������� �����������
        im_vector = image_db( :, index( 1 ) );
        size_vector = size_db( :, index( 1 ) );
        rec_image = reshape( im_vector, size_vector' );
        
        figure( 2 );
        subplot( 2, 3, 4 );
        imshow( rec_image );
        
        figure( 2 );
        subplot( 2, 3, 5 );
        full_sp_vector = full_spectrum_db( :, index( 1 ) );
        plot( full_sp_vector' );
        
        figure( 2 );
        subplot( 2, 3, 6 );
        short_sp_vector = short_spectrum_db( :, index( 1 ) );
        bar( short_sp_vector' );

        % ���� ����� ������������� �����������
        class_new = fix( ( index ( 1 ) - 1 ) / L ) + 1;
        if class == class_new
            rec_rate = rec_rate + 1;
        end;
        
        % ������������� ������� �������������
        rec_percent = ( rec_rate / takt ) * 100;
        subplot( 2, 3, 4 );
        title( ['Recognition: ', num2str( rec_percent )] );

        % ����
        pause( 0.04 );
    end;
end;