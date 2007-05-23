using System;
using System.ComponentModel;
using System.Text;
using System.Windows.Forms;
using lab_1;

namespace lab_3
{
	/// <summary>
	/// Summary description for Form1.
	/// </summary>
	public class Form1 : Form
	{
		private RSAHandler _L3RSAH = new RSAHandler();
		private Button b_Encrypt;
		private Button b_Decrypt;
		private Label label2;
		private Label label3;
		private Label label4;
	    
		private Panel panel1;
		private Panel panel2;
		private OpenFileDialog openFileDialog1;
		private SaveFileDialog saveFileDialog1;
		private RichTextBox rtbINPUT;
		private Button bLoadInputFromFile;
		private Panel panel3;
		private Panel panel4;
		private Button bLoadEncrFromFile;
		private RichTextBox rtbEncr;
		private RichTextBox rtbDecrypt;
		private RichTextBox tbKEY;
		private Button bSaveIn;
		private Button button1;
		private Button button2;
		private Splitter splitter1;
		private Splitter splitter2;
		private TextBox tBKey2;
		private RichTextBox rTBkey2;
		private Button bGen;
		private System.Windows.Forms.TextBox tb_Key;
		private System.Windows.Forms.Label label1;

		private myRSA myRsa = new myRSA();

		private byte[] lastOper;


		/// <summary>
		/// Required designer variable.
		/// </summary>
		private Container components = null;

		public Form1()
		{
			InitializeComponent();
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.b_Encrypt = new System.Windows.Forms.Button();
			this.b_Decrypt = new System.Windows.Forms.Button();
			this.label2 = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.panel1 = new System.Windows.Forms.Panel();
			this.rTBkey2 = new System.Windows.Forms.RichTextBox();
			this.tBKey2 = new System.Windows.Forms.TextBox();
			this.tbKEY = new System.Windows.Forms.RichTextBox();
			this.bGen = new System.Windows.Forms.Button();
			this.panel2 = new System.Windows.Forms.Panel();
			this.rtbINPUT = new System.Windows.Forms.RichTextBox();
			this.bSaveIn = new System.Windows.Forms.Button();
			this.bLoadInputFromFile = new System.Windows.Forms.Button();
			this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
			this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
			this.panel3 = new System.Windows.Forms.Panel();
			this.rtbEncr = new System.Windows.Forms.RichTextBox();
			this.button1 = new System.Windows.Forms.Button();
			this.bLoadEncrFromFile = new System.Windows.Forms.Button();
			this.panel4 = new System.Windows.Forms.Panel();
			this.button2 = new System.Windows.Forms.Button();
			this.rtbDecrypt = new System.Windows.Forms.RichTextBox();
			this.splitter1 = new System.Windows.Forms.Splitter();
			this.splitter2 = new System.Windows.Forms.Splitter();
			this.tb_Key = new System.Windows.Forms.TextBox();
			this.label1 = new System.Windows.Forms.Label();
			this.panel1.SuspendLayout();
			this.panel2.SuspendLayout();
			this.panel3.SuspendLayout();
			this.panel4.SuspendLayout();
			this.SuspendLayout();
			// 
			// b_Encrypt
			// 
			this.b_Encrypt.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.b_Encrypt.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.b_Encrypt.Location = new System.Drawing.Point(0, 96);
			this.b_Encrypt.Name = "b_Encrypt";
			this.b_Encrypt.Size = new System.Drawing.Size(504, 24);
			this.b_Encrypt.TabIndex = 1;
			this.b_Encrypt.Text = "Зашифровать";
			this.b_Encrypt.Click += new System.EventHandler(this.button1_Click);
			// 
			// b_Decrypt
			// 
			this.b_Decrypt.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.b_Decrypt.Location = new System.Drawing.Point(0, 88);
			this.b_Decrypt.Name = "b_Decrypt";
			this.b_Decrypt.Size = new System.Drawing.Size(504, 24);
			this.b_Decrypt.TabIndex = 3;
			this.b_Decrypt.Text = "Расшифровать";
			this.b_Decrypt.Click += new System.EventHandler(this.button2_Click);
			// 
			// label2
			// 
			this.label2.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.label2.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.label2.Dock = System.Windows.Forms.DockStyle.Top;
			this.label2.Location = new System.Drawing.Point(0, 0);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(504, 32);
			this.label2.TabIndex = 7;
			this.label2.Text = "Входная строка:";
			this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// label3
			// 
			this.label3.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.label3.Dock = System.Windows.Forms.DockStyle.Top;
			this.label3.Location = new System.Drawing.Point(0, 0);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(504, 32);
			this.label3.TabIndex = 8;
			this.label3.Text = "Зашифрованная строка:";
			this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// label4
			// 
			this.label4.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.label4.Dock = System.Windows.Forms.DockStyle.Top;
			this.label4.Location = new System.Drawing.Point(0, 0);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(504, 32);
			this.label4.TabIndex = 8;
			this.label4.Text = "Расшифрованный";
			this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// panel1
			// 
			this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
			this.panel1.Controls.Add(this.bGen);
			this.panel1.Controls.Add(this.label1);
			this.panel1.Controls.Add(this.tbKEY);
			this.panel1.Controls.Add(this.tBKey2);
			this.panel1.Controls.Add(this.tb_Key);
			this.panel1.Controls.Add(this.rTBkey2);
			this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
			this.panel1.Location = new System.Drawing.Point(0, 0);
			this.panel1.Name = "panel1";
			this.panel1.Size = new System.Drawing.Size(504, 128);
			this.panel1.TabIndex = 9;
			this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
			// 
			// rTBkey2
			// 
			this.rTBkey2.BackColor = System.Drawing.SystemColors.GrayText;
			this.rTBkey2.Location = new System.Drawing.Point(248, 64);
			this.rTBkey2.Name = "rTBkey2";
			this.rTBkey2.ReadOnly = true;
			this.rTBkey2.Size = new System.Drawing.Size(240, 48);
			this.rTBkey2.TabIndex = 10;
			this.rTBkey2.Text = "";
			this.rTBkey2.TextChanged += new System.EventHandler(this.rTBkey2_TextChanged);
			// 
			// tBKey2
			// 
			this.tBKey2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
				| System.Windows.Forms.AnchorStyles.Right)));
			this.tBKey2.BackColor = System.Drawing.SystemColors.HighlightText;
			this.tBKey2.Location = new System.Drawing.Point(248, 40);
			this.tBKey2.Name = "tBKey2";
			this.tBKey2.Size = new System.Drawing.Size(240, 20);
			this.tBKey2.TabIndex = 9;
			this.tBKey2.Text = "";
			this.tBKey2.TextChanged += new System.EventHandler(this.tBKey2_TextChanged);
			// 
			// tbKEY
			// 
			this.tbKEY.BackColor = System.Drawing.SystemColors.GrayText;
			this.tbKEY.Location = new System.Drawing.Point(8, 64);
			this.tbKEY.Name = "tbKEY";
			this.tbKEY.ReadOnly = true;
			this.tbKEY.Size = new System.Drawing.Size(232, 48);
			this.tbKEY.TabIndex = 7;
			this.tbKEY.Text = "";
			this.tbKEY.TextChanged += new System.EventHandler(this.tbKEY_TextChanged);
			// 
			// bGen
			// 
			this.bGen.Location = new System.Drawing.Point(368, 8);
			this.bGen.Name = "bGen";
			this.bGen.Size = new System.Drawing.Size(104, 20);
			this.bGen.TabIndex = 10;
			this.bGen.Text = "Сгенерировать";
			this.bGen.Click += new System.EventHandler(this.bGen_Click);
			// 
			// panel2
			// 
			this.panel2.Controls.Add(this.rtbINPUT);
			this.panel2.Controls.Add(this.b_Encrypt);
			this.panel2.Controls.Add(this.bSaveIn);
			this.panel2.Controls.Add(this.bLoadInputFromFile);
			this.panel2.Controls.Add(this.label2);
			this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
			this.panel2.Location = new System.Drawing.Point(0, 128);
			this.panel2.Name = "panel2";
			this.panel2.Size = new System.Drawing.Size(504, 120);
			this.panel2.TabIndex = 10;
			// 
			// rtbINPUT
			// 
			this.rtbINPUT.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.rtbINPUT.Dock = System.Windows.Forms.DockStyle.Fill;
			this.rtbINPUT.Location = new System.Drawing.Point(0, 32);
			this.rtbINPUT.Name = "rtbINPUT";
			this.rtbINPUT.Size = new System.Drawing.Size(504, 64);
			this.rtbINPUT.TabIndex = 9;
			this.rtbINPUT.Text = "Текст 123 text jsfhsdjkah asdjkh asdghf ghjashs ajsa sdhf ghsd";
			// 
			// bSaveIn
			// 
			this.bSaveIn.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.bSaveIn.Location = new System.Drawing.Point(16, 5);
			this.bSaveIn.Name = "bSaveIn";
			this.bSaveIn.Size = new System.Drawing.Size(140, 20);
			this.bSaveIn.TabIndex = 11;
			this.bSaveIn.Text = "Сохранить";
			this.bSaveIn.Click += new System.EventHandler(this.bSaveIn_Click);
			// 
			// bLoadInputFromFile
			// 
			this.bLoadInputFromFile.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.bLoadInputFromFile.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.bLoadInputFromFile.Location = new System.Drawing.Point(352, 8);
			this.bLoadInputFromFile.Name = "bLoadInputFromFile";
			this.bLoadInputFromFile.Size = new System.Drawing.Size(140, 20);
			this.bLoadInputFromFile.TabIndex = 10;
			this.bLoadInputFromFile.Text = "Загрузить из файла";
			this.bLoadInputFromFile.Click += new System.EventHandler(this.button1_Click_1);
			// 
			// panel3
			// 
			this.panel3.Controls.Add(this.rtbEncr);
			this.panel3.Controls.Add(this.b_Decrypt);
			this.panel3.Controls.Add(this.button1);
			this.panel3.Controls.Add(this.bLoadEncrFromFile);
			this.panel3.Controls.Add(this.label3);
			this.panel3.Dock = System.Windows.Forms.DockStyle.Top;
			this.panel3.Location = new System.Drawing.Point(0, 251);
			this.panel3.Name = "panel3";
			this.panel3.Size = new System.Drawing.Size(504, 112);
			this.panel3.TabIndex = 11;
			// 
			// rtbEncr
			// 
			this.rtbEncr.BackColor = System.Drawing.SystemColors.Control;
			this.rtbEncr.Dock = System.Windows.Forms.DockStyle.Fill;
			this.rtbEncr.Location = new System.Drawing.Point(0, 32);
			this.rtbEncr.Name = "rtbEncr";
			this.rtbEncr.Size = new System.Drawing.Size(504, 56);
			this.rtbEncr.TabIndex = 10;
			this.rtbEncr.Text = "";
			// 
			// button1
			// 
			this.button1.Location = new System.Drawing.Point(16, 5);
			this.button1.Name = "button1";
			this.button1.Size = new System.Drawing.Size(140, 20);
			this.button1.TabIndex = 12;
			this.button1.Text = "Сохранить";
			this.button1.Click += new System.EventHandler(this.button1_Click_2);
			// 
			// bLoadEncrFromFile
			// 
			this.bLoadEncrFromFile.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
			this.bLoadEncrFromFile.Location = new System.Drawing.Point(352, 8);
			this.bLoadEncrFromFile.Name = "bLoadEncrFromFile";
			this.bLoadEncrFromFile.Size = new System.Drawing.Size(140, 20);
			this.bLoadEncrFromFile.TabIndex = 9;
			this.bLoadEncrFromFile.Text = "Загрузить из файла";
			this.bLoadEncrFromFile.Click += new System.EventHandler(this.bLoadEncrFromFile_Click);
			// 
			// panel4
			// 
			this.panel4.Controls.Add(this.button2);
			this.panel4.Controls.Add(this.rtbDecrypt);
			this.panel4.Controls.Add(this.label4);
			this.panel4.Dock = System.Windows.Forms.DockStyle.Fill;
			this.panel4.Location = new System.Drawing.Point(0, 363);
			this.panel4.Name = "panel4";
			this.panel4.Size = new System.Drawing.Size(504, 139);
			this.panel4.TabIndex = 12;
			// 
			// button2
			// 
			this.button2.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.button2.Location = new System.Drawing.Point(24, 5);
			this.button2.Name = "button2";
			this.button2.Size = new System.Drawing.Size(140, 20);
			this.button2.TabIndex = 13;
			this.button2.Text = "Сохранить";
			this.button2.Click += new System.EventHandler(this.button2_Click_1);
			// 
			// rtbDecrypt
			// 
			this.rtbDecrypt.BackColor = System.Drawing.SystemColors.ScrollBar;
			this.rtbDecrypt.Dock = System.Windows.Forms.DockStyle.Fill;
			this.rtbDecrypt.Location = new System.Drawing.Point(0, 32);
			this.rtbDecrypt.Name = "rtbDecrypt";
			this.rtbDecrypt.Size = new System.Drawing.Size(504, 107);
			this.rtbDecrypt.TabIndex = 9;
			this.rtbDecrypt.Text = "";
			// 
			// splitter1
			// 
			this.splitter1.BackColor = System.Drawing.SystemColors.AppWorkspace;
			this.splitter1.Dock = System.Windows.Forms.DockStyle.Top;
			this.splitter1.Location = new System.Drawing.Point(0, 248);
			this.splitter1.Name = "splitter1";
			this.splitter1.Size = new System.Drawing.Size(504, 3);
			this.splitter1.TabIndex = 13;
			this.splitter1.TabStop = false;
			// 
			// splitter2
			// 
			this.splitter2.BackColor = System.Drawing.SystemColors.AppWorkspace;
			this.splitter2.Dock = System.Windows.Forms.DockStyle.Top;
			this.splitter2.Location = new System.Drawing.Point(0, 363);
			this.splitter2.Name = "splitter2";
			this.splitter2.Size = new System.Drawing.Size(504, 3);
			this.splitter2.TabIndex = 14;
			this.splitter2.TabStop = false;
			// 
			// tb_Key
			// 
			this.tb_Key.Location = new System.Drawing.Point(8, 40);
			this.tb_Key.Name = "tb_Key";
			this.tb_Key.Size = new System.Drawing.Size(232, 20);
			this.tb_Key.TabIndex = 14;
			this.tb_Key.Text = "";
			this.tb_Key.TextChanged += new System.EventHandler(this.tb_Key_TextChanged_1);
			// 
			// label1
			// 
			this.label1.Location = new System.Drawing.Point(8, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(100, 24);
			this.label1.TabIndex = 15;
			this.label1.Text = "КЛЮЧИ:";
			// 
			// Form1
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(504, 502);
			this.Controls.Add(this.splitter2);
			this.Controls.Add(this.panel4);
			this.Controls.Add(this.panel3);
			this.Controls.Add(this.splitter1);
			this.Controls.Add(this.panel2);
			this.Controls.Add(this.panel1);
			this.MinimumSize = new System.Drawing.Size(450, 450);
			this.Name = "Form1";
			this.Text = "RSA.гр.3351: Ерёменок А.В., Шульженко В.И., Эмман П.А.";
			this.panel1.ResumeLayout(false);
			this.panel2.ResumeLayout(false);
			this.panel3.ResumeLayout(false);
			this.panel4.ResumeLayout(false);
			this.ResumeLayout(false);

		}
		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}

		private void button1_Click(object sender, EventArgs e)
		{
			string ResultMessage;
			try
			{
				if ( !this.tb_Key.Text.Equals(""))
				{
					if ( !this.rtbINPUT.Text.Equals("") )
					{
						string inStr = this.rtbINPUT.Text;
						//					string key1 = this.tbKEY.Text;
						//					string key2 = this.tBKey2.Text;
						
						byte[] b = StringOps.ConvertStringToByteArray(inStr);
						lastOper = myRsa.encrypt(b);
						string strRes = StringOps.ConvertByteArrayToString(lastOper);
						this.rtbEncr.Text = strRes;

						/*_L3RSAH.SetInString(inStr);
						_L3RSAH.Encrypt();
						this.rtbEncr.Text = _L3RSAH.GetEncryptedString();
						*/
						ResultMessage = "Шифрование выполнено успешно";  
					} 
					else
					{
						ResultMessage = "Не задан исходный текст";
					}
				} 
				else
				{
					ResultMessage = "Не задан ключ шифрования";
				}
		}catch(Exception ex){
			ResultMessage = ex.Message;
		}
			MessageBox.Show(ResultMessage);
		}

		private void button2_Click(object sender, EventArgs e)
		{
            string ResultMessage;
            try{
				if ( !this.tb_Key.Text.Equals(""))
            {
                if ( !this.rtbEncr.Text.Equals("") )
                {
					string inStr = this.rtbEncr.Text;
//					string key1 = this.tbKEY.Text;
//					string key2 = this.tBKey2.Text;
				    
					
					byte[] b = StringOps.ConvertStringToByteArray(inStr);
					byte[] res = myRsa.decrypt(lastOper);
					string strRes = StringOps.ConvertByteArrayToString(res);
					this.rtbDecrypt.Text = strRes;
					
					
					/*_L3RSAH.SetEncryptedString(inStr);
                    _L3RSAH.Decrypt();
					this.rtbDecrypt.Text = _L3RSAH.GetDecryptedString();*/
					ResultMessage = "Дешифрование выполнено успешно";    
                } 
                else
                {
                    ResultMessage = "Не задан шифртекст";
                }
            } 
            else
            {
                ResultMessage = "Не задан ключ шифрования";
            }
			}catch(Exception ex)
			{
				ResultMessage = ex.Message;
			}
            MessageBox.Show(ResultMessage);
		}

		private void panel1_Paint(object sender, PaintEventArgs e)
		{
		
		}

		private void button1_Click_1(object sender, EventArgs e)
		{
			if(this.openFileDialog1.ShowDialog() == DialogResult.OK)
			{
				//MessageBox.Show(this.openFileDialog1.FileName);
				this.rtbINPUT.LoadFile(this.openFileDialog1.FileName,RichTextBoxStreamType.PlainText);
			}
		}

		private void tb_Key_TextChanged(object sender, EventArgs e)
		{
			byte[] b = Encoding.UTF8.GetBytes(this.tb_Key.Text);
			string str = "";
			for(int i =0; i < b.Length; i++)
			{
				str += b[i] + " ";
			}
			this.tbKEY.Text = str;

			if(b.Length > 256) MessageBox.Show("Длина ключа более 256! Ключ будет обрезан до 256!");

		}

		private void bLoadEncrFromFile_Click(object sender, EventArgs e)
		{
			if(this.openFileDialog1.ShowDialog() == DialogResult.OK)
			{
				//MessageBox.Show(this.openFileDialog1.FileName);
				this.rtbEncr.LoadFile(this.openFileDialog1.FileName,RichTextBoxStreamType.PlainText);
			}
		}

		private void bSaveIn_Click(object sender, EventArgs e)
		{
			if(this.saveFileDialog1.ShowDialog() == DialogResult.OK)
			{
				this.rtbINPUT.SaveFile( this.saveFileDialog1.FileName, RichTextBoxStreamType.PlainText);
			}
		}

		private void button1_Click_2(object sender, EventArgs e)
		{
			if(this.saveFileDialog1.ShowDialog() == DialogResult.OK)
			{
				this.rtbEncr.SaveFile( this.saveFileDialog1.FileName, RichTextBoxStreamType.PlainText );
			}
		}

		private void button2_Click_1(object sender, EventArgs e)
		{
			if(this.saveFileDialog1.ShowDialog() == DialogResult.OK)
			{
				this.rtbDecrypt.SaveFile( this.saveFileDialog1.FileName, RichTextBoxStreamType.PlainText );
			}
		}

		private void tbKEY_TextChanged(object sender, EventArgs e)
		{
		
		}

		private void panel5_Paint(object sender, PaintEventArgs e)
		{
		
		}

		private void tBKey2_TextChanged(object sender, EventArgs e)
		{
			byte[] b = Encoding.UTF8.GetBytes(this.tBKey2.Text);
			string str = "";
			for(int i =0; i < b.Length; i++)
			{
				str += b[i] + " ";
			}
			this.rTBkey2.Text = str;

			if(b.Length > 256) MessageBox.Show("Длина ключа более 256! Ключ будет обрезан до 256!");

		}

		private void bGen_Click(object sender, EventArgs e)
		{
			myRsa.generateNewKeys();

			this.tb_Key.Text = StringOps.ConvertByteArrayToString(myRsa.Rsaencpriv.Modulus);

			this.tBKey2.Text = StringOps.ConvertByteArrayToString(myRsa.Rsaencpub.Modulus);


			/*string[] str = _L3RSAH.GenerateKeys();
			this.tb_Key.Text = str[0];
			this.tBKey2.Text = str[1];
			this.tbkey3.Text = str[2];*/
		}

		private void rTBkey2_TextChanged(object sender, EventArgs e)
		{
		
		}

		private void tbkey3_TextChanged(object sender, EventArgs e)
		{
//			byte[] b = Encoding.UTF8.GetBytes(this.tbkey3.Text);
//			string str = "";
//			for(int i =0; i < b.Length; i++)
//			{
//				str += b[i] + " ";
//			}
//			this.rTBkey3.Text = str;
//
//			if(b.Length > 256) MessageBox.Show("Длина ключа более 256! Ключ будет обрезан до 256!");

		}

		private void tb_Key_TextChanged_1(object sender, System.EventArgs e)
		{
			byte[] b = Encoding.UTF8.GetBytes(this.tb_Key.Text);
			string str = "";
			for(int i =0; i < b.Length; i++)
			{
				str += b[i] + " ";
			}
			this.tbKEY.Text = str;

			if(b.Length > 256) MessageBox.Show("Длина ключа более 256! Ключ будет обрезан до 256!");
		}
	}
}
