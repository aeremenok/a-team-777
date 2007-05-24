function [myconv] = myConv(y1,y2)
k=0;m=0;
n  = length(y1);
n1 = length(y2); 
myconv =(0:n+n1-2);
while k<=n+n1-2
   myconv(1,k+1)=0;
   m=-n-n1+2;
   while  m<=k
      if (m>=0 & m<=n-1 & k-m>=0 & k-m<=n1-1) 
         myconv(1,k+1)=myconv(1,k+1)+y1(1,m+1)*y2(1,k-m+1);
         end;
         m=m+1;
    end;
    k=k+1;
end;
