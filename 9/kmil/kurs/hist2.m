% модификация гистограммного метода

% очистка
clear all; clc;

% параметры
DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % путь к базе
LEVELS = 64;    % количество уровней в гистограмме
K = 5;          % количество классов изображений
L = 3;          % количество изображений в базе
Q = 6;          % общее количество изображений в классе

% инициализация
rec_rate = 0;                               % количество распознанных верно изображений
rec_percent = 0;                            % процент распознанных верно изображений
takt = 0;                                   % номер шага
hist_block_size = uint8( 256 / LEVELS );    % размер шага в гистограмме
image_db = [];                              % база изображений
size_db = [];                               % база размеров изображений
full_spectrum_db = [];                      % база полных гистограмм
short_spectrum_db = [];                     % база коротких гистограмм

% построение баз
for class = 1 : K
    for file = 1 : L
        % чтение из файла
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );

        % добавление изображения
        image_db = [image_db image( : )];
        
        % добавление размера
        vec = size( image );
        size_db = [size_db vec'];
        
        % рисуем исходное изображение
        figure( 1 );
        subplot( 1, 3, 1 );
        imshow( image );
        
        % считаем полную гистограмму
        full_spectrum = uint8( zeros( 1, 256 ) );
        for i = 1 : size( image, 1 )
            for j = 1: size( image, 2 )
                color = image( i, j );
                full_spectrum( color + 1 ) = full_spectrum( color + 1 ) + 1;
            end;
        end;
        
        % добавляем полную гистограмму в базу
        full_spectrum_db = [full_spectrum_db full_spectrum( : )];
        
        % рисуем полную гистограмму
        figure( 1 );
        subplot( 1, 3, 2 );
        plot( full_spectrum );
        
        % считаем короткую гистограмму
        short_spectrum = uint8( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
        end;
        
        % добавляем короткую гистограмму в базу
        short_spectrum_db = [short_spectrum_db short_spectrum( : )];
        
        % рисуем короткую гистограмму
        figure( 1 );
        subplot( 1, 3, 3 );
        bar( short_spectrum );

        % ждёмс
        pause( 0.04 );
    end;
end;

% производим распознавание
for class = 1 : K
    for file = L + 1 : Q
        % следующий шаг
        takt = takt + 1;
        
        % чтение изображения из файла
        image = imread( [DB_PATH, num2str( class ), '\', num2str( file ), '.bmp'] );
        
        % рисуем исходное изображение
        figure( 2 );
        subplot( 2, 3, 1 );
        imshow( image );

        % считаем полную гистограмму
        full_spectrum = uint8( zeros( 1, 256 ) );
        for i = 1 : size( image, 1 )
            for j = 1: size( image, 2 )
                color = image( i, j );
                full_spectrum( color + 1 ) = full_spectrum( color + 1 ) + 1;
            end;
        end;

        % рисуем полную гистограмму
        figure( 2 );
        subplot( 2, 3, 2 );
        plot( full_spectrum );

        % считаем короткую гистограмму
        short_spectrum = uint8( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size + 1 ) );
        end;
        
        % рисуем короткую гистограмму
        figure( 2 );
        subplot( 2, 3, 3 );
        bar( short_spectrum );

        % выравниваем короткую гистограмму

        % сдвиг
        for tr = 0 : 511
            vector = short_spectrum( : );
            
            new_vector = [ uint8( zeros( 256, 1 ) ); vector; uint8( zeros( 256, 1 ) ); ];

            % что-то
            
            % клонируем вектор до размера базы
            QF = kron( vector, uint8( ones( 1, K * L ) ) );

            % рассчитываем и сортируем расстояние по метрике L2
            %delta = sum( ( short_spectrum_db - QF ).^2 );
        end;
        
        % 
        [ ss, index ] = sort( delta );
        
        % рисуем распознанное изображение и его гистограммы ниже исходного изображения
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

        % ищем класс распознанного изображения
        class_new = fix( ( index ( 1 ) - 1 ) / L ) + 1;
        if class == class_new
            rec_rate = rec_rate + 1;
        end;
        
        % пересчитываем процент распознавания
        rec_percent = ( rec_rate / takt ) * 100;
        subplot( 2, 3, 4 );
        title( ['Recognition: ', num2str( rec_percent )] );

        % ждёмс
        pause( 0.04 );
    end;
end;