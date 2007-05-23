using System;
using System.Text;

namespace lab_3
{
    /// <summary>
    /// Summary description for Class1.
    /// </summary>
    public class StringOps
    {
        public StringOps()
        {
            //
            // TODO: Add constructor logic here
            //
        }
        
        public static byte[] ConvertStringToByteArray(string StrToConvert)
        {
        	return System.Text.Encoding.GetEncoding( 1251 ).GetBytes( StrToConvert );
        }

        /// <summary>
        /// Converts a byte array to a string
        /// </summary>
        /// <param name="ByteToConvert"></param>
        /// <returns></returns>
        public static string ConvertByteArrayToString(byte[] ByteToConvert)
        {
            string tempStr = "";
            tempStr += System.Text.Encoding.GetEncoding( 1251 ).GetString( ByteToConvert ) ;
            return tempStr;
        }
        
        
        public static byte[] ConvertStringNUMBERSToByteArray(string StrToConvert)
        {
        	byte[] res = new byte[StrToConvert.Length];
        	int pos = 0;
        	int cur = 0;
        	for(int i = 0; i < StrToConvert.Length; i++)
        	{
        		if(StrToConvert[i] == ' '){
        			res[pos] = (byte)cur;
        			cur = 0;
        			pos++;
        		}else{
        			cur = cur * 10 + Byte.Parse(StrToConvert[i] + "");
        		}
        	}
        	//res.Length = pos;
        	byte[] result = new byte[pos];
        	for(int i = 0; i < pos; i++)
        	{
        		result[i] = res[i];
        	}
        	                        
        	return result;
        }

        /// <summary>
        /// Converts a byte array to a string
        /// </summary>
        /// <param name="ByteToConvert"></param>
        /// <returns></returns>
        public static string ConvertByteArrayToStringNUMBERS(byte[] ByteToConvert)
        {
            string tempStr = "";
            for(int i = 0; i < ByteToConvert.Length; i++)
            tempStr += ByteToConvert[i] + " ";
            return tempStr;
        }
    }
}
