using System;
using System.Security.Cryptography;
using System.Windows.Forms;
using org.bouncycastle.math;

namespace lab_3
{
	/// <summary>
	/// Summary description for RSAHandler.
	/// </summary>
	public class RSAHandler : IEncrypter
	{
        private byte[] _inBA;
        private byte[] _encrBA;
        private BigInteger _encrBI;
        private byte[] _decrBA;

        private const int KEY_LEN = 512;
        private const int NUM_BASE = 10;
        private const int RSA_ENCR_SIZE = 53;
		private const int RSA_DECR_SIZE = 65;
		

        RSACryptoServiceProvider _rsaCsp = new RSACryptoServiceProvider(KEY_LEN);

		public RSAHandler()
		{
		}

        public string[] GenerateKeys()
        {
            //��������� ������: ����� ���� ������������ ������������� ��� �������� ���������� ������
            _rsaCsp = new RSACryptoServiceProvider(KEY_LEN);
            //������� �����
            RSAParameters privateRSAParameters = _rsaCsp.ExportParameters(true); //������� ��������� �����
            RSAParameters publicRSAParameters = _rsaCsp.ExportParameters(false); //������� ��������� �����
            //������� �������� ����
            BigInteger privateExpBI = new BigInteger( privateRSAParameters.Exponent );
            BigInteger publicExpBI = new BigInteger( publicRSAParameters.Modulus );
            BigInteger privateModBI = new BigInteger( privateRSAParameters.Modulus );
            string privateExp = privateExpBI.ToString( NUM_BASE );
            string publicExp = publicExpBI.ToString( NUM_BASE );
            string privateMod = privateModBI.ToString( NUM_BASE );
            string[] res = { privateExp, publicExp, privateMod };

            //������ �����
            _rsaCsp.ImportParameters(privateRSAParameters);
            return res;
        }

        private byte[] EncryptMessage(byte[] message)
        {
            int numberOfBlocks = message.Length/RSA_ENCR_SIZE;
			byte[] res = new byte[numberOfBlocks * RSA_DECR_SIZE];
			// ��������� ������� �� ����� �� RSA_DECR_SIZE
            if(numberOfBlocks==0)
            {
                res = _rsaCsp.Encrypt(message, false);
            }
            else
            {
                int j = 0;
                for (int i = 0; i < numberOfBlocks; i++)
                {
                    // ���������� ������ �����
                    int blockLen = message.Length - j;
                    if ( blockLen > RSA_ENCR_SIZE)
                    {	// �� �������� ��������� � �� ��������� ����
                        blockLen = RSA_ENCR_SIZE;
                    }                    
                    byte[] tempSrc = new byte[blockLen];
                    for (int k = 0; k < blockLen; k++)
                    {
                        tempSrc[k] = message[j + k];
                    }
                    byte[] tempRes = _rsaCsp.Encrypt(tempSrc, false);
                    tempRes.CopyTo(res, j);

                    j+=blockLen;
                }
            }
            return res;
//            try
//            {
//                byte[] res = _rsaCsp.Encrypt(message, false);
//                MessageBox.Show("Yes");
//                return res;
//            }
//            catch(Exception)
//            {
//                MessageBox.Show("No");
//                return new byte[RSA_DECR_SIZE];
//            }
        }

        private byte[] DecryptMessage(byte[] message)
        {
            int numberOfBlocks = message.Length/RSA_DECR_SIZE;
            byte[] res = new byte[numberOfBlocks * RSA_ENCR_SIZE];
            // ��������� ������� �� ����� �� RSA_DECR_SIZE
            if(numberOfBlocks==0)
            {
                res = _rsaCsp.Decrypt(message, false);
            }
            else
            {
                int j = 0;
                for (int i = 0; i < numberOfBlocks; i++)
                {
                    // ���������� ������ �����
                    int blockLen = message.Length - j;
                    if ( blockLen > RSA_DECR_SIZE)
                    {	// �� �������� ��������� � �� ��������� ����
                        blockLen = RSA_DECR_SIZE;
                    }                    
                    byte[] tempSrc = new byte[blockLen];
                    for (int k = 0; k < blockLen; k++)
                    {
                        tempSrc[k] = message[j + k];
                    }
                    byte[] tempRes;
                    try
                    {
                        tempRes = _rsaCsp.Decrypt(tempSrc, false);
                    }
                    catch(Exception e)
                    {
                        tempRes = new byte[RSA_ENCR_SIZE];
                        MessageBox.Show(e.StackTrace);
                    }
                    tempRes.CopyTo(res, j);

                    j+=blockLen;
                }
            }
            return res;
        }

	    public string SetInString(string p_sInString)
	    {
            _inBA = StringOps.ConvertStringToByteArray(p_sInString);
            return StringOps.ConvertByteArrayToString(_inBA);
	    }

	    public string GetInString()
	    {
	        return StringOps.ConvertByteArrayToString(_inBA);
	    }

	    public string SetKey(string p_sKey)
	    {
	        throw new NotImplementedException();
	    }

	    public string GetKey()
	    {
	        throw new NotImplementedException();
	    }

	    public void Encrypt()
	    {
	        _encrBA = EncryptMessage(_inBA);
			_encrBI = new BigInteger(_encrBA);
	    }

	    public void Decrypt()
	    {
	        _decrBA = DecryptMessage(_encrBA);
	    }

	    public string GetEncryptedString()
	    {
	        return _encrBI.ToString(NUM_BASE);
	    }

	    public string SetEncryptedString(string p_sEncryptedString)
	    {
            _encrBI = new BigInteger(p_sEncryptedString, NUM_BASE);
			_encrBA = _encrBI.toByteArray();
            return _encrBI.ToString(NUM_BASE);
	    }

	    public string GetDecryptedString()
	    {
	        return StringOps.ConvertByteArrayToString(_decrBA);
	    }

	    public void CryptoAnalize()
	    {
	        throw new NotImplementedException();
	    }
	}
}
