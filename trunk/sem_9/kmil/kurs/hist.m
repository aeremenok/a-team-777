% standard histogram method realization

% get lost, old variables ]=)
clear all; clc;

% ===========================================================================
% You are free to change parameters listed below, but be careful about limits

DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % path to database file
LEVELS = 64;    % levels of color in histogram
K = 40;         % number of classes in database
L = 3;          % number of images to use in database
Q = 10;         % number of images in class

% You should NOT change anything below this line
% ===========================================================================

% initialization
rec_rate = 0;                               % rate of recognition
rec_percent = 0;                            % procent of recognition
takt = 0;                                   % number of step
hist_block_size = uint8( 256 / LEVELS );    % step in array for numbers
image_db = [];                              % image database
size_db = [];                               % works even with different sized images! potentially... ^_^
full_spectrum_db = [];                      % full spectrum database
short_spectrum_db = [];                     % short spectrum database

% building databases
for class = 1 : K
    for file = 1 : L
        % read file
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );

        % add image to a database
        image_db = [image_db image( : )];
        
        % add image size to a database
        vec = size( image );
        size_db = [size_db vec'];
        
        % show source image
        figure( 1 );
        subplot( 1, 3, 1 );
        imshow( image );
        
        % calculate full spectrum
        full_spectrum = uint8( zeros( 1, 256 ) );
        for i = 1 : size( image, 1 )
            for j = 1: size( image, 2 )
                color = image( i, j );
                full_spectrum( color + 1 ) = full_spectrum( color + 1 ) + 1;
            end;
        end;
        
        % add full spectrum to a database
        full_spectrum_db = [full_spectrum_db full_spectrum( : )];
        
        % show full spectrum
        figure( 1 );
        subplot( 1, 3, 2 );
        plot( full_spectrum );
        
        % calculate shorten spectrum
        short_spectrum = uint8( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
        end;
        
        % add shorten spectrum to a database
        short_spectrum_db = [short_spectrum_db short_spectrum( : )];
        
        % show shorten spectrum
        figure( 1 );
        subplot( 1, 3, 3 );
        bar( short_spectrum );

        % wait
        pause( 0.04 );
    end;
end;

% performing recognition
for class = 1 : K
    for file = L + 1 : Q
        % next step
        takt = takt + 1;
        
        % read image from file
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );
        
        % show image
        figure( 2 );
        subplot( 2, 3, 1 );
        imshow( image );

        % calculate full spectrum
        full_spectrum = uint8( zeros( 1, 256 ) );
        for i = 1 : size( image, 1 )
            for j = 1: size( image, 2 )
                color = image( i, j );
                full_spectrum( color + 1 ) = full_spectrum( color + 1 ) + 1;
            end;
        end;

        % show full spectrum
        figure( 2 );
        subplot( 2, 3, 2 );
        plot( full_spectrum );

        % calculate shorten spectrum
        short_spectrum = uint8( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
        end;
        
        % show shorten spectrum
        figure( 2 );
        subplot( 2, 3, 3 );
        bar( short_spectrum );

        % arranging the shorten spectrum
        vector = short_spectrum( : );

        % cloning vector to match size of database
        QF = kron( vector, uint8( ones( 1, K * L ) ) );
        
        % calculate and order L2 metric
        delta = sum( ( short_spectrum_db - QF ).^2 );
        [ ss, index ] = sort( delta );
        
        % show recognized image and it's spectrums below tested sample
        im_vector = image_db( :, index( 1 ) );
        size_vector = size_db( :, index( 1 ) );
        rec_image = reshape( im_vector, size_vector' );
        figure( 2 );
        subplot( 2, 3, 4 );
        imshow( rec_image );
        full_sp_vector = full_spectrum_db( :, index( 1 ) );
        short_sp_vector = short_spectrum_db( :, index( 1 ) );
        figure( 2 );
        subplot( 2, 3, 5 );
        plot( full_sp_vector' );
        figure( 2 );
        subplot( 2, 3, 6 );
        bar( short_sp_vector' );

        % find out nearest class
        class_new = fix( ( index ( 1 ) - 1 ) / L ) + 1;
        if class == class_new
            rec_rate = rec_rate + 1;
        end;
        
        % recalculate procent of recognition
        rec_percent = ( rec_rate / takt ) * 100;
        subplot( 2, 3, 4 );
        title( ['Recognition: ', num2str( rec_percent )] );

        % wait
        pause( 0.04 );
    end;
end;