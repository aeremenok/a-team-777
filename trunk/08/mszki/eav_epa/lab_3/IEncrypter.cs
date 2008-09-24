using System;

namespace lab_3
{
	/// <summary>
	/// Summary description for IEncrypter.
	/// </summary>
	public interface IEncrypter
	{
		/// <summary>
		/// задать строку, подлежащую шифрованию
		/// </summary>
		/// <param name="p_sInString">строка, подлежащая шифрованию</param>
		/// <returns>строка, подлежащая шифрованию</returns>
		string SetInString(string p_sInString);
		
		/// <summary>
		/// получить строку, подлежащую шифрованию
		/// </summary>
		/// <returns>строка, подлежащая шифрованию</returns>
		string GetInString();
		
		/// <summary>
		/// задать ключ шифрования
		/// </summary>
		/// <param name="p_sKey">ключ шифрования</param>
		/// <returns>ключ шифрования</returns>
		string SetKey(string p_sKey);
		
		/// <summary>
		/// получить ключ шифрования
		/// </summary>
		/// <returns>ключ шифрования</returns>
		string GetKey();
		
		/// <summary>
		/// зашифровать, используя ключ
		/// </summary>
		void Encrypt();
		
		/// <summary>
		/// расшифровать, используя ключ
		/// </summary>
		void Decrypt();
		
		/// <summary>
		/// получить зашифрованную строку
		/// </summary>
		/// <returns>зашифрованная строка</returns>
		string GetEncryptedString();

        /// <summary>
        /// задать зашифрованную строку
        /// </summary>
        /// <param name="p_sEncryptedString">зашифрованная строка</param>
        /// <returns>зашифрованная строка</returns>
        string SetEncryptedString(string p_sEncryptedString);		

		/// <summary>
		/// получить расшифрованную строку
		/// </summary>
		/// <returns>расшифрованная строка</returns>
		string GetDecryptedString();
		
		/// <summary>
		/// криптоанализ
		/// </summary>
		void CryptoAnalize();
	}
}
