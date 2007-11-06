function res = myConv(A, B)
    alen = length(A);
    blen = length(B);
    c = zeros(alen,blen+alen-1);
    for i = 1:blen
        for j = 1:alen
            c(i, i+j-1) = A(j)*B(i);
        end
    end
    for k = 1:alen+blen-1
        res(k) = sum( c(1:blen, k) );
    end