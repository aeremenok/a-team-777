using System;
using System.Security.Cryptography;
using System.Windows.Forms;
using lab_3;

namespace lab_1
{
	/// <summary>
	/// Summary description for myRSA.
	/// </summary>
	public class myRSA
	{
		RSAParameters rsaencpub,rsaencpriv;
		bool EncPubPresent,EncPrivPresent;
		private const int KeyLen=512;
		private const int MaxRSABlockSize=KeyLen/8-11-30-1; //тут документация нагло врет (-30 -чтобы шифровалось, -1-чтобы дешифровалось)

		public RSAParameters Rsaencpub
		{
			get { return rsaencpub; }
		}

		public RSAParameters Rsaencpriv
		{
			get { return rsaencpriv; }
		}

		public myRSA()
		{
			//
			// TODO: Add constructor logic here
			//
			EncPrivPresent=false; EncPubPresent=false;
		}

		public void generateNewKeys()
		{
			RSACryptoServiceProvider rsa=new RSACryptoServiceProvider(KeyLen);

			rsaencpriv=rsa.ExportParameters(true);
			rsaencpub=rsa.ExportParameters(false);

			EncPrivPresent=true;
			EncPubPresent=true;
				
			MessageBox.Show( "EncPrivPresent " + EncPrivPresent + "  EncPubPresent" + EncPubPresent );
		}

		public byte[] encrypt(byte[] inc)
		{
			if(!EncPubPresent) //есть ли ключ шифрования?
			{
				//MessageBox.Show(this,"Нет ключа для шифрования","Нет ключа",MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
				throw new Exception("Нет ключа для шифрования");
			}
			
			RSACryptoServiceProvider rsa=new RSACryptoServiceProvider(KeyLen);
			RSAParameters tmp=new RSAParameters(); //это обход глюка с модификацией входного параметра ImportParameters
			tmp.Exponent=(byte [])rsaencpub.Exponent.Clone();
			tmp.Modulus=(byte [])rsaencpub.Modulus.Clone();
			
			rsa.ImportParameters(tmp); //устанавливаем ключ
			
			byte []inbuf=new byte[MaxRSABlockSize];
			byte []outbuf=new byte[MaxRSABlockSize];
			
			byte[] output = new byte[(inc.Length/MaxRSABlockSize+1)*64]; 

			int len = 0;
			int i = 0;

			while(i + MaxRSABlockSize < inc.Length)
			{
				inbuf = fillin(inc,i,MaxRSABlockSize);
				byte []enc;
				enc = rsa.Encrypt(inbuf,true); //шифруем

				
				
				
				for(int t = 0; t < enc.Length; t++)
				{
					output[len + t] = enc[t];
				}
				len += 64;


				i += MaxRSABlockSize;
			}
				
			inbuf = fillin(inc,i,inc.Length-i);

			outbuf=rsa.Encrypt(inbuf,true); //шифурем последний кусок
			

			for(int t = 0; t < outbuf.Length; t++)
			{
				output[len + t ] = outbuf[t];
			}
			

			return output;
		}

		public byte[] decrypt(byte[] inc)
		{
			if(!EncPubPresent) //есть ли ключ шифрования?
			{
				//MessageBox.Show(this,"Нет ключа для шифрования","Нет ключа",MessageBoxButtons.OK,MessageBoxIcon.Exclamation);
				throw new Exception("Нет ключа для шифрования");
			}
			
			RSACryptoServiceProvider rsa=new RSACryptoServiceProvider(KeyLen);
			
			RSAParameters tmp=new RSAParameters();
			tmp.D=(byte [])rsaencpriv.D.Clone();
			tmp.DP=(byte [])rsaencpriv.DP.Clone();
			tmp.DQ=(byte [])rsaencpriv.DQ.Clone();
			tmp.Exponent=(byte [])rsaencpriv.Exponent.Clone();
			tmp.InverseQ=(byte [])rsaencpriv.InverseQ.Clone();
			tmp.Modulus=(byte [])rsaencpriv.Modulus.Clone();
			tmp.P=(byte [])rsaencpriv.P.Clone();
			tmp.Q=(byte [])rsaencpriv.Q.Clone();
			rsa.ImportParameters(tmp); //устанавливаем ключ
			
			byte []inbuf=new byte[MaxRSABlockSize];
			byte []outbuf=new byte[MaxRSABlockSize];
			

			byte[] output = new byte[(inc.Length/MaxRSABlockSize+1)*64]; 

			int len;
			int i = 0;

			while(i + KeyLen/8 < inc.Length)
			{
				inbuf = fillin(inc,i,KeyLen/8);
				byte []enc;
				
				enc = rsa.Decrypt(inbuf,true);
				
				for(int t = 0; t < enc.Length; t++)
				{
					output[i + t] = enc[t];
				}


				i += KeyLen/8;
			}
				
			inbuf = fillin(inc,i,inc.Length-i);

			outbuf=rsa.Decrypt(inbuf,true);

			for(int t = 0; t < outbuf.Length; t++)
			{
				output[i + t ] = outbuf[t];
			}
			

			return output;
		}

		private byte[] fillin(byte[] inc, int start, int size)
		{
			byte[] inbuf = new byte[size];
			for(int i = 0; i < size; i++)
			{
				inbuf[i] = inc[start + i];
			}
			return inbuf;
		}


		public byte[] test(byte[] inc)
		{
			RSACryptoServiceProvider rsa=new RSACryptoServiceProvider(KeyLen);
			RSAParameters tmp=new RSAParameters(); //это обход глюка с модификацией входного параметра ImportParameters
			tmp.Exponent=(byte [])rsaencpub.Exponent.Clone();
			tmp.Modulus=(byte [])rsaencpub.Modulus.Clone();
			
			rsa.ImportParameters(tmp); //устанавливаем ключ
			

			RSACryptoServiceProvider rsa1=new RSACryptoServiceProvider(KeyLen);
			
			RSAParameters tmp1=new RSAParameters();
			tmp1.D=(byte [])rsaencpriv.D.Clone();
			tmp1.DP=(byte [])rsaencpriv.DP.Clone();
			tmp1.DQ=(byte [])rsaencpriv.DQ.Clone();
			tmp1.Exponent=(byte [])rsaencpriv.Exponent.Clone();
			tmp1.InverseQ=(byte [])rsaencpriv.InverseQ.Clone();
			tmp1.Modulus=(byte [])rsaencpriv.Modulus.Clone();
			tmp1.P=(byte [])rsaencpriv.P.Clone();
			tmp1.Q=(byte [])rsaencpriv.Q.Clone();
			rsa1.ImportParameters(tmp1); //устанавливаем ключ


			byte []inbuf=new byte[MaxRSABlockSize];
			byte []outbuf=new byte[MaxRSABlockSize];
			
			byte[] output = new byte[(inc.Length/MaxRSABlockSize+1)*64]; 

			int len;
			int i = 0;

			while(i + MaxRSABlockSize < inc.Length)
			{
				inbuf = fillin(inc,i,MaxRSABlockSize);
				byte []enc;
				enc = rsa.Encrypt(inbuf,true); //шифруем

				byte[]ttt = rsa1.Decrypt(enc,true);
				
				
				
				for(int t = 0; t < enc.Length; t++)
				{
					output[i + t] = enc[t];
				}


				i += MaxRSABlockSize;
			}
				
			inbuf = fillin(inc,i,inc.Length-i);

			outbuf=rsa.Encrypt(inbuf,true); //шифурем последний кусок
			

			for(int t = 0; t < outbuf.Length; t++)
			{
				output[i + t ] = outbuf[t];
			}
			

			return output;
		}

		
	}
}
