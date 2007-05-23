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
    }
}
