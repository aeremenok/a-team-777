% ����������� �������������� ������

% �������
clear all; clc;

% ���������
DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % ���� � ����
LEVELS = 32;    % ���������� ������� � �����������
K = 40;          % ���������� ������� �����������
L = 1;          % ���������� ����������� � ����
Q = 10;          % ����� ���������� ����������� � ������

% �������������
rec_rate = 0;                               % ���������� ������������ ����� �����������
rec_percent = 0;                            % ������� ������������ ����� �����������
takt = 0;                                   % ����� ����
hist_block_size = uint32( 256 / LEVELS );    % ������ ���� � �����������
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
        full_spectrum = uint32( zeros( 1, 256 ) );
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
        short_spectrum = uint32( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size ) );
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
        full_spectrum = uint32( zeros( 1, 256 ) );
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
        short_spectrum = uint32( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size ) );
        end;
        
        % ������ �������� �����������
        figure( 2 );
        subplot( 2, 3, 3 );
        bar( short_spectrum );

        % ����������� ������ � �������
        vector = short_spectrum( : );

        % ����������� ������ ������
        ext_vector = [ uint32( zeros( LEVELS, 1 ) ); vector; uint32( zeros( LEVELS, 1 ) ); ];

        % ����������� �������� ����������, ������ � ��������
        min_value = uint32( intmax) * 2;
        min_index = 0;
        best_offset = 0;

        % �����
        for offset = 1 : LEVELS * 2 + 1
            % �������� ������� ����� 
            work_vector = ext_vector( offset : offset + LEVELS - 1 );
            
            % ��������� ������ �� ������� ����
            cloned_vector = kron( work_vector, uint32( ones( 1, K * L ) ) );

            % ������������ � ��������� ���������� �� ������� L2
            delta = sum( ( short_spectrum_db - cloned_vector ).^2 );
            [ value, index ] = sort( delta );
            
            % ��������
            if value( 1 ) < min_value
                min_value = value( 1 );
                min_index = index( 1 );
                best_offset = offset;
            end;
        end;
        
        % ������ ������������ ����������� � ��� ����������� ���� ��������� �����������
        im_vector = image_db( :, min_index );
        size_vector = size_db( :, min_index );
        rec_image = reshape( im_vector, size_vector' );
        
        figure( 2 );
        subplot( 2, 3, 4 );
        imshow( rec_image );
        
        figure( 2 );
        subplot( 2, 3, 5 );
        full_sp_vector = full_spectrum_db( :, min_index );
        plot( full_sp_vector' );
        
        figure( 2 );
        subplot( 2, 3, 6 );
        short_sp_vector = short_spectrum_db( :, min_index );
        bar( short_sp_vector' );

        % ���� ����� ������������� �����������
        class_new = fix( ( min_index - 1 ) / L ) + 1;
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
