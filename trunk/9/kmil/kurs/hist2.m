% модификация гистограммного метода

% очистка
clear all; clc;

% параметры
DB_PATH = 'D:\LETI\9\kmil\bases\orl_bmp\s'; % путь к базе
LEVELS = 32;    % количество уровней в гистограмме
K = 40;          % количество классов изображений
L = 1;          % количество изображений в базе
Q = 10;          % общее количество изображений в классе

% инициализация
rec_rate = 0;                               % количество распознанных верно изображений
rec_percent = 0;                            % процент распознанных верно изображений
takt = 0;                                   % номер шага
hist_block_size = uint32( 256 / LEVELS );    % размер шага в гистограмме
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
        full_spectrum = uint32( zeros( 1, 256 ) );
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
        short_spectrum = uint32( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size ) );
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
        full_spectrum = uint32( zeros( 1, 256 ) );
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
        short_spectrum = uint32( zeros( 1, LEVELS ) );
        for i = 1 : LEVELS
            short_spectrum( i ) = mean( full_spectrum( 1, ( i - 1 ) * hist_block_size + 1 : i * hist_block_size ) );
        end;
        
        % рисуем короткую гистограмму
        figure( 2 );
        subplot( 2, 3, 3 );
        bar( short_spectrum );

        % выравниваем вектор в столбец
        vector = short_spectrum( : );

        % расширенный нулями вектор
        ext_vector = [ uint32( zeros( LEVELS, 1 ) ); vector; uint32( zeros( LEVELS, 1 ) ); ];

        % минимальное значение расстояния, индекс и смещение
        min_value = uint32( intmax) * 2;
        min_index = 0;
        best_offset = 0;

        % сдвиг
        for offset = 1 : LEVELS * 2 + 1
            % выбираем рабочую часть 
            work_vector = ext_vector( offset : offset + LEVELS - 1 );
            
            % клонируем вектор до размера базы
            cloned_vector = kron( work_vector, uint32( ones( 1, K * L ) ) );

            % рассчитываем и сортируем расстояние по метрике L2
            delta = sum( ( short_spectrum_db - cloned_vector ).^2 );
            [ value, index ] = sort( delta );
            
            % выбираем
            if value( 1 ) < min_value
                min_value = value( 1 );
                min_index = index( 1 );
                best_offset = offset;
            end;
        end;
        
        % рисуем распознанное изображение и его гистограммы ниже исходного изображения
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

        % ищем класс распознанного изображения
        class_new = fix( ( min_index - 1 ) / L ) + 1;
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
