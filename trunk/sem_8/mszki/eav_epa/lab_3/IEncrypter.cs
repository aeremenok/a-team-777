using System;

namespace lab_3
{
	/// <summary>
	/// Summary description for IEncrypter.
	/// </summary>
	public interface IEncrypter
	{
		/// <summary>
		/// ������ ������, ���������� ����������
		/// </summary>
		/// <param name="p_sInString">������, ���������� ����������</param>
		/// <returns>������, ���������� ����������</returns>
		string SetInString(string p_sInString);
		
		/// <summary>
		/// �������� ������, ���������� ����������
		/// </summary>
		/// <returns>������, ���������� ����������</returns>
		string GetInString();
		
		/// <summary>
		/// ������ ���� ����������
		/// </summary>
		/// <param name="p_sKey">���� ����������</param>
		/// <returns>���� ����������</returns>
		string SetKey(string p_sKey);
		
		/// <summary>
		/// �������� ���� ����������
		/// </summary>
		/// <returns>���� ����������</returns>
		string GetKey();
		
		/// <summary>
		/// �����������, ��������� ����
		/// </summary>
		void Encrypt();
		
		/// <summary>
		/// ������������, ��������� ����
		/// </summary>
		void Decrypt();
		
		/// <summary>
		/// �������� ������������� ������
		/// </summary>
		/// <returns>������������� ������</returns>
		string GetEncryptedString();

        /// <summary>
        /// ������ ������������� ������
        /// </summary>
        /// <param name="p_sEncryptedString">������������� ������</param>
        /// <returns>������������� ������</returns>
        string SetEncryptedString(string p_sEncryptedString);		

		/// <summary>
		/// �������� �������������� ������
		/// </summary>
		/// <returns>�������������� ������</returns>
		string GetDecryptedString();
		
		/// <summary>
		/// ������������
		/// </summary>
		void CryptoAnalize();
	}
}
